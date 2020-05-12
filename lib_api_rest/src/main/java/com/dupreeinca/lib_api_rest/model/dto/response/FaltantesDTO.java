package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.Faltante;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;

import java.util.List;

/**
 * Created by cloudemotion on 20/8/17.
 */

public class FaltantesDTO {
    private String status;
    private boolean valid;
    private List<Faltante> result;
    private String campana;
    private int code;
    private List<RaiseDTO> raise;

    public FaltantesDTO(String status, boolean valid, List<Faltante> result, String campana, int code) {
        this.status = status;
        this.valid = valid;
        this.result = result;
        this.campana = campana;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public List<Faltante> getResult() {
        return result;
    }

    public String getCampana() {
        return campana;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
