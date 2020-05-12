package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ListaPuntos;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cloudemotion on 2/9/17.
 */

public class PuntosAsesoraDTO {
    private String status;
    private boolean valid;
    @SerializedName("result")
    private ListaPuntos result;
    private int code;
    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public ListaPuntos getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
