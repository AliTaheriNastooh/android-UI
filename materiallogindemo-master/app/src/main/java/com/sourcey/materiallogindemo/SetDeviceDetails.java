package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.R;

public class SetDeviceDetails extends AppCompatActivity {
    String stateOfSpinner="nothing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_device_details);

        NoDefaultSpinner2 spinner = (NoDefaultSpinner2) findViewById(R.id.spinnerOfDevice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.device_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                switch(position){
                    case 0:
                        stateOfSpinner="agriculture";
                        break;
                    case 1:
                        stateOfSpinner="building";
                        break;
                    case 2:
                        stateOfSpinner="parking";
                        break;
                    default:

                }

            }


            public void onNothingSelected(AdapterView<?> parent) {
                stateOfSpinner="nothing";
            }
        });
    }
    public void buttonTaped(View view){
        if(validate()){
            Intent data = new Intent();
            EditText deviceName=(EditText)findViewById(R.id.editText_devicName);
            EditText deviceAddress=(EditText)findViewById(R.id.editText_deviceAdress);
            EditText devicePhoneNumber=(EditText)findViewById(R.id.editText_devicePhoneNumber);
            data.putExtra("deviceName",deviceName.getText().toString());
            data.putExtra("deviceAddress",deviceAddress.getText().toString());
            data.putExtra("devicePhoneNumber",devicePhoneNumber.getText().toString());
            data.putExtra("deviceModel",stateOfSpinner);
            setResult(RESULT_OK,data);
            finish();
        }
    }
    public boolean validate(){
        EditText deviceName=(EditText)findViewById(R.id.editText_devicName);
        EditText deviceAddress=(EditText)findViewById(R.id.editText_deviceAdress);
        EditText devicePhoneNumber=(EditText)findViewById(R.id.editText_devicePhoneNumber);
        if(deviceName.getText().toString().isEmpty()){
            deviceName.setError("نام دستگاه را وارد کنید");
            return false;
        }
        if( deviceAddress.getText().toString().isEmpty()){
           deviceAddress.setError("آدرس دستگاه را وارد کنید");
           return false;
        }
        if(devicePhoneNumber.getText().toString().length()!=11){
            devicePhoneNumber.setError("شماره تلفن باید یازده رقم باشد");
            return false;
        }
        if(stateOfSpinner.equals("nothing")){
            Toast.makeText(getApplicationContext(),"یکی از دستگاه ها را انتخاب کنید",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
