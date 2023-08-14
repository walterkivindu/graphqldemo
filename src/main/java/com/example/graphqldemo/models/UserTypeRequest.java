package com.example.graphqldemo.models;

import com.example.graphqldemo.CustomerUserType;

public class UserTypeRequest {
    private CustomerUserType userType;

    public CustomerUserType getUserType() {
        return userType;
    }

    public void setUserType(CustomerUserType userType) {
        this.userType = userType;
    }
}
