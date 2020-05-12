package com.dupreeinca.lib_api_rest.model.dto;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    private SingleUserDTO data;

    public SingleUserDTO getData() {
        return data;
    }

    public void setData(SingleUserDTO data) {
        this.data = data;
    }

    public class SingleUserDTO {

        private int id;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("last_name")
        private String lastName;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }
}
