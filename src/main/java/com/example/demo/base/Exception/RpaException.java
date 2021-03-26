package com.example.demo.base.Exception;

import com.example.demo.base.response.ApiResult;
import com.example.demo.base.response.RpaResponse;

public class RpaException extends RuntimeException{

    private RpaResponse response;

    public RpaException(){

    }

    public RpaException(String message) {
        super(message);
    }

    public RpaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpaException(RpaResponse response) {
        super(response.getMsg());
        this.setResponse(response);
    }

    public RpaResponse getResponse() {
        return response;
    }

    public void setResponse(RpaResponse response) {
        this.response = response;
    }
}
