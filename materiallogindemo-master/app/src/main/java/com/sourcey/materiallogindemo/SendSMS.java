package com.sourcey.materiallogindemo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity {
    Context mcontext;
    public SendSMS(Context context){
        mcontext=context;
        checkForSmsPermission();
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;




    public void smsSendMessage(String phone, String messages) {


        String destinationAddress = phone;
        // Find the sms_message view.

        String smsMessage = messages;
        // Set the service center address if needed, otherwise null.
        String scAddress = null;
        // Set pending intents to broadcast
        // when message sent and when delivered, or set to null.
        PendingIntent sentIntent = null, deliveryIntent = null;
        checkForSmsPermission();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage
                (destinationAddress, scAddress, smsMessage,
                        sentIntent, deliveryIntent);

    }



    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(mcontext,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, mcontext.getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions((Activity) mcontext,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            //enableSmsButton();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // Check if permission is granted or not for the request.
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Enable sms button.
                   // enableSmsButton();
                } else {
                    // Permission denied.
                    Log.d(TAG, getString(R.string.failure_permission));
                    Toast.makeText(this,
                            getString(R.string.failure_permission),
                            Toast.LENGTH_LONG).show();
                    // Disable the sms button.
                    //disableSmsButton();
                    Toast.makeText(this, "SMS usage disabled", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * Uses an implicit intent to make the phone call.
     * Before calling, checks to see if permission is granted.
     *
     * @param view View (phone_icon) that was clicked.
     */

    /**
     * Monitors and logs phone call activities, and shows the phone state
     * in a toast message.
     */


    /**
     * Makes the call button (phone_icon) invisible so that it can't be used,
     * and makes the Retry button visible.
     */
//    private void disableSmsButton() {
//        Toast.makeText(this, "SMS usage disabled", Toast.LENGTH_LONG).show();
//        ImageButton smsButton = (ImageButton) findViewById(R.id.message_icon);
//        smsButton.setVisibility(View.INVISIBLE);
//        Button retryButton = (Button) findViewById(R.id.button_retry);
//        retryButton.setVisibility(View.VISIBLE);
//    }


//    private void enableSmsButton() {
//        ImageButton smsButton = (ImageButton) findViewById(R.id.message_icon);
//        smsButton.setVisibility(View.VISIBLE);
//    }


//    public void retryApp(View view) {
//        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
//        startActivity(intent);
//    }
}
