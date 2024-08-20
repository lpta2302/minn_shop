package dev.minn_shop.minn_shop.user;

public record UserBriefRecord(
    Integer id,
    String username,
    String firstName,
    String lastName,
    byte[] avatar
) {}
