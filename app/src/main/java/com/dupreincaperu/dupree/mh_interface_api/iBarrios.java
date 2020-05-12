package com.dupreincaperu.dupree.mh_interface_api;

import com.dupreeinca.lib_api_rest.model.dto.response.ListBarrioDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cloudemotion on 26/8/17.
 */

public interface iBarrios {
    @GET("panel/barrios/")
    Call<ListBarrioDTO> obtainBarrios(@Query("id_ciudad") String id_ciudad);
}
