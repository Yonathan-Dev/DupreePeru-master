package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by marwuinh@gmail.com on 21/8/17.
 */

public class ItemBandeja {
    private String id_mensaje;
    private String mensaje;
    private String estado;
    private String fecha;
    private String imagen;

    private boolean ItemSelected=false;

    public boolean getItemSelected() {
        return ItemSelected;
    }

    public void setItemSelected(Boolean itemSelected) {
        ItemSelected = itemSelected;
    }

    public String getId_mensaje() {
        return id_mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getImagen() {
        return imagen;
    }
}
