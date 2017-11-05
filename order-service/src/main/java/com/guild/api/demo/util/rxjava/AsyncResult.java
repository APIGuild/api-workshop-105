package com.guild.api.demo.util.rxjava;

public final class AsyncResult<V> {
    private final V value;
    private final Exception exception;

    private AsyncResult(V value, Exception exception) {
        this.value = value;
        this.exception = exception;
    }

    public static <V> AsyncResult<V> success(V value) {
        return new AsyncResult<>(value, null);
    }

    public static <V> AsyncResult<V> failed(Exception exception) {
        return new AsyncResult<>(null, exception);
    }

    public V getValue() {
        return value;
    }

    public Exception getException() {
        return exception;
    }

    public boolean hasException() {
        return exception != null;
    }
}
