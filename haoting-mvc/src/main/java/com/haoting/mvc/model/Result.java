package com.haoting.mvc.model;

import java.text.MessageFormat;

/**
 * 返回结果
 * 
 * @author Joe
 */
public class Result<T> {

    private static final Result RS_FALSE_DEFAULT   = new Result(false, 1000, "系统错误");
    private static final Result RS_SUCCESS_DEFAULT = new Result(true, 1, "默认的成功返回值");
    private int              code               = 1000;
    private String              message;
    private T                   data;
    private boolean             state              = false;

    public Result(boolean state, int code, String message, T data){
        this.state = state;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean state, int code, String message){
        this.state = state;
        this.code = code;
        this.message = message;
    }

    public static Result valueOfSuccess() {
        return new Result(RS_SUCCESS_DEFAULT.state, RS_SUCCESS_DEFAULT.code, RS_SUCCESS_DEFAULT.getMessage());
    }

    public static <M> Result valueOfSuccess(M model) {
        return new Result(RS_SUCCESS_DEFAULT.state, RS_SUCCESS_DEFAULT.code, RS_SUCCESS_DEFAULT.getMessage(), model);
    }

    public static <M> Result valueOfSuccessMsg(String message) {
        return new Result(RS_SUCCESS_DEFAULT.state, RS_SUCCESS_DEFAULT.code, message);
    }

    public static <M> Result valueOfSuccess(int code, String message, M model) {
        return new Result(RS_FALSE_DEFAULT.state, code, message, model);
    }

    public static Result valueOfError() {
        return new Result(RS_FALSE_DEFAULT.state, RS_FALSE_DEFAULT.code, RS_FALSE_DEFAULT.getMessage());
    }

    public static Result valueOfError(int code, String message) {
        return new Result(RS_FALSE_DEFAULT.state, code, message);
    }

    public static Result valueOfErrorMsg(String message) {
        return new Result(RS_FALSE_DEFAULT.state, RS_FALSE_DEFAULT.code, message);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
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

    public boolean isSuccess() {
        return this.state;
    }

    public boolean isFail() {
        return !this.isSuccess();
    }

    public Result formatMessage(String... arguments) {
        this.message = MessageFormat.format(this.message, arguments);
        return this;
    }

    public boolean equals(Object o) {
        if (o instanceof Result) {
            return ((Result) o).getCode() == this.getCode();
        } else {
            throw new RuntimeException("[Result-equals], o instanceof Result is false");
        }
    }

    public int hashCode() {
        String code = String.valueOf(this.getCode());
        return code.hashCode();
    }

    public String toString() {
        return "[code=" + this.getCode() + ";message=" + this.getMessage() + "]";
    }
}
