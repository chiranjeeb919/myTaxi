package com.mytaxi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Car alrady in use with id.")
public class CarAlreadyInUseException extends Exception
{
    static final long serialVersionUID = -838751345345448L;


    public CarAlreadyInUseException(String message)
    {
        super(message);
    }

}
