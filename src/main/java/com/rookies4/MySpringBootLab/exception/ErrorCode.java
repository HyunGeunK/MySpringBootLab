package com.rookies4.MySpringBootLab.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 책을 찾을 수 없습니다."),
    ISBN_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 ISBN입니다.");

    private final HttpStatus status;
    private final String message;
}