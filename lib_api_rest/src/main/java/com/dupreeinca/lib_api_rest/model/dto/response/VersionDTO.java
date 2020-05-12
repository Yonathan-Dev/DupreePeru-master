package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 4/27/18.
 */

public class VersionDTO {
    private String version = "";

    public VersionDTO(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
