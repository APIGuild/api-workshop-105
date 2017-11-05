package com.guild.api.demo.util.rxjava;

import java.util.concurrent.Callable;

import rx.Single;
import rx.SingleSubscriber;
import rx.schedulers.Schedulers;

public final class AsyncTemplate {
    public static <T> Single<AsyncResult<T>> async(Callable<T> callable) {
        return Single.create((SingleSubscriber<? super AsyncResult<T>> subscriber) -> {
            AsyncResult<T> asyncResult;
            try {
                T syncResult = callable.call();
                asyncResult = AsyncResult.success(syncResult);
            } catch (Exception exception) {
                asyncResult = AsyncResult.failed(exception);
            }
            subscriber.onSuccess(asyncResult);
        }).subscribeOn(Schedulers.io());
    }
}



