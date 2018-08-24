package com.sourcey.materiallogindemo;

public interface OnNewSMSListener {
    public abstract void onNewSMSReceived(String phoneNumber,String massage);
}
