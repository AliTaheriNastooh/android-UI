package com.sourcey.materiallogindemo;

import java.io.Serializable;

public class Device implements Serializable {
    private String name;
    private String model;
    private String address;
    private String phoneNumber;
    private Integer image;
    public Device(String _name,String _model,String _address,String _mobileNumber,Integer _imege){
        name=_name;
        model=_model;
        address=_address;
        phoneNumber=_mobileNumber;
        image=_imege;
    }
    public void setDetails(String _name,String _model,String _address,String _mobileNumber,Integer _imege){
        name=_name;
        model=_model;
        address=_address;
        phoneNumber=_mobileNumber;
        image=_imege;
    }
    String getName(){
        return name;
    }
    String getModel(){
        return model;
    }
    String getAddress(){
        return address;
    }
    String getPhoneNumber(){
        return phoneNumber;
    }
    Integer getImage(){
        return image;
    }
}
