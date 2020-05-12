package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by cloudemotion on 3/9/17.
 */

public class EstadoPrePedidoDTO {
    public static final String SHOW_PRDUCTS="1";

    private String status;
    private boolean valid;
    private ResultEdoPrePedido result;
    private int code;


    private List<RaiseDTO> raise;


    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public ResultEdoPrePedido getResult() {  return result;    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
