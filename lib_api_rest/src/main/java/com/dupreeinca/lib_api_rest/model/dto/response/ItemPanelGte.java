package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 13/8/17.
 */

public class ItemPanelGte {

    private String campana;
    private String valo_camp1;
    private String valo_meta1;
    private String valo_desc1;
    private String valo_camp2;
    private String valo_meta2;
    private String valo_desc2;
    private String valo_camp3;
    private String valo_meta3;
    private String valo_desc3;

    public ItemPanelGte(String campana, String valo_camp1, String valo_meta1, String valo_desc1, String valo_camp2,
                        String valo_meta2, String valo_desc2, String valo_camp3, String valo_meta3, String valo_desc3) {
        this.campana = campana;
        this.valo_camp1 = valo_camp1;
        this.valo_meta1 = valo_meta1;
        this.valo_desc1 = valo_desc1;
        this.valo_camp2 = valo_camp2;
        this.valo_meta2 = valo_meta2;
        this.valo_desc2 = valo_desc2;
        this.valo_camp3 = valo_camp3;
        this.valo_meta3 = valo_meta3;
        this.valo_desc3 = valo_desc3;
    }


    public String getCampana() {
        return campana;
    }

    public void setCampana(String campana) {
        this.campana = campana;
    }

    public String getValo_camp1() {
        return valo_camp1;
    }

    public String getValo_meta1() {
        return valo_meta1;
    }

    public String getValo_desc1() {
        return valo_desc1;
    }

    public String getValo_camp2() {
        return valo_camp2;
    }

    public String getValo_meta2() {
        return valo_meta2;
    }

    public String getValo_desc2() {
        return valo_desc2;
    }

    public String getValo_camp3() {
        return valo_camp3;
    }

    public String getValo_meta3() {
        return valo_meta3;
    }

    public String getValo_desc3() {
        return valo_desc3;
    }

}
