package com.innotech.demo.data.service;

import com.innotech.demo.entity.Movie;
import com.innotech.demo.data.retrofit.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sunyuqin on 16/8/22.
 */

public interface MovieService {

    @GET("top250")
    Observable<HttpResult<Movie>> getTopMoive(@Query("start") int start, @Query("count") int count);
}
