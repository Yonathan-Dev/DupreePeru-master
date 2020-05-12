package com.dupreincaperu.dupree.mh_interface_api;

import com.dupreeinca.lib_api_rest.model.dto.response.BannerDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.VersionDTO;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by cloudemotion on 25/8/17.
 */

public interface iBanners {
    @GET("panel/banner/")
    Call<BannerDTO> obtainBanner();

    @GET("panel/version/")
    Call<VersionDTO> obtainVersion();
}
