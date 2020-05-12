package com.dupreeinca.lib_api_rest.model.dto.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.dupreeinca.lib_api_rest.BR;
import com.dupreeinca.lib_api_rest.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cloudemotion on 1/9/17.
 */

public class Referencia extends BaseObservable {
    private String tipo_documento_id = "";
    private String cedula = "";
    private String nombre = "";//
    private String apellido = "";//
    private String celular = "";//
    private String telefono = "";//
    private String parentesco = "";//
    private transient String name_parentesco = "";//



    public void loadDataInit(Referencia data, List<String> parentescos){
        setTipo_documento_id("");
        setCedula("");
        setNombre(data.getNombre());
        setApellido(data.getApellido());
        setCelular(data.getCelular());
        setTelefono(data.getTelefono());
        setParentesco(data.getParentesco());

        int parent1 = Integer.parseInt(
                data.getParentesco().isEmpty() ? "0" : data.getParentesco()
        );
        setName_parentesco(parentescos.get(
                parent1 < 0 ? 0 : parent1
        ));
    }

    public void clearData(){
        setTipo_documento_id("");
        setCedula("");
        setNombre("");
        setApellido("");
        setCelular("");
        setTelefono("");
        setParentesco("");
        setName_parentesco("");
    }

    public Referencia() {
    }

    public void setTipo_documento_id(String tipo_documento_id) {
        this.tipo_documento_id = tipo_documento_id;
        notifyPropertyChanged(BR.tipo_documento_id);
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
        notifyPropertyChanged(BR.cedula);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(BR.nombre);
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
        notifyPropertyChanged(BR.apellido);
    }

    public void setCelular(String celular) {
        this.celular = celular;
        notifyPropertyChanged(BR.celular);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        notifyPropertyChanged(BR.telefono);
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
        notifyPropertyChanged(BR.parentesco);
    }
    public void setName_parentesco(String name_parentesco) {
        this.name_parentesco = name_parentesco;
        notifyPropertyChanged(BR.name_parentesco);
    }

    @Bindable
    public String getTipo_documento_id() {
        return tipo_documento_id;
    }

    @Bindable
    public String getCedula() {
        return cedula;
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    @Bindable
    public String getApellido() {
        return apellido;
    }

    @Bindable
    public String getCelular() {
        return celular;
    }

    @Bindable
    public String getTelefono() {
        return telefono;
    }

    @Bindable
    public String getParentesco() {
        return parentesco;
    }

    @Bindable
    public String getName_parentesco() {
        return name_parentesco;
    }

}
