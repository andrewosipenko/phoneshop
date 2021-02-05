package com.es.core.exceptions;

public class OutOfStockException extends RuntimeException {

    private String errCode;

    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public OutOfStockException() {
        this.errCode = "outOfStock";
        this.errMsg = "Not enough stock of phone item";
    }

}
