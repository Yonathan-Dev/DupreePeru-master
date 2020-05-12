package com.dupreincaperu.dupree.mh_CRUD;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Posibles_Nuevas;

import java.util.List;

public class ListPosiblesNuevasDTO {
    private String status;
    private boolean valid;
    private List<Posibles_Nuevas> result;
    private int page_results;
    private int page_index;
    private int total_pages;
    private int total_results;
    private int code;

    public ListPosiblesNuevasDTO(String status, boolean valid, List<Posibles_Nuevas> result, int page_results, int page_index, int total_pages, int total_results, int code) {
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

    public List<Posibles_Nuevas> getResult() {
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

    public void setResult(List<Posibles_Nuevas> result) {
        this.result = result;
    }
}

