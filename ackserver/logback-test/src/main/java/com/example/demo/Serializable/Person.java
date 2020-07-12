package com.example.demo.Serializable;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Person implements Serializable{
    /**
     * 序列化ID
     */
    private static final long serialVersionUID=-5809782578272943999L;

    private int age;
    private String name;
    private String sex;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
}
