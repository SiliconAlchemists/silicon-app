package com.alchemists.silicon_app;

import android.content.Context;

public class UserData {
    private static UserData sUserData;

    public static UserData get() {
        if (sUserData == null) {
            sUserData = new UserData();
        }
        return sUserData;
    }

        private UserData(){

        }
        private String name;
        private String email;
        private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
