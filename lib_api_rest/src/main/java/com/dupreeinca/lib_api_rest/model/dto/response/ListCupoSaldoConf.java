package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemCupoSaldoConf;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cloudemotion on 21/8/17.
 */

public class ListCupoSaldoConf {
    private String status;
    private boolean valid;

    @SerializedName("result")
    private List<ItemCupoSaldoConf> cupoSaldoConfList;
    private int code;

    private List<RaiseDTO> raise;

    public ListCupoSaldoConf(String status, boolean valid, List<ItemCupoSaldoConf> cupoSaldoConfList, int code) {
        this.status = status;
        this.valid = valid;
        this.cupoSaldoConfList = cupoSaldoConfList;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public List<ItemCupoSaldoConf> getCupoSaldoConfList() {
        return cupoSaldoConfList;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
