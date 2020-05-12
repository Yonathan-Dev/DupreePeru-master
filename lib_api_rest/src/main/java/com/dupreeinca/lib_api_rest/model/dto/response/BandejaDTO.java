package com.dupreeinca.lib_api_rest.model.dto.response;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 1/17/18.
 */

public class BandejaDTO {
    private String status;
    private boolean valid;
    private List<ItemBandeja> result;
    private int code;

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public List<ItemBandeja> getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

}
