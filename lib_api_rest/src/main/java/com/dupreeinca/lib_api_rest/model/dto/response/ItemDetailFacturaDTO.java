package com.dupreeinca.lib_api_rest.model.dto.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemDetailFacturaDTO implements Parcelable {

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String name;

	@SerializedName("cantidad_ped")
	private String cantidadPed;

	@SerializedName("cantidad_desp")
	private String cantidadDesp;

	@SerializedName("valor")
	private String valor;

	protected ItemDetailFacturaDTO(Parcel in) {
		id = in.readString();
		name = in.readString();
		cantidadPed = in.readString();
		cantidadDesp = in.readString();
		valor = in.readString();
	}

	public static final Creator<ItemDetailFacturaDTO> CREATOR = new Creator<ItemDetailFacturaDTO>() {
		@Override
		public ItemDetailFacturaDTO createFromParcel(Parcel in) {
			return new ItemDetailFacturaDTO(in);
		}

		@Override
		public ItemDetailFacturaDTO[] newArray(int size) {
			return new ItemDetailFacturaDTO[size];
		}
	};

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setCantidadPed(String cantidadPed){
		this.cantidadPed = cantidadPed;
	}

	public String getCantidadPed(){
		return cantidadPed;
	}

	public void setCantidadDesp(String cantidadDesp){
		this.cantidadDesp = cantidadDesp;
	}

	public String getCantidadDesp(){
		return cantidadDesp;
	}

	public void setValor(String valor){
		this.valor = valor;
	}

	public String getValor(){
		return valor;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(id);
		parcel.writeString(name);
		parcel.writeString(cantidadPed);
		parcel.writeString(cantidadDesp);
		parcel.writeString(valor);
	}
}