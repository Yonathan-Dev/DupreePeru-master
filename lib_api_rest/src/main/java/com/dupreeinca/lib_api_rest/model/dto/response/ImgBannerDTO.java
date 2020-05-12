package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 25/8/17.
 */

public class ImgBannerDTO {
    private Resolution resoluction1;
    private Resolution resoluction2;

    public Resolution getResoluction1() {
        return resoluction1;
    }

    public Resolution getResoluction2() {
        return resoluction2;
    }

    public class Resolution {
        private String size;
        private String img1;
        private String img2;
        private String img3;

        public String getSize() {
            return size;
        }

        public String getImg1() {
            return img1;
        }

        public String getImg2() {
            return img2;
        }

        public String getImg3() {
            return img3;
        }
    }

}
