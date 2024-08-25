package dev.minn_shop.minn_shop.handler;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

import lombok.Getter;

public enum BusinessErrorCodes {
    NO_CODE(0,NOT_IMPLEMENTED,"No code"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST,"The password is not correct!"),
    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST, "The new password does not match"),
    ACCOUNT_LOCKED(302,FORBIDDEN, "User account is locked"),
    ACCOUNT_DISABLED(303,FORBIDDEN, "User account is disabled"),
    BAD_CREDENTIALS(304,FORBIDDEN, "Login and / or password is not correct"),
    ;
    @Getter
    private final int code;
    @Getter
    private final HttpStatus httpStatus;
    @Getter
    private final String description;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
