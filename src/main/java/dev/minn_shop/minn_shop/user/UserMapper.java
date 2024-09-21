package dev.minn_shop.minn_shop.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMapper {
    public BriefUserRecord toUserBriefRecord(User user) {
        return new BriefUserRecord(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getAvatar());
    }

    public DetailUserRecord toUserDetailRecord(User user) {
        return new DetailUserRecord(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getAvatar());
    }

    public User toUser(User user, DetailUserRecord record) {
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
    public User toUser(User user, BriefUserRecord record) {
        user.setId(record.id());
        user.setUsername(record.username());
        user.setFirstName(record.firstName());
        user.setLastName(record.lastName());
        user.setAvatar(record.avatar());
        return user;
    }
}
