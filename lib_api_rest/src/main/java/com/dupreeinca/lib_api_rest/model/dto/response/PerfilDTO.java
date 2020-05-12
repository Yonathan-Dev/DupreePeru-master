package com.dupreeinca.lib_api_rest.model.dto.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 30/8/17.
 */

public class PerfilDTO {
    private String status;
    private Boolean valid;
    @SerializedName("result")
    private List<DataUser> perfilList;
    private int code;

    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public Boolean getValid() {
        return valid;
    }

    public List<DataUser> getPerfilList() {
        return perfilList;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
