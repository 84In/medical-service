package com.vasd.medical_service.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;



/**
 * AppException là custom exception được sử dụng để xử lý các lỗi nghiệp vụ (business logic)
 * trong hệ thống. Mỗi exception sẽ kèm theo một mã lỗi và thông điệp từ {@link ErrorCode}.
 *
 * Exception này kế thừa từ {@link RuntimeException} nên không bắt buộc phải try-catch,
 * và có thể được xử lý tập trung qua GlobalExceptionHandler.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {
    /**
     * Mã lỗi và thông điệp tương ứng được định nghĩa trong {@link ErrorCode}.
     */
    ErrorCode errorCode;
    /**
     * Khởi tạo một AppException với {@link ErrorCode} tương ứng.
     *
     * @param errorCode mã lỗi và thông điệp tương ứng
     */
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
