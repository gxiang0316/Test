package com.gordon.jasper.BeanUtil;

/**
 * Created by gordon on 2018/8/19.
 */
public class User {

    private String name;
    private String sex;
    private int age;
    private String address;
    private String phone;
    private String date;

    public User(){}

    public User(String name, String sex, int age, String address, String phone,String date) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
