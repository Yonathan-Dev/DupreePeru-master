package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by cloudemotion on 2/9/17.
 */

public class ListCDR {
    private String status;
    private boolean valid;
    private TitlesCDR result;
    private int code;

    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public TitlesCDR getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
