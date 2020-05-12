package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.response.Preinscripcion;
import com.dupreeinca.lib_api_rest.model.dto.response.RaiseDTO;

import java.util.List;

/**
 * Created by cloudemotion on 18/8/17.
 */

public class ListPreinscripcionDTO {
    private String status;
    private boolean valid;
    private List<Preinscripcion> result;
    private int page_results;
    private int page_index;
    private int total_pages;
    private int total_results;
    private int code;
    private List<RaiseDTO> raise;

    public ListPreinscripcionDTO(String status, boolean valid, List<Preinscripcion> result, int page_results, int page_index, int total_pages, int total_results, int code) {
        this.status = status;
        this.valid = valid;
        this.result = result;
        this.page_results = page_results;
        this.page_index = page_index;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }

    public List<Preinscripcion> getResult() {
        return result;
    }

    public int getPage_results() {
        return page_results;
    }

    public int getPage_index() {
        return page_index;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getCode() {
        return code;
    }

    public List<RaiseDTO> getRaise() {
        return raise;
    }
}
