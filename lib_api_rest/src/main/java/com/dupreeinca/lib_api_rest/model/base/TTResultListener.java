package com.dupreeinca.lib_api_rest.model.base;

public interface TTResultListener<T> {

    void success(T result);
    void error(TTError error);
}
