package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

public class Agriculture_system extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    Device device;
    public static final String EXTRA_MESSAGE1 = "com.sourcey.materiallogindemo.MESSAGE";
    /////////////////////
    private Switch simpleSwitch;
    private LinearLayout getNumberOfChannel;
    public  static int state=-1;
    public static String numberOfChannel;
    CallingWithDevice callingWithDevice;
    private int RESULT_LOAD_IMAGE=73;
    private int positionIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture_system);

        Intent intent = getIntent();
        device= (Device) intent.getSerializableExtra("device");
        positionIntent= (int) intent.getSerializableExtra("positionDevice");
        TextView phoneNumber=(TextView)findViewById(R.id.devicePhoneNumber);
        TextView address=(TextView)findViewById(R.id.deviceAdress);
        phoneNumber.setText("شماره تماس دستگاه: " +device.getPhoneNumber());
        address.setText( "آدرس دستگاه: " +device.getAddress());
        final Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("  "+device.getName());
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.primary));
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primary));

        loadBackdrop();

        callingWithDevice=new CallingWithDevice(this,"agricultureSystem",device.getPhoneNumber());

        //////////////////////////
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);
        TextView textOfToggleButton=(TextView)findViewById(R.id.textViewOfToogleButton);
        textOfToggleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

        final TextView textOfChooseAmaliat=(TextView)findViewById(R.id.textofChooseAmaliat);
        textOfChooseAmaliat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);

        getNumberOfChannel=(LinearLayout)findViewById(R.id.layoutOfGetChannelNumber);
        getNumberOfChannel.setVisibility(View.INVISIBLE);

        NoDefaultSpinner spinner = (NoDefaultSpinner) findViewById(R.id.spinnerOfActivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Activity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                switch(position){
                    case 0:
                        TextView textOfGetChannelNumber= (TextView) findViewById(R.id.textViewOfGetChannelNumber);
                        state=1;
                        textOfGetChannelNumber.setText("شماره کانال مورد نظر برای تنظیم راوارد کرده و دکمه تماس را بفشارید:");
                        getNumberOfChannel.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        TextView textOfGetChannelNumber1= (TextView) findViewById(R.id.textViewOfGetChannelNumber);
                        state=2;
                        textOfGetChannelNumber1.setText("شماره کانال مورد نظر برای دریافت گزارش راوارد کرده و دکمه تماس را بفشارید:");
                        getNumberOfChannel.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        state=3;
                        setIntentToNextPage("configDevice");
                        break;
                    default:

                }

                Log.v("item........... :", (String) parent.getItemAtPosition(position)+"  switch : "+ simpleSwitch.isChecked());
            }


            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("item........... :", "nothing Selected");
                // TODO Auto-generated method stub
            }
        });
        EditText ChannelNumber=(EditText)findViewById(R.id.editTextGetChannel);
        ChannelNumber.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                numberOfChannel=editable.toString();
                Log.i("me-------:",numberOfChannel);

            }
        });

        FloatingActionButton fb_agriculture=(FloatingActionButton)findViewById(R.id.fb_agricultureSystem);
        fb_agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            device.setDetails(device.getName(),device.getModel(),device.getAddress(),device.getPhoneNumber(),0,false,picturePath);
            ImageView imageView = (ImageView) findViewById(R.id.backdrop);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            Intent data1 = new Intent();
            data1.putExtra("device",device);
            data1.putExtra("positionDevice",positionIntent);
            setResult(RESULT_OK,data1);
        }
    }
    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        if (device.getDefaultImage()){
            Glide.with(this).load(device.getImage()).asBitmap().centerCrop().into(imageView);
        }else{
            Glide.with(this).load(device.getCustomImage()).asBitmap().centerCrop().into(imageView);
        }

    }
//apply(RequestOptions.centerCropTransform()).into(imageView)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    //////////////////////////

    public void CallButtonForGetChannel(View view){

        String setMessage="";
        EditText getChannelNumber=(EditText)findViewById(R.id.editTextGetChannel);
        setMessage=state+"#"+getChannelNumber.getText().toString();
        callingWithDevice.connectWithDevice(simpleSwitch.isChecked(),setMessage);
    }

    public void getRecievedSMSMassage(String recievedMassage){
        if(recievedMassage.equals("0")||recievedMassage.equals("1") ){
            goToNextPage(recievedMassage);
        }else{
            Toast.makeText(getApplicationContext(),"wrong message",Toast.LENGTH_SHORT).show();
        }
    }

    public void setIntentToNextPage(String sMessage){
        Intent intent1 = new Intent(this, GetDetails.class);
        String message = sMessage;
        intent1.putExtra(EXTRA_MESSAGE1+"1", message);
        intent1.putExtra(EXTRA_MESSAGE1+"2",device);
        intent1.putExtra(EXTRA_MESSAGE1+"3", numberOfChannel);
        startActivity(intent1);
    }
    public void goToNextPage(String channelState){
        if(state==1){
            if(channelState.equals("0")){
                setIntentToNextPage("Channeloff");
            }
            if(channelState.equals("1")){
                setIntentToNextPage("Channelon");
            }
        }
        if(state==2){
            if(channelState.equals("0")){
                setIntentToNextPage("ShowChannelStateOff");
            }
            if(channelState.equals("1")){
                setIntentToNextPage("ShowChannelStateOn");
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    @Override
    public void onPause() {
        super.onPause();
        callingWithDevice.unRegesteredSMS();

    }
    @Override
    public void onResume(){
        super.onResume();
        callingWithDevice.setRegisteredSMS();
    }
}
