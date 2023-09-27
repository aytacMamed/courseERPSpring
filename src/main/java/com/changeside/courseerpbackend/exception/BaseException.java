package com.changeside.courseerpbackend.exception;

import com.changeside.courseerpbackend.exception.types.NotFoundExceptionType;
import com.changeside.courseerpbackend.models.enums.response.ResponseMessages;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import static com.changeside.courseerpbackend.models.enums.response.ErrorResponseMessages.NOT_FOUND;
import static com.changeside.courseerpbackend.models.enums.response.ErrorResponseMessages.UNEXPECTED;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException{
    ResponseMessages responseMessages;
    NotFoundExceptionType notFoundData;

    //todo:fix. it doesn't return dynamic error message
    @Override
    public String getMessage() {
        return responseMessages.message();
    }

    public static BaseException unexpected(){
        return BaseException.builder().responseMessages(UNEXPECTED).build();
    }

    public static BaseException notFound(String target,String field,String value){
        return BaseException.
                builder().
                responseMessages(NOT_FOUND).
                        notFoundData(NotFoundExceptionType.of(target, Map.of(field,value))).build();
    }
}
