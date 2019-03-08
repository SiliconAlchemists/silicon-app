package com.alchemists.silicon_app;

import android.content.Context;

public class UserData {
    private static UserData sUserData;

    private static UserData get() {
        if (sUserData == null) {
            sUserData = new UserData();
        }
        return sUserData;
    }

        private UserData(){

        }
        private String userName;
        private String email;


}
