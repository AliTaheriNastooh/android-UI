package com.sourcey.materiallogindemo;

import java.io.Serializable;

public class Operation implements Serializable {
    private String title;
    private String info;
    private String status;
    private String time;
    private String date;
    private String textOfImage;
    private Integer image;
    private String textForCallDevice;
    public Operation(String _title,String _info,String _status,String _time,String _date,String _textOfCallDevice){
        title=_title;
        info=_info;
        status=_status;
        time=_time;
        date=_date;
        textForCallDevice=_textOfCallDevice;
        setImage();
    }
    public Operation(){}
    public void setDetails(String _title,String _info,String _status,String _time,String _date,String _textOfCallDevice) {
        title = _title;
        info = _info;
        status = _status;
        time = _time;
        date = _date;
        textForCallDevice=_textOfCallDevice;
        setImage();
    }
    public void setTitle(String _title){
        title=_title;
    }
    private void setImage(){
        if(status.equals("pending")){
            textOfImage="انتظار";
            image=R.drawable.pending;
        }
        if(status.equals("success")){
            textOfImage= "موفق";
            image=R.drawable.success;
        }
        if(status.equals("unsuccess")){
            textOfImage="ناموفق";
            image=R.drawable.unsuccess;
        }

    }
    public String getTitle(){return title;}

    public String getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    public Integer getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getTextOfImage() {
        return textOfImage;
    }

    public String getTime() {
        return time;
    }

    public String getTextForCallDevice() {
        return textForCallDevice;
    }
}