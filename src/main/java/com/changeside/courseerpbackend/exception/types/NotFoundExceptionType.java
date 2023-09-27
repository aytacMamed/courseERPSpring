package com.changeside.courseerpbackend.exception.types;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

//@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotFoundExceptionType {
    String target;
    Map<String,Object> fields;

    public static NotFoundExceptionType of(String target,Map<String,Object> fields){
        return NotFoundExceptionType.
                builder().
                target(target).
                fields(fields).
                build();
    }
}
