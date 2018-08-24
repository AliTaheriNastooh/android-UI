package com.sourcey.materiallogindemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

@SuppressLint("Registered")
public class Calling extends Activity {

    private MyPhoneCallListener mListener;
    TelephonyManager mTelephonyManager;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    Context mContext;
    CallingWithDevice callingWithDevice;
    public Calling(Context mContext,CallingWithDevice _callingWithDevice) {
            this.mContext = mContext;
            callingWithDevice=_callingWithDevice;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void setting(){
        ////////////////////

        mTelephonyManager = (TelephonyManager)
                mContext.getSystemService(TELEPHONY_SERVICE);
        if (isTelephonyEnabled()) {
            Log.d(TAG, mContext.getString(R.string.telephony_enabled));
            // Todo: Register the PhoneStateListener.
            checkForPhonePermission();
            mListener = new Calling.MyPhoneCallListener();
            mTelephonyManager.listen(mListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
            // Todo: Check for permission here.

        } else {
            Toast.makeText(this,
                    R.string.telephony_not_enabled,
                    Toast.LENGTH_LONG).show();
            Log.d(TAG, mContext.getString(R.string.telephony_not_enabled));
            // Disable the call button.
            disableCallButton();
        }


        ////////////////////////
    }
    ///////////////////////////////////
    private boolean isTelephonyEnabled() {
        if (mTelephonyManager != null) {
            if (mTelephonyManager.getSimState() ==
                    TelephonyManager.SIM_STATE_READY) {
                return true;
            }
        }
        return false;
    }
    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            Log.d(TAG, mContext.getString(R.string.permission_not_granted));
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission already granted.
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.CALL_PHONE)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                } else {
                    // Permission denied. Stop the app.
                    Log.d(TAG, mContext.getString(R.string.failure_permission));
                    Toast.makeText(this,
                            mContext.getString(R.string.failure_permission),
                            Toast.LENGTH_SHORT).show();
                    // Disable the call button
                    disableCallButton();
                }
            }
        }
    }
    private class MyPhoneCallListener extends PhoneStateListener {
        private boolean returningFromOffHook = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            String message = mContext.getString(R.string.phone_status);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    message = message + mContext.getString(R.string.ringing) + incomingNumber +" THis Partitaion.\n";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, message);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    message = message + mContext.getString(R.string.offhook)+"I Am Here";
                    Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, message);
                    returningFromOffHook = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    message = message + mContext.getString(R.string.idle);
                    Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, message);
                    if (returningFromOffHook) {
                        Toast.makeText(mContext.getApplicationContext(), message+":1", Toast.LENGTH_SHORT).show();
                        returningFromOffHook = false;
//                        if(mstate==1) {
//                            ((ChooseActivity) mContext).getRecognizeDTMF();
//                        }else{
//                            if(mstate==2) {
//                                ((GetDetails) mContext).getRecognizeDTMF();
//                            }
//                        }
                        callingWithDevice.getRecognizeDTMF();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                            Log.i(TAG, mContext.getString(R.string.restarting_app));
                        }
                    }else{
                        Toast.makeText(mContext.getApplicationContext(), message+":2",
                                Toast.LENGTH_SHORT).show();
//                        if(mstate==1) {
//                            ((ChooseActivity) mContext).startRecord();
//                        }else{
//                            if(mstate==2) {
//                                ((GetDetails) mContext).startRecord();
//                            }
//                        }
                       callingWithDevice.startRecord();
                    }
                    break;
                default:
                    message = message + "Phone off";
                    Toast.makeText(getApplicationContext(), message,
                            Toast.LENGTH_SHORT).show();
                    Log.i(TAG, message);
                    break;
            }
        }

    }
    private void disableCallButton(){
    }
    public void setCall(String callString){
        setting();
        Log.i("call String : ",callString);
        String phoneNumber = String.format("tel: %s", callString);
        Intent callIntent = new Intent(Intent.ACTION_CALL);

        callIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse(phoneNumber));
        if (callIntent.resolveActivity(mContext.getPackageManager()) != null) {
            checkForPhonePermission();
            mContext.startActivity(callIntent);
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_CALL Intent.");

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isTelephonyEnabled()) {
            mTelephonyManager.listen(mListener,
                    PhoneStateListener.LISTEN_NONE);
        }
    }

}
