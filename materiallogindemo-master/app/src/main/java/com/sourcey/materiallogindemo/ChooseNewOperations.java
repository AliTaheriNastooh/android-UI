package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseNewOperations extends AppCompatActivity {
    String lastFragment="";
    String newFragment="";
    Fragment fragment;
    String numberOfChannel;
    private Device device;
    private int RESULT_GET_DETAIL=75;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_new_operations);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        set_NewFragment(intent.getStringExtra(Agriculture_system.EXTRA_MESSAGE1+"1"));
        device=(Device) intent.getSerializableExtra(Agriculture_system.EXTRA_MESSAGE1+"2");
        numberOfChannel=intent.getStringExtra(Agriculture_system.EXTRA_MESSAGE1+"3");
        TextView devicePhoneNumber=(TextView)findViewById(R.id.devicePhoneNumberOfChooseNewOperation);
        devicePhoneNumber.setText("شماره تماس دستگاه: " +device.getPhoneNumber());
        TextView deviceAddress=(TextView)findViewById(R.id.deviceAdressOfChooseNewOperation);
        deviceAddress.setText( "آدرس دستگاه: " +device.getAddress());
        changeFragment(newFragment);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_GET_DETAIL && resultCode == RESULT_OK && null != data) {
            Intent data3 = new Intent();
            data3.putExtra("operation", data.getSerializableExtra("operation"));
            data3.putExtra("getState",getState());
            data3.putExtra("getChannelNumber",getNumberOfChannel());
            data3.putExtra("whichOperation","newOperation");
            setResultToPreviousActivity(data3);
        }
    }
    private void setResultToPreviousActivity(Intent tmp){
        setResult(RESULT_OK,tmp);
        finish();
    }
    public void set_NewFragment(String set){
        if(!newFragment.isEmpty()){
            lastFragment=newFragment;
        }
        newFragment=set;
    }
    public void changeFragment(String desFragment){
        if (!lastFragment.isEmpty()) {
            Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(lastFragment);
            if (fragment1 != null)
                getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }
        Bundle bundle = new Bundle();
        String myMessage = "";

        fragment=new backgroundFragment() ;
        if(desFragment.equals("useTemplate")){
            fragment=new UseTemplate();
        }
        if(desFragment.equals("chooseTemplateOperation")){
            fragment=new ChooseTemplateOperation();
        }

        bundle.putString("message", myMessage );
        //bundle.putSerializable("context",getApplicationContext());
        fragment.setArguments(bundle);
        FragmentManager fm =getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft=fm.beginTransaction();

        if(desFragment.equals("useTemplate")){
            ft.add(R.id.fragmentTemplate,fragment,"useTemplate");
        }
        if(desFragment.equals("chooseTemplateOperation")){
            ft.add(R.id.fragmentTemplate,fragment,"chooseTemplateOperation");
        }

        try {
            ft.commit();
        }
        catch(Exception exc) {
            Toast.makeText(getApplicationContext(),"hey error commit",Toast.LENGTH_SHORT).show();
        }

    }
    public int getState(){
        ChooseTemplateOperation fragment = (ChooseTemplateOperation) getSupportFragmentManager().findFragmentByTag("chooseTemplateOperation");
        return fragment.getState();
    }

    public String getNumberOfChannel(){
        ChooseTemplateOperation fragment = (ChooseTemplateOperation) getSupportFragmentManager().findFragmentByTag("chooseTemplateOperation");
        return fragment.getNumberOfChannel();
    }
    public void goToNextPage(String channelState) {
        switch (getState()) {
            case 1:
                setIntentToNextPage("Channeloff", GetDetails.class);
                break;
            case 2:
                setIntentToNextPage("Channelon", GetDetails.class);

                break;

            case 3:

                break;

            case 4:
                setIntentToNextPage("configDevice", GetDetails.class);
                break;

            default:

        }
    }
    public void setIntentToNextPage(String sMessage,Class destination){
        Intent intent1 = new Intent(this,destination);
        String message = sMessage;
        intent1.putExtra(Agriculture_system.EXTRA_MESSAGE1+"1", message);
        intent1.putExtra(Agriculture_system.EXTRA_MESSAGE1+"2",device);
        intent1.putExtra(Agriculture_system.EXTRA_MESSAGE1+"3", getNumberOfChannel());
        startActivityForResult(intent1,RESULT_GET_DETAIL);
    }

    public void callButtonForNextState(View view){
        goToNextPage("  ");
    }

    public void addTemplateToList(View view){
        UseTemplate tmp=(UseTemplate) getSupportFragmentManager().findFragmentByTag("useTemplate");
        MyArrayList<Operation> tmpOperation=tmp.getOperations();
        Intent data3 = new Intent();
        data3.putExtra("whichOperation","template");
        Bundle bundle=new Bundle();
        bundle.putSerializable("operations",tmpOperation);
//        data3.putExtra("operations",tmpOperation);
        data3.putExtras(bundle);
        setResultToPreviousActivity(data3);
    }


}
