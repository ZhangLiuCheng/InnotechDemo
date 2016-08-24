package com.innotech.demo.data.retrofit;

import rx.functions.Func1;

/**
 * Created by sunyuqin on 16/8/22.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.resultCode == 0) {
            throw new HttpException(httpResult);
        }
        return httpResult.t;
    }
}
