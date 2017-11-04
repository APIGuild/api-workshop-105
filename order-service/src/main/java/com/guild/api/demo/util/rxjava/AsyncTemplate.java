package com.guild.api.demo.util.rxjava;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.schedulers.Schedulers;

public final class AsyncTemplate {
    public static <T> Single<AsyncResult<T>> async(Callable<T> callable) {
        return Single.create((SingleEmitter<AsyncResult<T>> singleEmitter) -> {
            AsyncResult<T> asyncResult;
            try {
                T syncResult = callable.call();
                asyncResult = AsyncResult.success(syncResult);
            } catch (Exception exception) {
                asyncResult = AsyncResult.failed(exception);
            }
            singleEmitter.onSuccess(asyncResult);
        }).subscribeOn(Schedulers.io());
    }
}



