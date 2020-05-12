package com.dupreeinca.lib_api_rest.interceptors;

import java.io.IOException;

/**
 * Created by marwuinh@gmail.com on 2/13/18.
 */

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}
