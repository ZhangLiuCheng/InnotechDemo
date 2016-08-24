package com.innotech.demo.data.retrofit;

/**
 * Created by sunyuqin on 16/8/22.
 */

public class HttpException extends RuntimeException {

    private HttpResult result;

    public HttpException(HttpResult result) {
        this.result = result;
    }

    @Override
    public String getMessage() {
        return "接口错误:" + result.resultCode + " -- " + result.resultMessage;
    }

    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }
}
