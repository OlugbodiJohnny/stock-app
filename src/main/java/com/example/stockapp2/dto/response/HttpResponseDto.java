package com.example.stockapp2.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @author Johnny
 */
@Data
@NoArgsConstructor
public class HttpResponseDto {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "GMT+1")
    private final Date timeStamp = new Date();

    private HttpStatus statusCode;

    private String entity;

    private String message;

    private String errorMessage;

    private String trace;

    private Object data;

    public HttpResponseDto(HttpStatus statusCode, String entity, String message) {
        this.statusCode = statusCode;
        this.entity = entity;
        this.message = message;
    }

    public HttpResponseDto(HttpStatus statusCode, String entity, String message, Object data) {
        this.statusCode = statusCode;
        this.entity = entity;
        this.message = message;
        this.data = data;
    }

    public HttpResponseDto(HttpStatus statusCode, String entity, String message, String errorMessage, String trace, Object data) {
        this.statusCode = statusCode;
        this.entity = entity;
        this.message = message;
        this.errorMessage = errorMessage;
        this.trace = trace;
        this.data = data;
    }

}
