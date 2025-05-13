package com.vasd.medical_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //
    UNCATEGORIZED_EXCEPTION(999, "", HttpStatus.INTERNAL_SERVER_ERROR),
    //System Error
    UNAUTHORIZED(101, "You do not have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(102, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    //Validation
    INVALID_KEY(201, "Invalid message key", HttpStatus.BAD_REQUEST),

    //User Error
    ROLE_NOT_FOUND(301, "Role not found", HttpStatus.BAD_REQUEST),
    ROLE_EXISTS(302, "Role already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(303, "User not found", HttpStatus.BAD_REQUEST),
    USER_EXISTS(304, "User already exists", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(305, "Invalid password", HttpStatus.BAD_REQUEST),

    //SQL Error
    DATA_INTEGRITY_VIOLATION(1002, "Lỗi ràng buộc dữ liệu", HttpStatus.BAD_REQUEST),
    ;


    private Integer code = 0;
    private String message = "Success!";
    private HttpStatusCode statusCode;

    ErrorCode(Integer code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
