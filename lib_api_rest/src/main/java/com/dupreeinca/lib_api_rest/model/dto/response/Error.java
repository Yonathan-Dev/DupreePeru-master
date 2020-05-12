package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 2/19/19.
 */

public class Error {
    private String status;
    private boolean valid;
    private int code;
    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
