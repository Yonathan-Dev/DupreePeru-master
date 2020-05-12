package com.dupreeinca.lib_api_rest.model.dto.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemFacturaDTO implements Parcelable {

	@SerializedName("id_factura")
	private String idFactura;

	@SerializedName("productos")
	private List<ItemDetailFacturaDTO> productos;

	@SerializedName("precio_total")
	private String precioTotal;

	@SerializedName("campana")
	private String campana;

	protected ItemFacturaDTO(Parcel in) {
		idFactura = in.readString();
		productos = in.createTypedArrayList(ItemDetailFacturaDTO.CREATOR);
		precioTotal = in.readString();
		campana = in.readString();
	}

	public static final Creator<ItemFacturaDTO> CREATOR = new Creator<ItemFacturaDTO>() {
		@Override
		public ItemFacturaDTO createFromParcel(Parcel in) {
			return new ItemFacturaDTO(in);
		}

		@Override
		public ItemFacturaDTO[] newArray(int size) {
			return new ItemFacturaDTO[size];
		}
	};

	public void setIdFactura(String idFactura){
		this.idFactura = idFactura;
	}

	public String getIdFactura(){
		return idFactura;
	}

	public void setProductos(List<ItemDetailFacturaDTO> productos){
		this.productos = productos;
	}

	public List<ItemDetailFacturaDTO> getProductos(){
		return productos;
	}

	public void setPrecioTotal(String precioTotal){
		this.precioTotal = precioTotal;
	}

	public String getPrecioTotal(){
		return precioTotal;
	}

	public void setCampana(String campana){
		this.campana = campana;
	}

	public String getCampana(){
		return campana;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(idFactura);
		parcel.writeTypedList(productos);
		parcel.writeString(precioTotal);
		parcel.writeString(campana);
	}
}