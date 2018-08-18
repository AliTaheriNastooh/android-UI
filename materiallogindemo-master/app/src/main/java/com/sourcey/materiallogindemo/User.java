package com.sourcey.materiallogindemo;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String rememberMe;
    public User(){

    }
    public User(String _usernam,String _password,String _email,String _mobile,String _rememberMe){
        username=_usernam;
        password=_password;
        email=_email;
        mobile=_mobile;
        rememberMe=_rememberMe;
    }
    public void setContent(String _usernam,String _password,String _email,String _mobile,String _rememberMe){
        username=_usernam;
        password=_password;
        email=_email;
        mobile=_mobile;
        rememberMe=_rememberMe;
    }
    public void setRememberMe(String s){
        rememberMe=s;
    }
    public String getUsername(){
        return username;
    }
    public String getPassworde(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getMobile(){
        return mobile;
    }
    public String getRememberMe(){
        return rememberMe;
    }
}
