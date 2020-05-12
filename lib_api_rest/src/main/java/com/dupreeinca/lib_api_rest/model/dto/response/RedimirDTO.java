package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ListPremios;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;

import java.util.List;

/**
 * Created by cloudemotion on 1/9/17.
 */

public class RedimirDTO {
    private String status;
    private boolean valid;
    private ListPremios result;
    private int code;
    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public ListPremios getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
