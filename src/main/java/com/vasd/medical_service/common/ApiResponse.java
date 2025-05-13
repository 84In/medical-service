package com.vasd.medical_service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * ApiResponse là class dùng để chuẩn hóa định dạng phản hồi (response)
 * trả về từ API. Giúp các client (frontend, mobile...) dễ dàng xử lý kết quả.
 *
 * @param <T> Kiểu dữ liệu cụ thể của phần kết quả (payload)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    /**
     * Mã phản hồi (code). Thường là 0 nếu thành công, hoặc mã lỗi nếu thất bại.
     */
    @Builder.Default
    int code = 0;
    /**
     * Thông điệp mô tả kết quả (message). Mặc định là "Success!".
     */
    @Builder.Default
    String message = "Success!";

    /**
     * Dữ liệu kết quả trả về (payload). Có thể null nếu không cần thiết.
     */
    T result;
}
