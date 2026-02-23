package com.rede.stagingapi.exception;

import lombok.Getter;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    @Getter
    private int status;
    private String error;
    private String message;

    public ErrorResponse(int status, String error, String message){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }
    public LocalDateTime getTimeStamp() { return timestamp;}

}
