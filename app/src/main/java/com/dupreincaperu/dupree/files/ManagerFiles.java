package com.dupreincaperu.dupree.files;

/**
 * Created by marwuinh@gmail.com on 2/21/19.
 */

public enum ManagerFiles {
    BIS("bis"),
    DIR_SEND("dir_send"),
    LETRA("letra"),
    OPTION_CATALOG("option_catalog"),
    PARENTEZCOS("parentezcos"),
    PCARDINAL("pcardinal"),
    TIPO_VIA("tipo_via"),
    TIPO_DOC("tipo_doc");

    private String key;
    ManagerFiles(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
