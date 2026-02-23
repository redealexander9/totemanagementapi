package com.rede.stagingapi.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToteNotFoundException extends ResourceNotFoundException{

    public ToteNotFoundException(Long id){
        super("Tote with ID " + id + " not in system");
    }

    public ToteNotFoundException(String message){
        super(message);
    }
}
