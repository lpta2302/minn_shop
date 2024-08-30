package dev.minn_shop.minn_shop.user;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.exception.OperationNotPermittedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User currentUser = (User) authentication.getPrincipal();
            
            int userId = currentUser.getId();
            currentUser = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Not found user with id: " + userId));
            return currentUser;
        } else
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
    }

    public User getUserById(int id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found user with id: " + id));
    }


    public List<UserDetailRecord> getUsersDetails(int page, int size) {
        return userRepository.findUserDetails(PageRequest.of(page, size)).toList();
    }

    public UserDetailRecord updateUser(UserDetailRecord userRecord) {
        final int id = userRecord.id();
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getId() != id)
            throw new OperationNotPermittedException("Permission denied! Can not update user that no authenticated");
        currentUser = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Not found user with id: " + id));
        
        User updatedUser = userRepository.save(userMapper.toUser(currentUser, userRecord));

        return userMapper.toUserDetailRecord(updatedUser);
    }

    public boolean deleteUser(int id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (currentUser.getId() != id)
            throw new OperationNotPermittedException("Permission denied! Can not delete user that no authenticated");
            
        userRepository.deleteById(id);

        return true;
    }
}
