package com.example.mini;

public class PhoneNoStore {
    String user,city,Phone1,Phone2,Phone3,Phone4,mobile;

    public PhoneNoStore(){

    }

    public PhoneNoStore(String phone1,String phone2,String phone3,String phone4) {
        Phone1 = phone1;
        Phone2 = phone2;
        Phone3 = phone3;
        Phone4 = phone4;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getPhone3() {
        return Phone3;
    }

    public void setPhone3(String phone3) {
        Phone3 = phone3;
    }

    public String getPhone4() {
        return Phone4;
    }

    public void setPhone4(String phone4) {
        Phone4 = phone4;
    }
}
