package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.dupreeinca.lib_api_rest.model.view.Profile;

import java.util.List;

/**
 * Created by cloudemotion on 27/8/17.
 */

public class DataAuth {
    private String status;
    private boolean valid;
    private String result;
    private List<Profile> perfil;
    private int code;
    private List<RaiseDTO> raise;

    public String getStatus() {
        return status;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }

    public boolean isValid() {
        return valid;
    }

    public String getResult() {
        return result;
    }

    public List<Profile> getPerfil() {
        return perfil;
    }

    public int getCode() {
        return code;
    }
}
