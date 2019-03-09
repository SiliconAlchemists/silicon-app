package com.alchemists.silicon_app;

public class DriverData {
    private static DriverData sDriverData;

    public static DriverData get() {
        if (sDriverData == null) {
            sDriverData = new DriverData();
        }
        return sDriverData;
    }

    private DriverData(){

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
