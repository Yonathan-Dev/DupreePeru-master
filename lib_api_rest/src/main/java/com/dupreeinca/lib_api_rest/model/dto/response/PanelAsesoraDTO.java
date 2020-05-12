package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.PanelAsesora;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cloudemotion on 1/9/17.
 */

public class PanelAsesoraDTO {
    private String status;
    private boolean valid;
    @SerializedName("result")
    private PanelAsesora panelAsesora;
    private int code;

    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public PanelAsesora getPanelAsesora() {
        return panelAsesora;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
