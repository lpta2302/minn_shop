package dev.minn_shop.minn_shop.user;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User currentUser = (User) authentication.getPrincipal();
            return currentUser;
        } else
            throw new RuntimeException("User not authenticated");
    }

    public User getUserById(int id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Not found user with id: " + id));
    }

    public List<UserDetailRecord> getUsersDetails(int page, int size) {
        return userRepository.findUserDetails(PageRequest.of(page, size)).toList();
    }

    public User updateUser(int id, User user) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(currentUser.getId() != id)
            throw new RuntimeException("Permission denied! Can not update user that no authenticated");

        return userRepository.save(user);
    }

    public boolean deleteUser(int id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(currentUser.getId() != id)
            throw new RuntimeException("Permission denied! Can not update user that no authenticated");

        userRepository.deleteById(id);

        return true;
    }
}
