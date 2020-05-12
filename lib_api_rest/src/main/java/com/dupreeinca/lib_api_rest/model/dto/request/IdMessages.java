package com.dupreeinca.lib_api_rest.model.dto.request;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 28/8/17.
 */

public class IdMessages {
    List<Integer> id_mensajes;

    public IdMessages(List<Integer> id_mensajes) {
        this.id_mensajes = id_mensajes;
    }

    public List<Integer> getId_mensajes() {
        return id_mensajes;
    }
}
