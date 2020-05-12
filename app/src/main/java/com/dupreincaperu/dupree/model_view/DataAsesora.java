package com.dupreincaperu.dupree.model_view;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by marwuinh@gmail.com on 2/26/19.
 */

public class DataAsesora implements Parcelable{
    private int id = -1;
    private String nombre="", cedula="";
    private boolean modeEdit;
    private String formato_direccion;

    public DataAsesora() {
    }

    public DataAsesora(String formato_direccion, String nombre, String cedula, boolean modeEdit) {
        this.formato_direccion = formato_direccion;
        this.nombre = nombre;
        this.cedula = cedula;
        this.modeEdit = modeEdit;
    }

    public DataAsesora(int id, String nombre, String cedula) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
    }


    protected DataAsesora(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        cedula = in.readString();
        modeEdit = in.readByte() != 0;
        formato_direccion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(cedula);
        dest.writeByte((byte) (modeEdit ? 1 : 0));
        dest.writeString(formato_direccion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataAsesora> CREATOR = new Creator<DataAsesora>() {
        @Override
        public DataAsesora createFromParcel(Parcel in) {
            return new DataAsesora(in);
        }

        @Override
        public DataAsesora[] newArray(int size) {
            return new DataAsesora[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public boolean isModeEdit() {
        return modeEdit;
    }

    public void setModeEdit(boolean modeEdit) {
        this.modeEdit = modeEdit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormato_direccion() {
        return formato_direccion;
    }

    public void setFormato_direccion(String formato_direccion) {
        this.formato_direccion = formato_direccion;
    }
}
