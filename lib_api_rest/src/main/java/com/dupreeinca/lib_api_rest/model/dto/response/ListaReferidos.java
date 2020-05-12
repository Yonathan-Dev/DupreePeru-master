package com.dupreeinca.lib_api_rest.model.dto.response;

import com.dupreeinca.lib_api_rest.model.dto.request.Index;
import com.dupreeinca.lib_api_rest.model.dto.response.IncentivoRef;

import java.util.List;

/**
 * Created by cloudemotion on 2/9/17.
 */

public class ListaReferidos {
    private String title_1;
    private String title_2;
    private String title_3;
    private String title_4;
    private String title_5;
    private List<IncentivoRef> table;
    private Index paginator;
    private String asesora;

    public String getTitle_1() {
        return title_1;
    }

    public String getTitle_2() {
        return title_2;
    }

    public String getTitle_3() {
        return title_3;
    }

    public String getTitle_4() {
        return title_4;
    }

    public String getTitle_5() {
        return title_5;
    }

    public List<IncentivoRef> getTable() {
        return table;
    }

    public Index getPaginator() {
        return paginator;
    }

    public String getAsesora() {
        return asesora;
    }
}
