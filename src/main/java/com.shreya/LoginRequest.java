package com.shreya;

import lombok.Data;

/**
 * Created by shreya on 29/11/16.
 */
@Data
public class LoginRequest {

    private String contactNumber;
    private String password;

    public LoginRequest(){}

    public LoginRequest(String contactNumber,String password){
        this.contactNumber=contactNumber;
        this.password=password;
    }
}
