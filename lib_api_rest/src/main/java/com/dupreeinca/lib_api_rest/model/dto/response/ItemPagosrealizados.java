package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 21/8/17.
 */

public class ItemPagosrealizados {
    private String fecha;
    private String banco;
    private double valor;
    private int fila;

    public ItemPagosrealizados(String fecha, String banco, double valor, int fila) {
        this.fecha = fecha;
        this.banco = banco;
        this.valor = valor;
        this.fila = fila;
    }

    public String getFecha() {
        return fecha;
    }

    public String getBanco() {
        return banco;
    }

    public double getValor() {
        return valor;
    }

    public int getFila() {
        return fila;
    }
}
