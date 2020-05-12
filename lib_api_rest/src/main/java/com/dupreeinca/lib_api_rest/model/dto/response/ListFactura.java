package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemFactura;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;

import java.util.List;

/**
 * Created by cloudemotion on 2/9/17.
 */

public class ListFactura {
    private String status;
    private boolean valid;
    private List<ItemFactura> result;
    private String asesora;
    private int code;

    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public List<ItemFactura> getResult() {
        return result;
    }

    public String getAsesora() {
        return asesora;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
