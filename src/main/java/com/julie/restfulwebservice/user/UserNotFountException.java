package com.julie.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
HTTP status code
 2XX -> OK
 4XX -> Client
 5XX -> Server
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFountException extends RuntimeException {

    public UserNotFountException(String message) {
        super(message);
    }
}
