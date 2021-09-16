package com.stanbic.authorizationservice.wrappers;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bkariuki
 */
public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1200427340087598870L;

    private int code;
    private String message;
    private T data;
    private Long timeStamp;

    public ResponseWrapper() {
        this.code = 200;
        this.message = "Request was successful";
        this.timeStamp = new Date().getTime();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ResponseWrapper code(int code) {
        this.code = code;
        return this;
    }

    public ResponseWrapper message(String message) {
        this.message = message;
        return this;
    }

    public ResponseWrapper data(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
