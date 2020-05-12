package com.dupreeinca.lib_api_rest.model.base;

import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mdagostino on 7/18/16.
 */
public class TTError implements Serializable {

    private String title = "";

    private String message = "";

    private List<RaiseDTO> raise;
    private String errorBody = "";

    @SerializedName("status_code")
    private Integer statusCode;

    private String codigo = ""; //solventa detalles BE (Liquidar Pedido)
    private String total_pedido = ""; //solventa detalles BE (Liquidar Pedido)

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static TTError errorFromMessage(String message){
        TTError result = new TTError();
        result.setMessage(message);
        return result;
    }

    public static TTError errorFromMessageAndTitle(String message, String title){
        TTError result = TTError.errorFromMessage(message);
        result.setTitle(title);
        return result;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }

    public void setRaise(List<RaiseDTO> raise) {
        this.raise = raise;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTotal_pedido() {
        return total_pedido;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setTotal_pedido(String total_pedido) {
        this.total_pedido = total_pedido;
    }
}
