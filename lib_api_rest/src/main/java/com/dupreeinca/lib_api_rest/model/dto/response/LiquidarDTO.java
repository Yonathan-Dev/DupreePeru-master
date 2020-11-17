package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.JsonArray;

import java.util.List;

/**
 * Created by cloudemotion on 12/9/17.
 */

public class LiquidarDTO {
    private String status;
    private boolean valid;
    private String result;
    private String mensaje;
    private String total_pedido;
    private String codigo;
    private int code;

    private String tota_pedi;
    JsonArray mensajes = new JsonArray();

    private List<RaiseDTO> raise;


    public LiquidarDTO() {
    }

    public LiquidarDTO(String codigo) {
        this.codigo = codigo;
    }

    public LiquidarDTO(String mensaje, String total_pedido, String codigo) {
        this.mensaje = mensaje;
        this.total_pedido = total_pedido;
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public String getResult() {
        return result;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTotal_pedido() {
        return total_pedido;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }

    public String getTota_pedi() {
        return tota_pedi;
    }

    public JsonArray getMensajes() {
        return mensajes;
    }
}
