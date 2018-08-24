package com.sourcey.materiallogindemo;

import android.content.Context;
import android.widget.Toast;

public class StartDTMF_Decode {
    Controller controller;
    private String recognizeredText;
    private Context mContex;

    public StartDTMF_Decode(Context lastContext)
    {
        mContex=lastContext;
        controller = new Controller(this);
        recognizeredText = "";
    }
    private static final int REQUEST_CODE = 1;

    public void startDecode()
    {
        recognizeredText = "";
        controller.changeState();
    }

    public void stopDecode()
    {
        controller.changeState();
    }
    public String getRecognizeredText(){
        return recognizeredText;
    }

    public void showToast(String s){
        Toast.makeText(mContex,s, Toast.LENGTH_LONG).show();
    }
    public void clearText()
    {
        recognizeredText = "";
    }

    public void addText(Character c)
    {
        //Toast.makeText(this,"character: "+c, Toast.LENGTH_LONG).show();
        recognizeredText += c;
    }




}
