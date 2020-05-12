package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 26/8/17.
 */

public class BarrioDTO {
    private String id_barrio;
    private String name_barrio;

    public BarrioDTO(String id_barrio, String name_barrio) {
        this.id_barrio = id_barrio;
        this.name_barrio = name_barrio;
    }

    public String getId_barrio() {
        return id_barrio;
    }

    public String getName_barrio() {
        return name_barrio;
    }

    public void setId_barrio(String id_barrio) {
        this.id_barrio = id_barrio;
    }

    public void setName_barrio(String name_barrio) {
        this.name_barrio = name_barrio;
    }
}
