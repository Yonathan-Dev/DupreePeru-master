package com.dupreeinca.lib_api_rest.model.dto.request;

import com.dupreeinca.lib_api_rest.model.dto.request.SendPremios;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 2/9/17.
 */

public class RedimirPremios {
    private List<SendPremios> premios;

    public RedimirPremios(List<SendPremios> premios) {
        this.premios = premios;
    }
}
