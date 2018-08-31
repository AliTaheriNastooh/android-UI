package com.sourcey.materiallogindemo;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class CallingWithDevice {
    private SendSMS mySender_sms;
    private Context mContext;
    private String callerClass;
    private String phoneNumber;
    private Calling getCall;
    private String lastMessageSend;
    private StartDTMF_Decode dtmf_decode;
    boolean isRegistered = false;
    boolean which_Call_SMS=true;
    public static final String SMS_RECEIVED_ACTION =
            "android.provider.Telephony.SMS_RECEIVED";
    SMS_Receiver mSMS_Receiver;
    public CallingWithDevice(Context _context,String _callerDevice,String _phoneNumber){
        mContext=_context;
        callerClass=_callerDevice;
        mySender_sms=new SendSMS(mContext);
        phoneNumber=_phoneNumber;
        getCall=new Calling(mContext,this);
        dtmf_decode=new StartDTMF_Decode(mContext);
        mSMS_Receiver=new SMS_Receiver();
        IntentFilter filter = new IntentFilter(SMS_RECEIVED_ACTION);
        mContext.registerReceiver(mSMS_Receiver, filter);
        isRegistered=true;
        final OnNewSMSListener onNewLocationListener = new OnNewSMSListener() {
            @Override
            public void onNewSMSReceived(String phoneNumberRecieved,String messageRecieved) {
                // do something
                setRecievedSMS(phoneNumberRecieved,messageRecieved);
                // then stop listening
                //Toast.makeText(mContext,"now now"+in[0]+" : "+in[1],Toast.LENGTH_SHORT).show();
                SMS_Receiver.clearOnNewLocationListener(this);
            }
        };

        // start listening for new location
        SMS_Receiver.setOnNewLocationListener(
                onNewLocationListener);
    }
    public void setRegisteredSMS(){
        if (!isRegistered){
            isRegistered=true;
            IntentFilter filter = new IntentFilter(SMS_RECEIVED_ACTION);
            mContext.registerReceiver(mSMS_Receiver, filter);
        }
    }
    public void unRegesteredSMS(){
        if (isRegistered){
            mContext.unregisterReceiver(mSMS_Receiver);
            isRegistered=false;
        }
    }
    public void setRecievedSMS(String phoneNumberOfSMS,String messageOfSMS){
        if (phoneNumberOfSMS.length()<11)return;
        String parseGetPhoneNumber=phoneNumberOfSMS.substring(5);
        String parsePhoneNumber=phoneNumber.substring(3);

        if(parsePhoneNumber.equals(parseGetPhoneNumber)){
            checkResultOfCalling(messageOfSMS);
        }

    }
    public void connectWithDevice(boolean call_SMS,String sendMessage){
        which_Call_SMS=call_SMS;
        String convertedMessage=convertMessage(call_SMS,sendMessage);
        if(call_SMS){
            mySender_sms.smsSendMessage(phoneNumber,convertedMessage);
        }else{
            getCall.setCall(phoneNumber+convertedMessage);
        }
    }

    public String convertMessage(boolean call_sms,String lastMessage) {
        String newMessage="";
        if(call_sms){
            for (int i=0;i<lastMessage.length();i++){
                if(lastMessage.charAt(i)=='#'){
                    newMessage+="*";
                }else{
                    newMessage+=lastMessage.charAt(i);
                }
            }
            return newMessage;
        }else{
            newMessage=",";
            boolean flag=false;
            for (int i=0;i<lastMessage.length();i++){
                if (lastMessage.charAt(i) == '#') {
                    newMessage=newMessage+",";
                    flag=false;
                }else{
                    if (lastMessage.charAt(i) != '\n') {
                        if (flag) {
                            newMessage = newMessage + "," + lastMessage.charAt(i);
                            flag = true;
                        } else {
                            newMessage = newMessage + lastMessage.charAt(i);
                            flag = true;

                        }
                    }
                    //setMessage=setMessage+massages.charAt(i);
                }
            }
            lastMessageSend=newMessage;
            return newMessage;
        }

    }

    public void checkResultOfCalling(String getMassage){
        if (which_Call_SMS){
            if(getMassage.equals("0")){
                ((Agriculture_system)mContext).getResultOfCalling("notcomplete");
            }
            if(getMassage.equals("1")){
                ((Agriculture_system)mContext).getResultOfCalling("complete");
            }
        }else{
            if (getMassage.substring(getMassage.length()-2).equals("##")){
                ((Agriculture_system)mContext).getResultOfCalling("complete");
            }else{
                ((Agriculture_system)mContext).getResultOfCalling("notcomplete");
            }

        }
//        if (callerClass.equals("agricultureSystem")){
//            ((Agriculture_system)mContext).getRecievedSMSMassage(messageOfSMS);
//        }else{
//            if (callerClass.equals("getDetails")){
//                ((GetDetails)mContext).getRecievedSMSMassage(messageOfSMS);
//            }
//        }
    }
    public void getRecognizeDTMF(){
        String recognizeText=dtmf_decode.getRecognizeredText();
        Toast.makeText(mContext,"text"+ dtmf_decode.getRecognizeredText(),Toast.LENGTH_SHORT).show();
        Log.i("recognize Text----:", recognizeText);
        dtmf_decode.stopDecode();
        checkResultOfCalling(recognizeText);
//        String correctText="";
//        for(int i=0;i<lastMessageSend.length();i++){
//            if (lastMessageSend.charAt(i) == ',') {
//
//            }else{
//                correctText+=lastMessageSend.charAt(i);
//            }
//        }
//        boolean flag=false;
//        if(recognizeText.length()<=correctText.length()){
//            return;
//        }
//        for(int i=0;i<correctText.length();i++){
//            if(correctText.charAt(i)==recognizeText.charAt((i))){
//                flag=true;
//            }else{
//                flag=false;
//            }
//        }
//        if(flag){
//            String tmp=""+(recognizeText.charAt(correctText.length()));
//            //goToNextPage(tmp);
//        }
    }
    public  void startRecord(){
        dtmf_decode.startDecode();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Toast.makeText(mContext,"ali",Toast.LENGTH_SHORT).show();
    }
    //////////////////////////




    //////////////////////////
}
