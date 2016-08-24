package com.innotech.demo.data;

import com.innotech.demo.entity.Movie;
import com.innotech.demo.data.retrofit.HttpResultFunc;
import com.innotech.demo.data.service.MovieService;
import com.innotech.demo.data.retrofit.RetrofitManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by sunyuqin on 16/8/22.
 */
public class MovieModel {

    public static void getTopMoive(int start, int count, Subscriber<Movie> subscriber) {
        MovieService movieService = RetrofitManager.getRetrofit().create(MovieService.class);

        /*
        movieService.getTopMoive(start, count)
                .map(new HttpResultFunc<Movie>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
                */

        Observable observable = movieService.getTopMoive(start, count);
        RetrofitManager.toSubscribe(observable, subscriber);
    }
}
