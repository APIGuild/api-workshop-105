package com.apiguild.model;

/**
 * Created by bingwang on 10/30/17.
 */
public class UserInfo {
    public String userName;
    public int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "userName: " + this.userName + " ,age is: " + this.age;
    }
}