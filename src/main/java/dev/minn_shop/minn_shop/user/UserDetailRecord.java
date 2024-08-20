package dev.minn_shop.minn_shop.user;

import java.util.Date;

public record UserDetailRecord(
    Integer id,
    String username,
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    Date birthDate,
    byte[] avatar) {}
