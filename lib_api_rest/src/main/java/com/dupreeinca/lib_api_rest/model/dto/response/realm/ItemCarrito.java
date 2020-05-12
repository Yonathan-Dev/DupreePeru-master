package com.dupreeinca.lib_api_rest.model.dto.response.realm;

/**
 * Created by marwuinh@gmail.com on 4/9/17.
 */

public class ItemCarrito{
    public final static int TYPE_CATALOGO = 1;
    public final static int TYPE_OFFERTS = 2;
    private String id;
    private String name;
    private String valor;
    private String valor_descuento;//solo ofertas
    private String url_img;
    private String page;
    private int cantidad;
    //control

    private int cantidadServer=0;
    private long time=0;//para control de fecha de item, agregado (o usar incremento), solo cataloog
    private int type=0;
    public ItemCarrito() {
    }

    public ItemCarrito(Oferta item){
        setId(item.getId());
        setName(item.getName());
        setValor(item.getValor());//en el carrito solo
        setValor_descuento(item.getValor_descuento());//importa el precio real
        setUrl_img(item.getUrl_img());
        setPage(item.getPage());
        setCantidad(item.getCantidad());
        setCantidadServer(item.getCantidadServer());
        setTime(item.getTime());
        setType(TYPE_OFFERTS);
    }

    public ItemCarrito(Catalogo item){
        setId(item.getId());
        setName(item.getName());
        setValor(item.getValor());
        setValor_descuento(item.getValor());
        setUrl_img(item.getUrl_img());
        setPage(item.getPage());
        setCantidad(item.getCantidad());
        setCantidadServer(item.getCantidadServer());
        setTime(item.getTime());
        setType(TYPE_CATALOGO);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor_descuento() {
        return valor_descuento;
    }

    public void setValor_descuento(String valor_descuento) {
        this.valor_descuento = valor_descuento;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadServer() {
        return cantidadServer;
    }

    public void setCantidadServer(int cantidadServer) {
        this.cantidadServer = cantidadServer;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
