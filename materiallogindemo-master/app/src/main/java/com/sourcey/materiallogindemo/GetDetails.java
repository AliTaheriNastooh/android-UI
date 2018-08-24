package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

public class GetDetails extends AppCompatActivity {
    String lastFragment="";
    String newFragment="";
    int a=0;
    CallingWithDevice callingWithDevice;
    Fragment fragment;
    private Device device;
    private Switch simpleSwitch;
    private StartDTMF_Decode dtmf_decode;
    private String sentMassage;
    String numberOfChannel;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details);
        TextView textOfToggleButton=(TextView)findViewById(R.id.textViewOfToogleButton2);
        textOfToggleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
      //  mySender_sms=new SendSMS(this);
       // getCall=new Calling(this,2);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch2);
        Intent intent = getIntent();
        set_NewFragment(intent.getStringExtra(Agriculture_system.EXTRA_MESSAGE1+"1"));
        device=(Device) intent.getSerializableExtra(Agriculture_system.EXTRA_MESSAGE1+"2");
        numberOfChannel=intent.getStringExtra(Agriculture_system.EXTRA_MESSAGE1+"3");
        TextView devicePhoneNumber=(TextView)findViewById(R.id.devicePhoneNumberInGetDetails);
        devicePhoneNumber.setText("شماره تماس دستگاه: " +device.getPhoneNumber());
        TextView deviceAddress=(TextView)findViewById(R.id.deviceAdressInGetDetails);
        deviceAddress.setText( "آدرس دستگاه: " +device.getAddress());
        changeFragment(newFragment);
        dtmf_decode=new StartDTMF_Decode(this);
        callingWithDevice=new CallingWithDevice(this,"getDetails",device.getPhoneNumber());
    }
    ////////////////////
    public void set_NewFragment(String set){
        if(!newFragment.isEmpty()){
            lastFragment=newFragment;
        }
        newFragment=set;
    }
    ////////////////////
    public void changeFragment(String desFragment){
        if (!lastFragment.isEmpty()) {
            Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(lastFragment);
            if (fragment1 != null)
                getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }
        Bundle bundle = new Bundle();
        String myMessage = "";

        fragment=new backgroundFragment() ;
        if(desFragment.equals("Channeloff")){
            fragment=new Channel_Off();
        }
        if(desFragment.equals("Channelon")){
            fragment=new channelOn();
        }
        if(desFragment.equals("ShowChannelStateOff")){
            fragment=new showChannelState();
            myMessage = "کانال خاموش است";
            LinearLayout l1=(LinearLayout) findViewById(R.id.LinearLayoutOfSetChange);
            l1.setVisibility(View.INVISIBLE);
            LinearLayout l2=(LinearLayout) findViewById(R.id.linearLayoutOfHowConnectWithDevice);
            l2.setVisibility(View.INVISIBLE);
        }
        if(desFragment.equals("ShowChannelStateOn")){
            fragment=new showChannelState();
            myMessage = "کانال روشن است";
            LinearLayout l1=(LinearLayout) findViewById(R.id.LinearLayoutOfSetChange);
            l1.setVisibility(View.INVISIBLE);
            LinearLayout l2=(LinearLayout) findViewById(R.id.linearLayoutOfHowConnectWithDevice);
            l2.setVisibility(View.INVISIBLE);
        }
        if(desFragment.equals("showOperationCorrect")){
            fragment=new showChannelState();
            myMessage = "عملیات با موقیت انجام شد";
            LinearLayout l1=(LinearLayout) findViewById(R.id.LinearLayoutOfSetChange);
            l1.setVisibility(View.INVISIBLE);
            LinearLayout l2=(LinearLayout) findViewById(R.id.linearLayoutOfHowConnectWithDevice);
            l2.setVisibility(View.INVISIBLE);
        }
        if(desFragment.equals("showOperationWrong")){
            fragment=new showChannelState();
            myMessage = "عملیات انجام نگرفت";
            LinearLayout l1=(LinearLayout) findViewById(R.id.LinearLayoutOfSetChange);
            l1.setVisibility(View.INVISIBLE);
            LinearLayout l2=(LinearLayout) findViewById(R.id.linearLayoutOfHowConnectWithDevice);
            l2.setVisibility(View.INVISIBLE);
        }

        if(desFragment.equals("configDevice")){
            fragment=new ConfigOfDevice();
            LinearLayout l1=(LinearLayout) findViewById(R.id.LinearLayoutOfSetChange);
            l1.setVisibility(View.VISIBLE);
            LinearLayout l2=(LinearLayout) findViewById(R.id.linearLayoutOfHowConnectWithDevice);
            l2.setVisibility(View.VISIBLE);
        }

        bundle.putString("message", myMessage );
        fragment.setArguments(bundle);
        FragmentManager fm =getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft=fm.beginTransaction();

        if(desFragment.equals("Channeloff")){
            ft.add(R.id.fragment,fragment,"Channeloff");
        }
        if(desFragment.equals("Channelon")){
            ft.add(R.id.fragment,fragment,"Channelon");
        }
        if(desFragment.equals("ShowChannelStateOff")){
            ft.add(R.id.fragment,fragment,"showChannelState");
        }
        if(desFragment.equals("ShowChannelStateOn")){
            ft.add(R.id.fragment,fragment,"showChannelState");
        }
        if(desFragment.equals("showOperationCorrect")){
            ft.add(R.id.fragment,fragment,"showChannelState");
        }
        if(desFragment.equals("showOperationWrong")){
            ft.add(R.id.fragment,fragment,"showChannelState");
        }
        if(desFragment.equals("configDevice")){
            ft.add(R.id.fragment,fragment,"configDevice");
        }
        try {
            ft.commit();
        }
        catch(Exception exc) {
            Toast.makeText(getApplicationContext(),"hey error commit",Toast.LENGTH_SHORT).show();
        }

    }
    public void setTime(View view){
        Date currentTime = Calendar.getInstance().getTime();
        EditText editTextOfMinute=(EditText) findViewById(R.id.editTextOfSetMinute);
        editTextOfMinute.setText(Integer.toString(currentTime.getMinutes()));
        editTextOfMinute.setEnabled(false);
        EditText editTextOfHour=(EditText) findViewById(R.id.editTextOfSetHour);
        editTextOfHour.setText(Integer.toString(currentTime.getHours()));
        editTextOfHour.setEnabled(false);
    }
    public void setDate(View view){
        Calendar cal= Calendar.getInstance();
        int yearm=cal.get(Calendar.YEAR);
        int monthm=cal.get(Calendar.MONTH)+1;
        int daym=cal.get(Calendar.DAY_OF_MONTH);

        JalaliCalendar.gDate miladiCal=new JalaliCalendar.gDate(yearm,monthm,daym);
        JalaliCalendar.gDate jalaliCal= JalaliCalendar.MiladiToJalali(miladiCal);
        EditText editTextOfDay=(EditText) findViewById(R.id.editTextOfsetDay);
        editTextOfDay.setText(Integer.toString(jalaliCal.getDay()));
        editTextOfDay.setEnabled(false);
        EditText editTextOfMonth=(EditText) findViewById(R.id.editTextOfSetMonth);
        editTextOfMonth.setText(Integer.toString(jalaliCal.getMonth()));
        editTextOfMonth.setEnabled(false);
        EditText editTextOfyear=(EditText) findViewById(R.id.editTextOfSetYear);
        editTextOfyear.setText(Integer.toString(jalaliCal.getYear()).substring(2));
        editTextOfyear.setEnabled(false);
    }
    public void setDetails(View view) {
        String getedString = "";
        if (newFragment.equals("Channeloff")) {
            Channel_Off fragment = (Channel_Off) getSupportFragmentManager().findFragmentByTag("Channeloff");
            getedString = fragment.getString();
            if (getedString.equals("error")) {
                return;
            }else {
                getedString = "1#" + numberOfChannel + "#" + getedString;
            }
        }
        if (newFragment.equals("Channelon")) {
            channelOn fragment = (channelOn) getSupportFragmentManager().findFragmentByTag("Channelon");
            getedString = fragment.getString();
            if (getedString.equals("error")) {
                return;
            }else {
                getedString = "1#" + numberOfChannel + "#" + getedString;
            }

        }
        if (newFragment.equals("ShowChannelStateOff")) {
            return;
        }
        if (newFragment.equals("ShowChannelStateOn")) {
            return;
        }
        if(newFragment.equals("showOperationCorrect")){
            return;
        }
        if(newFragment.equals("showOperationWrong")){
            return;
        }

        if (newFragment.equals("configDevice")) {
            ConfigOfDevice fragment = (ConfigOfDevice) getSupportFragmentManager().findFragmentByTag("configDevice");
            getedString = fragment.getString();
            if (getedString.equals("error")) {
                return;
            } else {
                getedString = "3#" + getedString;

            }

        }
        setCall(getedString);
    }
//////////////////////////////////////////
    public void setCall(String massages){



        if(simpleSwitch.isChecked()){
            Button button=(Button)findViewById(R.id.buttonOfGetDetails);
            button.setEnabled(false);
            button.setText("لطفامنتظر بمانید");

        }else{

        }
        sentMassage=massages;
        callingWithDevice.connectWithDevice(simpleSwitch.isChecked(),massages);
    }
    //////////////////////////////////////////////

    /////////////////////////////////////////



    //////////////////////////////////////////////


    //////////////////////////////////////////////

    //////////////////////////////////////////////
    public void getRecievedSMSMassage(String SMSMessage){

        if (newFragment.equals("configDevice")){
            ConfigOfDevice fragment = (ConfigOfDevice) getSupportFragmentManager().findFragmentByTag("configDevice");
            String getedString=fragment.getString();
            if (getedString.equals("1#2")){

            }else{
                if(SMSMessage.charAt(0)=='0'){
                    set_NewFragment("showOperationWrong");
                    changeFragment("showOperationWrong");
                }
                if(SMSMessage.charAt(0)=='1'){
                    set_NewFragment("showOperationCorrect");
                    changeFragment("showOperationCorrect");
                }
            }
        }else{
            if(SMSMessage.charAt(0)=='0'){
                set_NewFragment("showOperationWrong");
                changeFragment("showOperationWrong");
            }
            if(SMSMessage.charAt(0)=='1'){
                set_NewFragment("showOperationCorrect");
                changeFragment("showOperationCorrect");
            }
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        callingWithDevice.unRegesteredSMS();
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        newFragment="";
        lastFragment="";

    }
    @Override
    public void onResume(){
        super.onResume();
        callingWithDevice.setRegisteredSMS();
    }
}
