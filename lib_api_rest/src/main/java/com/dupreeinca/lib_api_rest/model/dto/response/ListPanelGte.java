package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ListItemPanelGte;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cloudemotion on 30/8/17.
 */

public class ListPanelGte {
    public String status;
    public Boolean valid;
    @SerializedName("result")
    public ListItemPanelGte listDetail;
    public int code;
    public List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public Boolean getValid() {
        return valid;
    }

    public ListItemPanelGte getListDetail() {
        return listDetail;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
