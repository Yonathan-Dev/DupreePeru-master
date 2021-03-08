package com.dupreeinca.lib_api_rest.model.dto.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

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

public class InscriptionDTO extends BaseObservable implements Parcelable {
    //REFERENCIADO POR
    private String referenciado_por = "";//
    //solo para edicion de inscrip rechazada
    private transient String referenciado_hint = "";//
    private transient String referenciado_nombre = "";//

    //DATOS PERSONALES
    private String cedula = "";//
    private transient String nombre = "";//
    private String nacimiento = "";//
    private String zona_seccion = "";//

    //DIRECCION DERESIDENCIA
    private String direccion_concatenada = "";//

    private String tipo_via1 = "";//
    private String tipo_via2 = "";//

    private String numero1 = "";//
    private String numero2 = "";//
    private String numero3 = "";//
    private String numero4 = "";//
    private String numero5 = "";//

    private String letra1 = "";//
    private String letra2 = "";//
    private String letra3 = "";//

    private String bis1 = "";//
    private String bis2 = "";//

    private String pcardinal1 = "";//
    private String pcardinal2 = "";//

    private String complemento = "";//
    private String direreferencia  = "";

    private String id_departamento = "";//
    private String departamento = "";//

    private String id_ciudad = "";//
    private String name_ciudad = "";//

    private String barrio = "";//
    private String id_barrio = "";//

    //DIRECCION DE ENVIO
    private transient String spnDirEnvio = "";//control

    private String direccion_concatenada_env = "";//

    private String tipo_via_env_1 = "";//
    private String tipo_via_env_2 = "";//

    private String numero_env_1 = "";//
    private String numero_env_2 = "";//
    private String numero_env_3 = "";//
    private String numero_env_4 = "";//
    private String numero_env_5 = "";//

    private String letra_env_1 = "";//
    private String letra_env_2 = "";//
    private String letra_env_3 = "";//

    private String bis_env_1 = "";//
    private String bis_env_2 = "";//

    private String pcardinal_env_1 = "";//
    private String pcardinal_env_2 = "";//

    private String complemento_env = "";//
    private String direreferencia_env = "";//

    private String id_departamento_env = "";//
    private String departamento_env = "";//

    private String id_ciudad_env = "";//
    private String name_ciudad_env = "";//

    private String barrio_env = "";//
    private String id_barrio_env = "";//

    //CONTACTO
    private String telefono = "";//
    private String celular = "";//
    private String correo = "";//
    private String imei = "";

    //IMAGENES
    private transient String cedula_frontal = "";//
    private transient String cedula_adverso = "";//
    private transient String cedula_adverso2 = "";//
    private transient String pagare_frontal = "";//
    private transient String pagare_adverso = "";//
    private List<String> img_cedula = new ArrayList<>();//
    private List<String> pagare = new ArrayList<>();

    //img terminos
    private String img_terminos = "";

    //img ruc
    private String img_ruc = "";

    //img solicitu adversa 2
    private String img_back2 = "";

    //img central de riesgo
    private String img_central = "";

    //datos personales preinscripcion
    private String nomb_terc = "";
    private String apel_terc = "";

    //REFERENCIAS
    private List<Referencia> referencia = new ArrayList<>();//

    //CONTROL
    private transient boolean refValidated = false;
    private transient boolean showDirEnvio = false;
    private transient String formato_direccion = "";
    private transient boolean modeEdit = false;

    //REFERENCIADO POR O NOMBRE REFERENCIADO
    private int code;
    private List<RaiseDTO> raise;

    public InscriptionDTO() {
        referencia = new ArrayList<>();
        referencia.add(new Referencia());
        referencia.add(new Referencia());

        img_cedula = new ArrayList<>();
        img_cedula.add("");
        img_cedula.add("");

        pagare = new ArrayList<>();
        pagare.add("");
        pagare.add("");
    }

    protected InscriptionDTO(Parcel in) {
        referenciado_por = in.readString();
        cedula = in.readString();
        nacimiento = in.readString();
        zona_seccion = in.readString();
        direccion_concatenada = in.readString();
        tipo_via1 = in.readString();
        tipo_via2 = in.readString();
        numero1 = in.readString();
        numero2 = in.readString();
        numero3 = in.readString();
        numero4 = in.readString();
        numero5 = in.readString();
        letra1 = in.readString();
        letra2 = in.readString();
        letra3 = in.readString();
        bis1 = in.readString();
        bis2 = in.readString();
        pcardinal1 = in.readString();
        pcardinal2 = in.readString();
        complemento = in.readString();
        direreferencia = in.readString();
        id_departamento = in.readString();
        departamento = in.readString();
        id_ciudad = in.readString();
        name_ciudad = in.readString();
        barrio = in.readString();
        id_barrio = in.readString();
        direccion_concatenada_env = in.readString();
        tipo_via_env_1 = in.readString();
        tipo_via_env_2 = in.readString();
        numero_env_1 = in.readString();
        numero_env_2 = in.readString();
        numero_env_3 = in.readString();
        numero_env_4 = in.readString();
        numero_env_5 = in.readString();
        letra_env_1 = in.readString();
        letra_env_2 = in.readString();
        letra_env_3 = in.readString();
        bis_env_1 = in.readString();
        bis_env_2 = in.readString();
        pcardinal_env_1 = in.readString();
        pcardinal_env_2 = in.readString();
        complemento_env = in.readString();
        direreferencia_env = in.readString();
        id_departamento_env = in.readString();
        departamento_env = in.readString();
        id_ciudad_env = in.readString();
        name_ciudad_env = in.readString();
        barrio_env = in.readString();
        id_barrio_env = in.readString();
        telefono = in.readString();
        celular = in.readString();
        correo = in.readString();
        imei = in.readString();
        img_cedula = in.createStringArrayList();
        pagare = in.createStringArrayList();
        code = in.readInt();
        //img terminos
        img_terminos = in.readString();
        img_ruc = in.readString();
        img_back2 = in.readString();
        img_central = in.readString();
        //datos personales preinscripcion
        nomb_terc = in.readString();
        apel_terc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(referenciado_por);
        dest.writeString(cedula);
        dest.writeString(nacimiento);
        dest.writeString(zona_seccion);
        dest.writeString(direccion_concatenada);
        dest.writeString(tipo_via1);
        dest.writeString(tipo_via2);
        dest.writeString(numero1);
        dest.writeString(numero2);
        dest.writeString(numero3);
        dest.writeString(numero4);
        dest.writeString(numero5);
        dest.writeString(letra1);
        dest.writeString(letra2);
        dest.writeString(letra3);
        dest.writeString(bis1);
        dest.writeString(bis2);
        dest.writeString(pcardinal1);
        dest.writeString(pcardinal2);
        dest.writeString(complemento);
        dest.writeString(direreferencia);
        dest.writeString(id_departamento);
        dest.writeString(departamento);
        dest.writeString(id_ciudad);
        dest.writeString(name_ciudad);
        dest.writeString(barrio);
        dest.writeString(id_barrio);
        dest.writeString(direccion_concatenada_env);
        dest.writeString(tipo_via_env_1);
        dest.writeString(tipo_via_env_2);
        dest.writeString(numero_env_1);
        dest.writeString(numero_env_2);
        dest.writeString(numero_env_3);
        dest.writeString(numero_env_4);
        dest.writeString(numero_env_5);
        dest.writeString(letra_env_1);
        dest.writeString(letra_env_2);
        dest.writeString(letra_env_3);
        dest.writeString(bis_env_1);
        dest.writeString(bis_env_2);
        dest.writeString(pcardinal_env_1);
        dest.writeString(pcardinal_env_2);
        dest.writeString(complemento_env);
        dest.writeString(direreferencia_env);
        dest.writeString(id_departamento_env);
        dest.writeString(departamento_env);
        dest.writeString(id_ciudad_env);
        dest.writeString(name_ciudad_env);
        dest.writeString(barrio_env);
        dest.writeString(id_barrio_env);
        dest.writeString(telefono);
        dest.writeString(celular);
        dest.writeString(correo);
        dest.writeString(imei);
        dest.writeStringList(img_cedula);
        dest.writeStringList(pagare);
        dest.writeInt(code);
        //img terminos
        dest.writeString(img_terminos);
        dest.writeString(img_ruc);
        dest.writeString(img_back2);
        dest.writeString(img_central);
        //datos personales preinscripcion
        dest.writeString(nomb_terc);
        dest.writeString(apel_terc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InscriptionDTO> CREATOR = new Creator<InscriptionDTO>() {
        @Override
        public InscriptionDTO createFromParcel(Parcel in) {
            return new InscriptionDTO(in);
        }

        @Override
        public InscriptionDTO[] newArray(int size) {
            return new InscriptionDTO[size];
        }
    };

    public void loadDataInit(InscriptionDTO data, List<ModelList> listDirSend, List<String> listP){
        //REFERENCIADO POR
        setReferenciado_por(data.getReferenciado_por());
        setReferenciado_nombre(data.getReferenciado_nombre());
        //  setReferenciado_hint(data.getReferenciado_nombre());

        //DATOS PERSONALES
        setCedula(data.getCedula());
        //setNombre(data.getNombre());
        setNacimiento(data.getNacimiento());
        setZona_seccion(data.getZona_seccion());

        //DIRECCION DE RESIDENCIA
        setDireccion_concatenada("");//se hace automatico

        setTipo_via1(data.getTipo_via1());
        setTipo_via2(data.getTipo_via2());

        setNumero1(data.getNumero1());
        setNumero2(data.getNumero2());
        setNumero3(data.getNumero3());
        setNumero4(data.getNumero4());
        setNumero5(data.getNumero5());

        setLetra1(data.getLetra1());
        setLetra2(data.getLetra2());
        setLetra3(data.getLetra3());

        setBis1(data.getBis1());
        setBis2(data.getBis2());

        setPcardinal1(data.getPcardinal1());
        setPcardinal2(data.getPcardinal2());

        setComplemento(data.getComplemento());
        setDirereferencia(data.getDirereferencia());

        setId_departamento(data.getId_departamento());
        setDepartamento(data.getDepartamento());

        setId_ciudad(data.getId_ciudad());
        setName_ciudad(data.getName_ciudad());

        setBarrio(data.getBarrio());
        setId_barrio(data.getId_barrio());


        //DIRECCION DE ENVIO
        setSpnDirEnvio(listDirSend.get(data.getDireccion_concatenada_env().isEmpty() ? 0 : 1).getName() );
        setShowDirEnvio(!data.getDireccion_concatenada_env().isEmpty());

        setDireccion_concatenada_env("");//se hace automatico

        setTipo_via_env_1(data.getTipo_via_env_1());
        setTipo_via_env_2(data.getTipo_via_env_2());

        setNumero_env_1(data.getNumero_env_1());
        setNumero_env_2(data.getNumero_env_2());
        setNumero_env_3(data.getNumero_env_3());
        setNumero_env_4(data.getNumero_env_4());
        setNumero_env_5(data.getNumero_env_5());

        setLetra_env_1(data.getLetra_env_1());
        setLetra_env_2(data.getLetra_env_2());
        setLetra_env_3(data.getLetra_env_3());

        setBis_env_1(data.getBis_env_1());
        setBis_env_2(data.getBis_env_2());

        setPcardinal_env_1(data.getPcardinal_env_1());
        setPcardinal_env_2(data.getPcardinal_env_2());

        setComplemento_env(data.getComplemento_env());
        setDirereferencia_env(data.getDirereferencia_env());

        setId_departamento_env(data.getId_departamento_env());
        setDepartamento_env(data.getDepartamento_env());

        setId_ciudad_env(data.getId_ciudad_env());
        setName_ciudad_env(data.getName_ciudad_env());

        setBarrio_env(data.getBarrio_env());
        setId_barrio_env(data.getId_barrio_env());


        //CONTACTOS
        setTelefono(data.getTelefono());
        setCelular(data.getCelular());
        setCorreo(data.getCorreo());
        setImei(data.getImei());

        //DOCUMENTOS ADJUNTOS
        setCedula_frontal(data.getImg_cedula().get(0));
        setCedula_adverso(data.getImg_cedula().get(1));
        setPagare_frontal(data.getPagare().get(0));
        setPagare_adverso(data.getPagare().get(1));

        List<String> listC = new ArrayList<>();
        listC.add(getCedula_frontal());
        listC.add(getCedula_adverso());
        setImg_cedula(listC);
        List<String> listPag = new ArrayList<>();
        listPag.add(getPagare_frontal());
        listPag.add(getPagare_adverso());
        setPagare(listPag);

        //REFERENCIAS
        List<Referencia> list2 = new ArrayList<>();

        Referencia modelRefPersonal = new Referencia();
        Referencia modelRefFamiliar = new Referencia();
        modelRefPersonal.loadDataInit(data.getReferencia().get(0), listP);
        modelRefFamiliar.loadDataInit(data.getReferencia().get(1), listP);

        list2.add(modelRefPersonal);
        list2.add(modelRefFamiliar);
        setReferencia(list2);

        setShowDirEnvio(false);
        setRefValidated(false);

        //img terminos
        setImg_terminos(data.getImg_terminos());
        setImg_ruc(data.getImg_ruc());
        setImg_back2(data.getImg_back2());
        setImg_central(data.getImg_central());
        setNomb_terc(data.getNomb_terc());
        setApel_terc(data.getApel_terc());

//        setFormato_direccion(data.getFormato_direccion());
    }

    public void clearData(){
        //REFERENCIADO POR
        setReferenciado_por("");
        setReferenciado_nombre("");
        setReferenciado_hint("");
        //DATOS PERSONALES
        setCedula("");
        setNombre("");
        setNacimiento("");
        setZona_seccion("");

        //DIRECCION DE RESIDENCIA
        setDireccion_concatenada("");

        setTipo_via1("");
        setTipo_via2("");

        setNumero1("");
        setNumero2("");
        setNumero3("");
        setNumero4("");
        setNumero5("");

        setLetra1("");
        setLetra2("");
        setLetra3("");

        setBis1("");
        setBis2("");

        setPcardinal1("");
        setPcardinal2("");

        setComplemento("");
        setDirereferencia("");

        setId_departamento("");
        setDepartamento("");

        setId_ciudad("");
        setName_ciudad("");

        setBarrio("");
        setId_barrio("");

        //DIRECCION DE ENVIO
        setSpnDirEnvio("");//control
        setDireccion_concatenada_env("");

        setTipo_via_env_1("");
        setTipo_via_env_2("");

        setNumero_env_1("");
        setNumero_env_2("");
        setNumero_env_3("");
        setNumero_env_4("");
        setNumero_env_5("");

        setLetra_env_1("");
        setLetra_env_2("");
        setLetra_env_3("");

        setBis_env_1("");
        setBis_env_2("");

        setPcardinal_env_1("");
        setPcardinal_env_2("");

        setComplemento_env("");
        setDirereferencia_env("");

        setId_departamento_env("");//
        setDepartamento_env("");

        setId_ciudad_env("");
        setName_ciudad_env("");

        setBarrio_env("");
        setId_barrio_env("");

        //CONTACTOS
        setTelefono("");
        setCelular("");
        setCorreo("");
        setImei("5453693683636934");

        //DOCUMENTOS ADJUNTOS
        setCedula_frontal("");
        setCedula_adverso("");
        setPagare_frontal("");
        setPagare_adverso("");

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        setImg_cedula(list);
        setPagare(list);

        //REFERENCIAS
        List<Referencia> list2 = new ArrayList<>();
        list2.add(new Referencia());
        list2.add(new Referencia());
        setReferencia(list2);

        setShowDirEnvio(false);
        setRefValidated(false);

        setImg_terminos("");
        setImg_ruc("");
        setImg_back2("");
        setImg_central("");
        setApel_terc("");
        setNomb_terc("");
    }


    @Bindable
    public String getSpnDirEnvio() {
        return spnDirEnvio;
    }

    @Bindable
    public String getCedula() {
        return cedula;
    }

    @Bindable
    public String getNacimiento() {
        return nacimiento;
    }

    @Bindable
    public String getZona_seccion() {
        return zona_seccion;
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
    public String getLetra_env_3() {
        return letra_env_3;
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
    public String getNumero5() {
        return numero5;
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
    public String getDirereferencia() {
        return direreferencia;
    }

    @Bindable
    public String getDireccion_concatenada() {

        if (formato_direccion.equals(EnumFormatDireccion.FORMATO_1.getKey())) {
            if (numero1.equalsIgnoreCase("")){
                direccion_concatenada =
                        (tipo_via1 + " ") + (complemento);
            } else {
                direccion_concatenada =
                        (tipo_via1 + " ") +
                                (numero1 + " ") +
                                (letra1 + " ") +
                                (bis1 + " ") +
                                (letra2 + " ") +
                                (pcardinal1 + " ") +
                                (numero2 + " ") +
                                (letra3 + " ") +
                                (numero3 + " ") +
                                (pcardinal2 + " ") +
                                (complemento);
            }

        }

        if (formato_direccion.equals(EnumFormatDireccion.FORMATO_2.getKey())) {
            direccion_concatenada =
                    (tipo_via1 + " ") +
                            (numero1 + " ") +
                            (bis1 + " ") +
                            (letra1 + " ") +
                            (numero2 + " ") +
                            (pcardinal1 + " ") +
                            (numero3 + " ") +
                            (bis2 + " ") +
                            (letra2 + " ") +
                            (numero4 + " ") +
                            (numero5 + " ") +
                            (pcardinal2 + " ") +
                            (complemento);
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
    public String getTelefono() {
        return telefono;
    }

    @Bindable
    public String getCelular() {
        return celular;
    }

    @Bindable
    public String getCorreo() {
        return correo;
    }

    @Bindable
    public String getImei() {
        return imei;
    }

    @Bindable
    public String getReferenciado_por() {
        return referenciado_por;
    }

    @Bindable
    public List<String> getImg_cedula() {
        return img_cedula;
    }

    @Bindable
    public List<String> getPagare() {
        return pagare;
    }

    @Bindable
    public String getReferenciado_nombre() {
        return referenciado_nombre;
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    @Bindable
    public String getTipo_via_env_1() {
        return tipo_via_env_1;
    }

    @Bindable
    public String getTipo_via_env_2() {
        return tipo_via_env_2;
    }


    @Bindable
    public String getNumero_env_1() {
        return numero_env_1;
    }

    @Bindable
    public String getLetra_env_1() {
        return letra_env_1;
    }

    @Bindable
    public boolean isEnableLetra_env_1(){
        boolean result = Pattern.matches("[0-9]+", numero_env_1);
        if(!result){
            setLetra_env_1("");
        }
        return result;
    }

    @Bindable
    public String getBis_env_1() {
        return bis_env_1;
    }

    @Bindable
    public String getBis_env_2() {
        return bis_env_2;
    }

    @Bindable
    public String getPcardinal_env_2() {
        return pcardinal_env_2;
    }

    @Bindable
    public String getNumero_env_2() {
        return numero_env_2;
    }

    @Bindable
    public String getLetra_env_2() {
        return letra_env_2;
    }

    @Bindable
    public String getNumero_env_3() {
        return numero_env_3;
    }

    @Bindable
    public String getNumero_env_4() {
        return numero_env_4;
    }

    @Bindable
    public String getNumero_env_5() {
        return numero_env_5;
    }

    @Bindable
    public String getPcardinal_env_1() {
        return pcardinal_env_1;
    }

    @Bindable
    public String getComplemento_env() {
        return complemento_env;
    }

    @Bindable
    public String getDirereferencia_env() {
        return direreferencia_env;
    }

    @Bindable
    public String getDireccion_concatenada_env() {
        if(formato_direccion.equals(EnumFormatDireccion.FORMATO_1.getKey())) {
            direccion_concatenada_env = tipo_via_env_1 + " " +
                    (numero_env_1 + " ") +
                    (letra_env_1 + " ") +
                    (bis_env_1 + " ") +
                    (letra_env_2 + " ") +
                    (pcardinal_env_1 + " ") +
                    (numero_env_2 + " ") +
                    (letra_env_3 + " ") +
                    (pcardinal_env_2 + " ") +
                    complemento_env;
        }

        if (formato_direccion.equals(EnumFormatDireccion.FORMATO_2.getKey())) {
            direccion_concatenada =
                    (tipo_via_env_1 + " ") +
                            (numero_env_1 + " ") +
                            (bis_env_1 + " ") +
                            (letra_env_1 + " ") +
                            (numero_env_2 + " ") +
                            (pcardinal_env_1 + " ") +
                            (numero_env_3 + " ") +
                            (bis_env_2 + " ") +
                            (letra_env_2 + " ") +
                            (numero_env_4 + " ") +
                            (numero_env_5 + " ") +
                            (pcardinal_env_2 + " ") +
                            (complemento_env);
        }

        return direccion_concatenada_env;
    }

    @Bindable
    public String getDepartamento_env() {
        return departamento_env;
    }

    @Bindable
    public String getId_ciudad_env() {
        return id_ciudad_env;
    }

    @Bindable
    public String getName_ciudad_env() {
        return name_ciudad_env;
    }

    @Bindable
    public String getBarrio_env() {
        return barrio_env;
    }

    @Bindable
    public String getId_barrio_env() {
        return id_barrio_env;
    }

    @Bindable
    public List<Referencia> getReferencia() {
        return referencia;
    }

    @Bindable
    public String getReferenciado_hint() {
        return referenciado_hint;
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
    public boolean isModeEdit() {
        return modeEdit;
    }

    public boolean containHttp(String path){
        return path.toUpperCase().contains("HTTP");
    }

    @Bindable
    public String getCedula_frontal() {
        return cedula_frontal;
    }

    @Bindable
    public String getCedula_adverso() {
        return cedula_adverso;
    }

    @Bindable
    public String getPagare_frontal() {
        return pagare_frontal;
    }

    @Bindable
    public String getPagare_adverso() {
        return pagare_adverso;
    }

    @Bindable
    public String getId_departamento() {
        return id_departamento;
    }

    @Bindable
    public String getId_departamento_env() {
        return id_departamento_env;
    }

    public int getCode() {
        return code;
    }


    public List<RaiseDTO> getRaise() {
        return raise;
    }

    @Bindable
    public String getImg_terminos() {
        return img_terminos;
    }

    @Bindable
    public String getImg_ruc() {
        return img_ruc;
    }

    @Bindable
    public String getImg_back2() {
        return img_back2;
    }

    @Bindable
    public String getImg_central() {
        return img_central;
    }

    @Bindable
    public String getNomb_terc() {
        return nomb_terc;
    }
    @Bindable
    public String getApel_terc() {
        return apel_terc;
    }


    //SETTER

    public void setApel_terc(String apel_terc) {
        this.apel_terc = apel_terc;
        notifyPropertyChanged(BR.apel_terc);
    }

    public void setNomb_terc(String nomb_terc) {
        this.nomb_terc = nomb_terc;
        notifyPropertyChanged(BR.nomb_terc);
    }

    public void setImg_terminos(String img_terminos) {
        this.img_terminos = img_terminos;
        notifyPropertyChanged(BR.img_terminos);
    }

    public void setImg_ruc(String img_ruc) {
        this.img_ruc = img_ruc;
        notifyPropertyChanged(BR.img_ruc);
    }

    public void setImg_back2(String img_back2) {
        this.img_back2 = img_back2;
        notifyPropertyChanged(BR.img_back2);
    }

    public void setImg_central(String img_central) {
        this.img_central = img_central;
        notifyPropertyChanged(BR.img_central);
    }

    public void setReferenciado_nombre(String referenciado_nombre) {
        this.referenciado_nombre = referenciado_nombre;
        notifyPropertyChanged(BR.referenciado_nombre);
    }

    public void setSpnDirEnvio(String spnDirEnvio) {
        this.spnDirEnvio = spnDirEnvio;
        notifyPropertyChanged(BR.spnDirEnvio);
    }

    public void setDireccion_concatenada(String direccion_concatenada) {
        this.direccion_concatenada = direccion_concatenada;
        notifyPropertyChanged(BR.direccion_concatenada);
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
        notifyPropertyChanged(BR.cedula);
    }

    public void setImg_cedula(List<String> img_cedula) {
        this.img_cedula = img_cedula;
        notifyPropertyChanged(BR.img_cedula);
    }

    public void setPagare(List<String> pagare) {
        this.pagare = pagare;
        notifyPropertyChanged(BR.pagare);
    }

    public void setReferenciado_por(String referenciado_por) {
        this.referenciado_por = referenciado_por;
        notifyPropertyChanged(BR.referenciado_por);
    }

    public void setReferencia(List<Referencia> referencia) {
        this.referencia = referencia;
        notifyPropertyChanged(BR.referencia);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(BR.nombre);
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
        notifyPropertyChanged(BR.nacimiento);
    }

    public void setZona_seccion(String zona_seccion) {
        this.zona_seccion = zona_seccion;
        notifyPropertyChanged(BR.zona_seccion);
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


    public void setNumero5(String numero5) {
        this.numero5 = numero5;
        notifyPropertyChanged(BR.numero5);
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

    public void setDirereferencia(String direreferencia) {
        this.direreferencia = direreferencia;
        notifyPropertyChanged(BR.direreferencia);
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

    public void setTipo_via_env_1(String tipo_via_env_1) {
        this.tipo_via_env_1 = tipo_via_env_1;
        notifyPropertyChanged(BR.tipo_via_env_1);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setTipo_via_env_2(String tipo_via_env_2) {
        this.tipo_via_env_2 = tipo_via_env_2;
        notifyPropertyChanged(BR.tipo_via_env_2);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setNumero_env_1(String numero_env_1) {
        this.numero_env_1 = numero_env_1;
        notifyPropertyChanged(BR.numero_env_1);
        notifyPropertyChanged(BR.enableLetra_env_1);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setLetra_env_1(String letra_env_1) {
        this.letra_env_1 = letra_env_1;
        notifyPropertyChanged(BR.letra_env_1);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setBis_env_1(String bis_env_1) {
        this.bis_env_1 = bis_env_1;
        notifyPropertyChanged(BR.bis_env_1);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setBis_env_2(String bis_env_2) {
        this.bis_env_2 = bis_env_2;
        notifyPropertyChanged(BR.bis_env_2);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setPcardinal_env_2(String pcardinal_env_2) {
        this.pcardinal_env_2 = pcardinal_env_2;
        notifyPropertyChanged(BR.pcardinal_env_2);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setNumero_env_2(String numero_env_2) {
        this.numero_env_2 = numero_env_2;
        notifyPropertyChanged(BR.numero_env_2);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setLetra_env_2(String letra_env_2) {
        this.letra_env_2 = letra_env_2;
        notifyPropertyChanged(BR.letra_env_2);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setLetra_env_3(String letra_env_3) {
        this.letra_env_3 = letra_env_3;
        notifyPropertyChanged(BR.letra_env_3);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setNumero_env_3(String numero_env_3) {
        this.numero_env_3 = numero_env_3;
        notifyPropertyChanged(BR.numero_env_3);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setNumero_env_4(String numero_env_4) {
        this.numero_env_4 = numero_env_4;
        notifyPropertyChanged(BR.numero_env_4);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setNumero_env_5(String numero_env_5) {
        this.numero_env_5 = numero_env_5;
        notifyPropertyChanged(BR.numero_env_5);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setPcardinal_env_1(String pcardinal_env_1) {
        this.pcardinal_env_1 = pcardinal_env_1;
        notifyPropertyChanged(BR.pcardinal_env_1);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setComplemento_env(String complemento_env) {
        this.complemento_env = complemento_env;
        notifyPropertyChanged(BR.complemento_env);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setDirereferencia_env(String direreferencia_env_env) {
        this.direreferencia_env = direreferencia_env;
        notifyPropertyChanged(BR.direreferencia_env);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setDireccion_concatenada_env(String direccion_concatenada_env) {
        this.direccion_concatenada_env = direccion_concatenada_env;
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setDepartamento_env(String departamento_env) {
        this.departamento_env = departamento_env;
        notifyPropertyChanged(BR.departamento_env);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setId_ciudad_env(String id_ciudad_env) {
        this.id_ciudad_env = id_ciudad_env;
        notifyPropertyChanged(BR.id_ciudad_env);
    }

    public void setName_ciudad_env(String name_ciudad_env) {
        this.name_ciudad_env = name_ciudad_env;
        notifyPropertyChanged(BR.name_ciudad_env);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setBarrio_env(String barrio_env) {
        this.barrio_env = barrio_env;
        notifyPropertyChanged(BR.barrio_env);
        notifyPropertyChanged(BR.direccion_concatenada_env);
    }

    public void setId_barrio_env(String id_barrio_env) {
        this.id_barrio_env = id_barrio_env;
        notifyPropertyChanged(BR.id_barrio_env);
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        notifyPropertyChanged(BR.telefono);
    }

    public void setCelular(String celular) {
        this.celular = celular;
        notifyPropertyChanged(BR.celular);
    }

    public void setCorreo(String correo) {
        this.correo = correo;
        notifyPropertyChanged(BR.correo);
    }

    public void setImei(String imei) {
        this.imei = imei;
        notifyPropertyChanged(BR.imei);
    }

    public void setReferenciado_hint(String referenciado_hint) {
        this.referenciado_hint = referenciado_hint;
        notifyPropertyChanged(BR.referenciado_hint);
    }

    public void setCedula_frontal(String cedula_frontal) {
        this.cedula_frontal = cedula_frontal;
        notifyPropertyChanged(BR.cedula_frontal);
    }

    public void setCedula_adverso(String cedula_adverso) {
        this.cedula_adverso = cedula_adverso;
        notifyPropertyChanged(BR.cedula_adverso);
    }

    public void setPagare_frontal(String pagare_frontal) {
        this.pagare_frontal = pagare_frontal;
        notifyPropertyChanged(BR.pagare_frontal);
    }

    public void setPagare_adverso(String pagare_adverso) {
        this.pagare_adverso = pagare_adverso;
        notifyPropertyChanged(BR.pagare_adverso);
    }

    public void setId_departamento(String id_departamento) {
        this.id_departamento = id_departamento;
        notifyPropertyChanged(BR.id_departamento);
    }

    public void setId_departamento_env(String id_departamento_env) {
        this.id_departamento_env = id_departamento_env;
        notifyPropertyChanged(BR.id_departamento_env);
    }

    public void setShowDirEnvio(boolean showDirEnvio) {
        this.showDirEnvio = showDirEnvio;
        notifyPropertyChanged(BR.showDirEnvio);
    }

    public void setRefValidated(boolean refValidated) {
        this.refValidated = refValidated;
        notifyPropertyChanged(BR.refValidated);
    }

    public void setModeEdit(boolean modeEdit) {
        this.modeEdit = modeEdit;
        notifyPropertyChanged(BR.modeEdit);
    }

    @Bindable
    public String getFormato_direccion() {
        return formato_direccion;
    }

    public void setFormato_direccion(String formato_direccion) {
        this.formato_direccion = formato_direccion;
        notifyPropertyChanged(BR.formato_direccion);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setRaise(List<RaiseDTO> raise) {
        this.raise = raise;
    }

}
