package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;

import java.util.List;

/**
 * Created by cloudemotion on 28/8/17.
 */

public class GenericDTO {
    String status;
    Boolean valid;
    String result;
    int code;
    List<RaiseDTO> raise;


    public String getStatus() {
        return status;
    }

    public Boolean getValid() {
        return valid;
    }

    public String getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
