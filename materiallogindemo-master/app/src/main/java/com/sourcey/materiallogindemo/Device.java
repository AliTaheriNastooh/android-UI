package com.sourcey.materiallogindemo;

import java.io.Serializable;

public class Device implements Serializable {
    private String name;
    private String model;
    private String address;
    private String phoneNumber;
    private Integer image;
    private boolean defaultImage;
    private String customImage;
    public Device(String _name,String _model,String _address,String _mobileNumber,Integer _imege,boolean _defaultImage,String _customImage){
        name=_name;
        model=_model;
        address=_address;
        phoneNumber=_mobileNumber;
        image=_imege;
        defaultImage=_defaultImage;
        customImage=_customImage;
    }
    public void setDetails(String _name,String _model,String _address,String _mobileNumber,Integer _imege,boolean _defaultImage,String _customImage){
        name=_name;
        model=_model;
        address=_address;
        phoneNumber=_mobileNumber;
        image=_imege;
        defaultImage=_defaultImage;
        customImage=_customImage;
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
    boolean getDefaultImage(){return defaultImage;}
    String getCustomImage(){return customImage; }
}
