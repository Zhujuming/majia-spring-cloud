package com.zhujuming.vip.exception;


import com.zhujuming.vip.constants.ResponseCode;


public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getDescription(), cause);
        this.code = responseCode.value();
    }

    public ServiceException(ResponseCode responseCode) {
        super(responseCode.getDescription());
        this.code = responseCode.value();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
