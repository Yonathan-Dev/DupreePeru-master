package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class HistorialDTO implements Serializable {

	@SerializedName("status")
	private String status;

	@SerializedName("valid")
	private boolean valid;

	@SerializedName("result")
	private List<ItemFacturaDTO> result;

	@SerializedName("code")
	private int code;

	private List<RaiseDTO> raise;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setValid(boolean valid){
		this.valid = valid;
	}

	public boolean isValid(){
		return valid;
	}

	public void setResult(List<ItemFacturaDTO> result){
		this.result = result;
	}

	public List<ItemFacturaDTO> getResult(){
		return result;
	}

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public List<RaiseDTO> getRaise() {
		return raise;
	}
}