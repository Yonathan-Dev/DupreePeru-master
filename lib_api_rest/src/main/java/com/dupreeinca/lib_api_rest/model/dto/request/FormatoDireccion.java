package com.dupreeinca.lib_api_rest.model.dto.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.dupreeinca.lib_api_rest.BR;
import com.dupreeinca.lib_api_rest.enums.EnumFormatDireccion;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;
import com.dupreeinca.lib_api_rest.util.models.ModelList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by marwuinh@gmail.com on 1/9/17.
 */

public class FormatoDireccion extends BaseObservable {
    //DIRECCION DERESIDENCIA
    private String direccion_concatenada = "";//

    private String tipo_via1 = "";//
    private String tipo_via2 = "";//

    private String numero1 = "";//
    private String numero2 = "";//
    private String numero3 = "";//
    private String numero4 = "";//

    private String letra1 = "";//
    private String letra2 = "";//
    private String letra3 = "";//

    private String bis1 = "";//
    private String bis2 = "";//

    private String pcardinal1 = "";//
    private String pcardinal2 = "";//

    private String complemento = "";//

    private String id_departamento = "";//
    private String departamento = "";//

    private String id_ciudad = "";//
    private String name_ciudad = "";//

    private String barrio = "";//
    private String id_barrio = "";//

    //control
    private transient boolean refValidated = false;
    private transient boolean showDirEnvio = false;
    private transient String formato_dir = "";

    public void loadDataInit(FormatoDireccion data, List<ModelList> listDirSend){
        //DIRECCION DE RESIDENCIA
        setDireccion_concatenada("");//se hace automatico

        setTipo_via1(data.getTipo_via1());
        setTipo_via2(data.getTipo_via2());

        setNumero1(data.getNumero1());
        setNumero2(data.getNumero2());
        setNumero3(data.getNumero3());
        setNumero4(data.getNumero4());

        setLetra1(data.getLetra1());
        setLetra2(data.getLetra2());
        setLetra3(data.getLetra3());

        setBis1(data.getBis1());
        setBis2(data.getBis2());

        setPcardinal1(data.getPcardinal1());
        setPcardinal2(data.getPcardinal2());

        setComplemento(data.getComplemento());

        setId_departamento(data.getId_departamento());
        setDepartamento(data.getDepartamento());

        setId_ciudad(data.getId_ciudad());
        setName_ciudad(data.getName_ciudad());

        setBarrio(data.getBarrio());
        setId_barrio(data.getId_barrio());

        //control
        setShowDirEnvio(false);
        setRefValidated(false);
        setFormato_dir(data.getFormato_dir());
    }

    public void clearData(){
        //DIRECCION DE RESIDENCIA
        setDireccion_concatenada("");

        setTipo_via1("");
        setTipo_via2("");

        setNumero1("");
        setNumero2("");
        setNumero3("");
        setNumero4("");

        setLetra1("");
        setLetra2("");
        setLetra3("");

        setBis1("");
        setBis2("");

        setPcardinal1("");
        setPcardinal2("");

        setComplemento("");

        setId_departamento("");
        setDepartamento("");

        setId_ciudad("");
        setName_ciudad("");

        setBarrio("");
        setId_barrio("");

    }

    @Bindable
    public String getTipo_via1() {
        return tipo_via1;
    }

    @Bindable
    public String getTipo_via2() {
        return tipo_via2;
    }

    @Bindable
    public String getNumero1() {
        return numero1;
    }

    @Bindable
    public String getLetra1() {
        return letra1;
    }

    @Bindable
    public boolean isEnableLetra1(){
        boolean result = Pattern.matches("[0-9]+", numero1);
        if(!result){
            setLetra1("");
        }
        return result;
    }

    @Bindable
    public String getBis1() {
        return bis1;
    }

    @Bindable
    public String getBis2() {
        return bis2;
    }

    @Bindable
    public String getPcardinal1() {
        return pcardinal1;
    }

    @Bindable
    public String getNumero2() {
        return numero2;
    }

    @Bindable
    public String getLetra2() {
        return letra2;
    }

    @Bindable
    public String getLetra3() {
        return letra3;
    }

    @Bindable
    public String getNumero3() {
        return numero3;
    }

    @Bindable
    public String getNumero4() {
        return numero4;
    }

    @Bindable
    public String getPcardinal2() {
        return pcardinal2;
    }

    @Bindable
    public String getComplemento() {
        return complemento;
    }

    @Bindable
    public String getDireccion_concatenada() {
        direccion_concatenada =
                tipo_via1 + " " +
                (numero1 + " ") +
                (letra1 + " ");

        if(formato_dir.equals(EnumFormatDireccion.FORMATO_1.getKey())){
            direccion_concatenada +=
                    (bis1 + " ") +
                    (letra2 + " ") +
                    (pcardinal1 + " ") +
                    (numero2 + " ") +
                    (letra3 + " ") +
                    (pcardinal2 + " ") +
                    complemento;
        } else if(formato_dir.equals(EnumFormatDireccion.FORMATO_2.getKey())){
            direccion_concatenada +=
                    (numero2 + " ") +
                    (pcardinal1 + " ") +
                    (numero3 + " ") +
                    (letra2 + " ") +
                    (numero4 + " ") +
                    (pcardinal2 + " ") +
                    complemento;
        } else if(formato_dir.equals(EnumFormatDireccion.FORMATO_3.getKey())){
            direccion_concatenada +=
                    (pcardinal1 + " ") +
                    (numero2 + " ") +
                    (letra2 + " ") +
                    (pcardinal2 + " ") +
                    complemento;
        } else if(formato_dir.equals(EnumFormatDireccion.FORMATO_4.getKey())){
            direccion_concatenada +=
                    (pcardinal1 + " ") +
                    (tipo_via2 + " ") +
                    (numero2 + " ") +
                    (letra2 + " ") +
                    (pcardinal2 + " ") +
                    complemento;
        }

        return direccion_concatenada;
    }

    @Bindable
    public String getDepartamento() {
        return departamento;
    }

    @Bindable
    public String getId_ciudad() {
        return id_ciudad;
    }

    @Bindable
    public String getName_ciudad() {
        return name_ciudad;
    }

    @Bindable
    public String getBarrio() {
        return barrio;
    }

    @Bindable
    public String getId_barrio() {
        return id_barrio;
    }



    @Bindable
    public boolean isShowDirEnvio() {
        return showDirEnvio;
    }

    @Bindable
    public boolean isRefValidated() {
        return refValidated;
    }

    @Bindable
    public String getId_departamento() {
        return id_departamento;
    }

    public void setDireccion_concatenada(String direccion_concatenada) {
        this.direccion_concatenada = direccion_concatenada;
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setTipo_via1(String tipo_via1) {
        this.tipo_via1 = tipo_via1;
        notifyPropertyChanged(BR.tipo_via1);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setTipo_via2(String tipo_via2) {
        this.tipo_via2 = tipo_via2;
        notifyPropertyChanged(BR.tipo_via2);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
        notifyPropertyChanged(BR.numero1);
        notifyPropertyChanged(BR.enableLetra1);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setLetra1(String letra1) {
        this.letra1 = letra1;
        notifyPropertyChanged(BR.letra1);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setBis1(String bis1) {
        this.bis1 = bis1;
        notifyPropertyChanged(BR.bis1);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setBis2(String bis2) {
        this.bis2 = bis2;
        notifyPropertyChanged(BR.bis2);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setPcardinal1(String pcardinal1) {
        this.pcardinal1 = pcardinal1;
        notifyPropertyChanged(BR.pcardinal1);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
        notifyPropertyChanged(BR.numero2);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setLetra2(String letra2) {
        this.letra2 = letra2;
        notifyPropertyChanged(BR.letra2);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setLetra3(String letra3) {
        this.letra3 = letra3;
        notifyPropertyChanged(BR.letra3);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setNumero3(String numero3) {
        this.numero3 = numero3;
        notifyPropertyChanged(BR.numero3);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setNumero4(String numero4) {
        this.numero4 = numero4;
        notifyPropertyChanged(BR.numero4);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setPcardinal2(String pcardinal2) {
        this.pcardinal2 = pcardinal2;
        notifyPropertyChanged(BR.pcardinal2);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
        notifyPropertyChanged(BR.complemento);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
        notifyPropertyChanged(BR.departamento);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setId_ciudad(String id_ciudad) {
        this.id_ciudad = id_ciudad;
        notifyPropertyChanged(BR.id_ciudad);
    }

    public void setName_ciudad(String name_ciudad) {
        this.name_ciudad = name_ciudad;
        notifyPropertyChanged(BR.name_ciudad);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
        notifyPropertyChanged(BR.barrio);
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setId_barrio(String id_barrio) {
        this.id_barrio = id_barrio;
        notifyPropertyChanged(BR.id_barrio);
    }

    public void setId_departamento(String id_departamento) {
        this.id_departamento = id_departamento;
        notifyPropertyChanged(BR.id_departamento);
    }

    public void setShowDirEnvio(boolean showDirEnvio) {
        this.showDirEnvio = showDirEnvio;
        notifyPropertyChanged(BR.showDirEnvio);
    }

    public void setRefValidated(boolean refValidated) {
        this.refValidated = refValidated;
        notifyPropertyChanged(BR.refValidated);
    }

    @Bindable
    public String getFormato_dir() {
        return formato_dir;
    }

    public void setFormato_dir(String formato_dir) {
        this.formato_dir = formato_dir;
        notifyPropertyChanged(BR.formato_dir);
    }
}
