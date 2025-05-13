package com.vasd.medical_service.exception;

import com.vasd.medical_service.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler dùng để xử lý các ngoại lệ phát sinh trong toàn bộ ứng dụng.
 * Sử dụng @ControllerAdvice để bắt các lỗi và trả về phản hồi thống nhất dưới dạng {@link ApiResponse}.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Bắt tất cả lỗi chưa xác định (RuntimeException).
     *
     * @param ex Ngoại lệ chưa xác định
     * @return Phản hồi lỗi mặc định
     */
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException ex) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage() + ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Bắt lỗi khi người dùng truy cập tài nguyên không được phép.
     *
     * @param exception AccessDeniedException từ Spring Security
     * @return Phản hồi lỗi
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        log.error("Access denied", exception);
        return ResponseEntity
                .status(ErrorCode
                        .UNAUTHORIZED
                        .getStatusCode())
                .body(ApiResponse.builder()
                        .code(ErrorCode.UNAUTHORIZED.getCode())
                        .message(ErrorCode.UNAUTHORIZED.getMessage())
                        .build());
    }

    /**
     * Bắt lỗi nghiệp vụ do AppException ném ra.
     *
     * @param exception AppException do ứng dụng định nghĩa
     * @return Phản hồi lỗi với mã lỗi và message tùy biến
     */
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {

        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<?> apiResponse = new ApiResponse<>();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);

    }
    /**
     * Bắt lỗi validate @Valid (MethodArgumentNotValidException).
     * Lỗi thường gặp khi tạo hoặc cập nhật entity có dữ liệu không hợp lệ.
     *
     * @param exception MethodArgumentNotValidException
     * @return Danh sách lỗi theo từng field
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Map<String, String>>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();


        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorMap.put(fieldName, message);
        });


        ApiResponse<Map<String, String>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.INVALID_KEY.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_KEY.getMessage());
        apiResponse.setResult(errorMap);

        return ResponseEntity.badRequest().body(apiResponse);
    }
    /**
     * Bắt lỗi vi phạm ràng buộc dữ liệu từ CSDL như:
     * - Trùng khóa (UNIQUE)
     * - Thiếu NOT NULL
     * - Sai khóa ngoại
     *
     * @param ex DataIntegrityViolationException từ Spring Data JPA
     * @return Phản hồi lỗi tùy theo nội dung lỗi từ MySQL
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Data integrity violation", ex);

        String message = "Dữ liệu không hợp lệ hoặc vi phạm ràng buộc.";

        Throwable rootCause = ex.getRootCause();
        if (rootCause != null && rootCause.getMessage() != null) {
            String rootMsg = rootCause.getMessage().toLowerCase();

            if (rootMsg.contains("duplicate entry")) {
                message = "Giá trị đã tồn tại trong hệ thống (ví dụ: username hoặc email bị trùng).";
            } else if (rootMsg.contains("cannot be null")) {
                message = "Thiếu dữ liệu bắt buộc (trường NOT NULL).";
            } else if (rootMsg.contains("foreign key constraint")) {
                message = "Dữ liệu liên kết không hợp lệ (khóa ngoại không tồn tại).";
            }
        }

        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .code(ErrorCode.DATA_INTEGRITY_VIOLATION.getCode())
                .message(message)
                .build());
    }
}
