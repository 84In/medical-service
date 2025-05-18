package com.vasd.medical_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //Server Error
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

    //Title Error
    TITLE_NOT_FOUND(310, "Title not found", HttpStatus.BAD_REQUEST),
    TITLE_EXISTS(311, "Title already exists", HttpStatus.BAD_REQUEST),

    //Position Error
    POSITION_NOT_FOUND(312, "Position not found", HttpStatus.BAD_REQUEST),
    POSITION_EXISTS(313, "Position already exists", HttpStatus.BAD_REQUEST),
    //Department Error
    DEPARTMENT_NOT_FOUND(314, "Department not found", HttpStatus.BAD_REQUEST),
    DEPARTMENT_EXISTS(315, "Department already exists", HttpStatus.BAD_REQUEST),
    //Doctor Error
    DOCTOR_NOT_FOUND(316, "Doctor not found", HttpStatus.BAD_REQUEST),
    DOCTOR_EXISTS(317, "Doctor already exists", HttpStatus.BAD_REQUEST),
    //ServiceType Error
    SERVICE_TYPE_NOT_FOUND(318, "Service type not found", HttpStatus.BAD_REQUEST),
    SERVICE_TYPE_EXISTS(319, "Service type already exists", HttpStatus.BAD_REQUEST),
    //Service Error
    SERVICE_NOT_FOUND(320, "Service not found", HttpStatus.BAD_REQUEST),
    SERVICE_EXISTS(321, "Service already exists", HttpStatus.BAD_REQUEST),
    //Slug Error
    SLUG_EXISTS(322, "Slug already exists", HttpStatus.BAD_REQUEST),
    //New Error
    NEWS_TYPE_NOT_FOUND(323, "News type not found", HttpStatus.BAD_REQUEST),
    NEWS_TYPE_EXISTS(324, "News type already exists", HttpStatus.BAD_REQUEST),
    NEWS_NOT_FOUND(325, "News not found", HttpStatus.BAD_REQUEST),
    NEWS_EXISTS(326, "News already exists", HttpStatus.BAD_REQUEST),

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
