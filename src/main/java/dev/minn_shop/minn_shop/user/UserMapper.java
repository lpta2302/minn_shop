package dev.minn_shop.minn_shop.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public UserBriefRecord toUserBriefRecord(User user) {
        return new UserBriefRecord(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatar());
    }

    public UserDetailRecord toUserDetailRecord(User user) {
        return new UserDetailRecord(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getAvatar());
    }

    public User toUser(User user, UserDetailRecord record) {
        user.setId(record.id());
        user.setUsername(record.username());
        user.setFirstName(record.firstName());
        user.setLastName(record.lastName());
        user.setEmail(record.email());
        user.setPhoneNumber(record.phoneNumber());
        user.setBirthDate(record.birthDate());
        user.setAvatar(record.avatar());
        return user;
    }
    public User toUser(User user, UserBriefRecord record) {
        user.setId(record.id());
        user.setUsername(record.username());
        user.setFirstName(record.firstName());
        user.setLastName(record.lastName());
        user.setAvatar(record.avatar());
        return user;
    }
}
