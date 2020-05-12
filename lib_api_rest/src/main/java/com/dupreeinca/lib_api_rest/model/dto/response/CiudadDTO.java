package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 25/8/17.
 */

public class CiudadDTO {
    private String id_ciudad;
    private String name_ciudad;

    public CiudadDTO(String id_ciudad, String name_ciudad) {
        this.id_ciudad = id_ciudad;
        this.name_ciudad = name_ciudad;
    }

    public String getId_ciudad() {
        return id_ciudad;
    }

    public String getName_ciudad() {
        return name_ciudad;
    }

    public void setId_ciudad(String id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public void setName_ciudad(String name_ciudad) {
        this.name_ciudad = name_ciudad;
    }
}
