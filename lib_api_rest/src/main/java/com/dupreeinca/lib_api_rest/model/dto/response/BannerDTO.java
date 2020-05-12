package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by cloudemotion on 25/8/17.
 */

public class BannerDTO {
    private List<ImgBannerDTO> banner;
    private List<TipoViaDTO> tipo_via;
    private List<DepartamentoDTO> departamentos;
    private String img_catalogo;
    private String url_chat;
    private String version;

    public List<ImgBannerDTO> getBanner() {
        return banner;
    }

    public List<TipoViaDTO> getTipo_via() {
        return tipo_via;
    }

    public List<DepartamentoDTO> getDepartamentos() {
        return departamentos;
    }

    public String getImg_catalogo() {
        return img_catalogo;
    }

    public String getUrl_chat() {
        return url_chat;
    }

    public String getVersion() {
        return version;
    }
}
