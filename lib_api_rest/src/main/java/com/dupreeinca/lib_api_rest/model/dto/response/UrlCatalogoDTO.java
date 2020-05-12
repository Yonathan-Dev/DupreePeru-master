package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 14/9/17.
 */

public class UrlCatalogoDTO {
    private String url;
    private String pdf;
    private String image;
    private String name;
    private String code;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getPdf() {
        return pdf;
    }

    public String getImage() {
        return image;
    }
}
