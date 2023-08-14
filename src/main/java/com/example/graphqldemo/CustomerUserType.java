package com.example.graphqldemo;

public enum CustomerUserType {
    SUPERVISOR("Agent Supervisor"), CASHIER("Pos Agent");

    private String userType;

    CustomerUserType(String userType) {
        this.userType = userType;
    }

    public String getValue(){
        return this.userType;
    }
}
