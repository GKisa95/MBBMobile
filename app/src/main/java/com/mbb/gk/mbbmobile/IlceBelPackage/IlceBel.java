package com.mbb.gk.mbbmobile.IlceBelPackage;

public class IlceBel {
    private String name;
    private String baskan;
    private String phone;

    public IlceBel(String name, String baskan, String phone) {
        this.name = name;
        this.baskan = baskan;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaskan() {
        return baskan;
    }

    public void setBaskan(String baskan) {
        this.baskan = baskan;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
