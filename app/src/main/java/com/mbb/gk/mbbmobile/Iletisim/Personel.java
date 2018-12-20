package com.mbb.gk.mbbmobile.Iletisim;

public class Personel {
    private String name;
    private String phone;
    private String email;
    private String department;
    private String job;
    private String deptPhone;
    private String deptMail;
    private String connectedTo;

    public Personel() {
    }

    public Personel(String name, String phone, String email, String department, String job, String deptPhone, String deptMail, String connectedTo) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.job = job;
        this.deptPhone = deptPhone;
        this.deptMail = deptMail;
        this.connectedTo = connectedTo;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getDeptMail() {
        return deptMail;
    }

    public void setDeptMail(String deptMail) {
        this.deptMail = deptMail;
    }

    public String getConnectedTo() {
        return connectedTo;
    }

    public void setConnectedTo(String connectedTo) {
        this.connectedTo = connectedTo;
    }

    @Override
    public String toString() {
        return "Personel{" + "name=" + name + ", phone=" + phone + ", email=" + email + ", department=" + department + ", job=" + job + ", deptPhone=" + deptPhone + ", deptMail=" + deptMail + ", connectedTo=" + connectedTo + '}';
    }



}
