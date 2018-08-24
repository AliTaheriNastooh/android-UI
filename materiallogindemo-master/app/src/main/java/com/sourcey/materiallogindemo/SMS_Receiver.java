package com.sourcey.materiallogindemo;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SMS_Receiver extends BroadcastReceiver  {
    private static final String TAG = SMS_Receiver.class.getSimpleName();
    public static final String pdu_type = "pdus";
    private Context mContext;
    static ArrayList<OnNewSMSListener> arrOnNewSMSListener =
            new ArrayList<OnNewSMSListener>();
    public SMS_Receiver(){

    }

    public static void setOnNewLocationListener(
            OnNewSMSListener listener) {
        arrOnNewSMSListener.add(listener);
    }

    public static void clearOnNewLocationListener(
            OnNewSMSListener listener) {
        arrOnNewSMSListener.remove(listener);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the SMS message.
        mContext=context;
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        String phoneNumber="";
        String mMessage="";
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // Build the message to show.
                phoneNumber=msgs[i].getOriginatingAddress();
                mMessage=msgs[i].getMessageBody();
                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                // Log and display the SMS message.
                Log.d(TAG, "onReceive: " + strMessage);
                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                OnNewLocationReceived(phoneNumber,mMessage);
            }
        }

    }
    private static void OnNewLocationReceived(String phoneNumber,String massage) {
        // Check if the Listener was set, otherwise we'll get an Exception when
        // we try to call it
        if (arrOnNewSMSListener != null) {
            // Only trigger the event, when we have any listener
            for (int i = arrOnNewSMSListener.size() - 1; i >= 0; i--) {
                arrOnNewSMSListener.get(i).onNewSMSReceived(phoneNumber,massage);
            }
        }
    }


}
