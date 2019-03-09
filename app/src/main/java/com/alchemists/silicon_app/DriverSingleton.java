package com.alchemists.silicon_app;

public class DriverSingleton {
    private static DriverSingleton sDriverSingleton;

    public static DriverSingleton get() {
        if (sDriverSingleton == null) {
            sDriverSingleton = new DriverSingleton();
        }
        return sDriverSingleton;
    }

    private DriverSingleton(){

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
