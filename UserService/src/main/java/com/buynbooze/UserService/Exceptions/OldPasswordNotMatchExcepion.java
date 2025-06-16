package com.buynbooze.UserService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OldPasswordNotMatchExcepion extends RuntimeException {
    public OldPasswordNotMatchExcepion(String messaage) {
        super(messaage);
    }
}
