package com.changeside.courseerpbackend.services.base;

public interface TokenReader <T> {
    T read(String token);
}
