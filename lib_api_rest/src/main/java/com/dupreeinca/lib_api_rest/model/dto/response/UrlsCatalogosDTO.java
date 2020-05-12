package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by cloudemotion on 14/9/17.
 */

public class UrlsCatalogosDTO {
    private List<ItemCatalogoDTO> catalogos;

    public List<ItemCatalogoDTO> getCatalogos() {
        return catalogos;
    }

    public class ItemCatalogoDTO {
        private UrlCatalogoDTO catalogo1;
        private UrlCatalogoDTO catalogo2;
        private UrlCatalogoDTO catalogo3;

        public UrlCatalogoDTO getCatalogo1() {
            return catalogo1;
        }

        public UrlCatalogoDTO getCatalogo2() {
            return catalogo2;
        }

        public UrlCatalogoDTO getCatalogo3() {
            return catalogo3;
        }
    }
}
