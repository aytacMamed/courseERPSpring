package com.changeside.courseerpbackend.models.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseResponse <T> {
    HttpStatus status;
    String msg;
    T data;

    public static <T> BaseResponse<T> success(T data){
        return BaseResponse.<T>builder().status(HttpStatus.OK).msg("SUCCESS").data(data).build();
    }
}
