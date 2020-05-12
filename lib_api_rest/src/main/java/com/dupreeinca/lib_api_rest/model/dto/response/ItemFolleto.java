package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by marwuinh@gmail.com on 3/13/19.
 */

public class ItemFolleto {
    private String code;//se usa en la db
    private String name;
    private String image;
    private String url;
    private String pdf;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getPdf() {
        return pdf;
    }
}
