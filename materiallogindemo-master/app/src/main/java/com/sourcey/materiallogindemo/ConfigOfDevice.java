package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;


public class ConfigOfDevice extends Fragment {
    View viewGlobal;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_config_of_device, container, false);
        viewGlobal=view;

        LinearLayout lt=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetTime);
        lt.setVisibility(View.INVISIBLE);

        LinearLayout ld=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetDate);
        ld.setVisibility(View.INVISIBLE);

        LinearLayout la=(LinearLayout)view.findViewById(R.id.linearLayoutOfAddNumber);
        la.setVisibility(View.INVISIBLE);
        LinearLayout ll=(LinearLayout)view.findViewById(R.id.linearLayoutOfHintToShowList);
        ll.setVisibility(View.INVISIBLE);

        RadioGroup rGroup = (RadioGroup)view.findViewById(R.id.radioGroupOfConfigDevice);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId){
                    case R.id.radioButtonOfNumbersList:
                        LinearLayout lt0=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetTime);
                        lt0.setVisibility(View.INVISIBLE);

                        LinearLayout ld0=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetDate);
                        ld0.setVisibility(View.INVISIBLE);

                        LinearLayout la0=(LinearLayout)view.findViewById(R.id.linearLayoutOfAddNumber);
                        la0.setVisibility(View.INVISIBLE);

                        LinearLayout ll0=(LinearLayout)view.findViewById(R.id.linearLayoutOfHintToShowList);
                        ll0.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioButtonOfAddNumber:
                        LinearLayout lt1=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetTime);
                        lt1.setVisibility(View.INVISIBLE);

                        LinearLayout ld1=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetDate);
                        ld1.setVisibility(View.INVISIBLE);

                        LinearLayout la1=(LinearLayout)view.findViewById(R.id.linearLayoutOfAddNumber);
                        la1.setVisibility(View.VISIBLE);

                        LinearLayout ll1=(LinearLayout)view.findViewById(R.id.linearLayoutOfHintToShowList);
                        ll1.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.radioButtonOfsetDate:
                        LinearLayout lt2=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetTime);
                        lt2.setVisibility(View.INVISIBLE);

                        LinearLayout ld2=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetDate);
                        ld2.setVisibility(View.VISIBLE);

                        LinearLayout la2=(LinearLayout)view.findViewById(R.id.linearLayoutOfAddNumber);
                        la2.setVisibility(View.INVISIBLE);

                        LinearLayout ll2=(LinearLayout)view.findViewById(R.id.linearLayoutOfHintToShowList);
                        ll2.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.radioButtonOfsetTime:
                        LinearLayout lt3=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetTime);
                        lt3.setVisibility(View.VISIBLE);

                        LinearLayout ld3=(LinearLayout)view.findViewById(R.id.linearLayoutOfsetDate);
                        ld3.setVisibility(View.INVISIBLE);

                        LinearLayout la4=(LinearLayout)view.findViewById(R.id.linearLayoutOfAddNumber);
                        la4.setVisibility(View.INVISIBLE);

                        LinearLayout ll4=(LinearLayout)view.findViewById(R.id.linearLayoutOfHintToShowList);
                        ll4.setVisibility(View.INVISIBLE);
                        break;
                }


            }
        });
        return view;

    }
    public boolean validate(){
        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfConfigDevice);
        switch (rGroup.getCheckedRadioButtonId()){
            case R.id.radioButtonOfNumbersList:
                return true;
            case R.id.radioButtonOfAddNumber:
                EditText number = (EditText)getActivity().findViewById(R.id.addNumberToConfigList);
                return true;
            case R.id.radioButtonOfsetDate:
                EditText year = (EditText)getActivity().findViewById(R.id.editTextOfSetYear);
                EditText month = (EditText)getActivity().findViewById(R.id.editTextOfSetMonth);
                EditText day = (EditText)getActivity().findViewById(R.id.editTextOfsetDay);
                String sYear="";
                String sMonth="";
                String sDay="";
                if(year.getText().length()>4){
                    year.setError("سال ورودی باید 4 رقم باشد");
                    return false;
                }
                if(month.getText().length()>2){
                    month.setError("ماه ورودی باید 2 رقم باشد");
                    return false;
                }
                if(day.getText().length()>2){
                    day.setError("روز ورودی باید 2 رقم باشد");
                    return false;
                }
                return true;
            case R.id.radioButtonOfsetTime:

                EditText minute = (EditText)getActivity().findViewById(R.id.editTextOfSetMinute);
                EditText hour = (EditText)getActivity().findViewById(R.id.editTextOfSetHour);
                if(minute.getText().length()>2){
                    minute.setError("دقیقه ورودی باید 2 رقم باشد");
                    return false;
                }
                if(hour.getText().length()>2){
                    hour.setError("ساعت ورودی باید 2 رقم باشد");
                    return false;
                }
                return true;
            default:
                return false;
        }
    }
    public String getString(){
        if (!validate()){
            return "error";
        }
        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfConfigDevice);
        switch (rGroup.getCheckedRadioButtonId()){
            case R.id.radioButtonOfNumbersList:
                return "1#2";
            case R.id.radioButtonOfAddNumber:
                EditText number = (EditText)getActivity().findViewById(R.id.addNumberToConfigList);
                return "1#1#"+number.getText()+"*";
            case R.id.radioButtonOfsetDate:
                EditText year = (EditText)getActivity().findViewById(R.id.editTextOfSetYear);
                EditText month = (EditText)getActivity().findViewById(R.id.editTextOfSetMonth);
                EditText day = (EditText)getActivity().findViewById(R.id.editTextOfsetDay);
                String sYear="";
                String sMonth="";
                String sDay="";
                if(year.getText().length()==1 ){
                    sYear="0"+year.getText().toString().charAt(0);
                }
                if(year.getText().length()==4){
                    sYear=year.getText().toString().substring(2);
                }
                if(year.getText().length()==2){
                    sYear=year.getText().toString();
                }
                if(month.getText().length()==1 ){
                    sMonth="0"+month.getText().toString().charAt(0);
                }
                if(month.getText().length()==2){
                    sMonth=month.getText().toString();
                }
                if(day.getText().length()==1 ){
                    sDay="0"+day.getText().toString().charAt(0);
                }
                if(day.getText().length()==2){
                    sDay=day.getText().toString();
                }
                return "2#"+sYear+sMonth+sDay;
            case R.id.radioButtonOfsetTime:

                EditText minute = (EditText)getActivity().findViewById(R.id.editTextOfSetMinute);
                EditText hour = (EditText)getActivity().findViewById(R.id.editTextOfSetHour);
                String sminute="";
                String shour="";
                if(minute.getText().length()==1 ){
                    sminute="0"+minute.getText().toString().charAt(0);
                }
                if(minute.getText().length()==2){
                    sminute=minute.getText().toString();
                }
                if(hour.getText().length()==1 ){
                    shour="0"+hour.getText().toString().charAt(0);
                }
                if(hour.getText().length()==2){
                    shour=hour.getText().toString();
                }
                return "3#"+shour+sminute;
            default:
                return "error";
        }
    }

    public String[] getStringForOperation(){
        String[] tmp={"error","error"};
        if (!validate()){

            return tmp;
        }
        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfConfigDevice);
        switch (rGroup.getCheckedRadioButtonId()){
            case R.id.radioButtonOfNumbersList:
                String[] tmp1={"تنظیمات دستگاه" ,"گرفتن لیست شماره ها" };
                return tmp1;
            case R.id.radioButtonOfAddNumber:
                EditText number = (EditText)getActivity().findViewById(R.id.addNumberToConfigList);
                String[] tmp2={"تنظیمات دستگاه" , "اضافه کردن شماره:" +number.getText().toString()};
                return tmp2;
            case R.id.radioButtonOfsetDate:
                EditText year = (EditText)getActivity().findViewById(R.id.editTextOfSetYear);
                EditText month = (EditText)getActivity().findViewById(R.id.editTextOfSetMonth);
                EditText day = (EditText)getActivity().findViewById(R.id.editTextOfsetDay);
                String sYear="";
                String sMonth="";
                String sDay="";
                if(year.getText().length()==1 ){
                    sYear="0"+year.getText().toString().charAt(0);
                }
                if(year.getText().length()==4){
                    sYear=year.getText().toString().substring(2);
                }
                if(year.getText().length()==2){
                    sYear=year.getText().toString();
                }
                if(month.getText().length()==1 ){
                    sMonth="0"+month.getText().toString().charAt(0);
                }
                if(month.getText().length()==2){
                    sMonth=month.getText().toString();
                }
                if(day.getText().length()==1 ){
                    sDay="0"+day.getText().toString().charAt(0);
                }
                if(day.getText().length()==2){
                    sDay=day.getText().toString();
                }
                String[] tmp3={"تنظیمات دستگاه" , "تنظیم تاریخ به: " +sYear+"/"+sMonth+"/"+sDay};
                return tmp3;
            case R.id.radioButtonOfsetTime:

                EditText minute = (EditText)getActivity().findViewById(R.id.editTextOfSetMinute);
                EditText hour = (EditText)getActivity().findViewById(R.id.editTextOfSetHour);
                String sminute="";
                String shour="";
                if(minute.getText().length()==1 ){
                    sminute="0"+minute.getText().toString().charAt(0);
                }
                if(minute.getText().length()==2){
                    sminute=minute.getText().toString();
                }
                if(hour.getText().length()==1 ){
                    shour="0"+hour.getText().toString().charAt(0);
                }
                if(hour.getText().length()==2){
                    shour=hour.getText().toString();
                }
                String[] tmp4={"تنظیمات دستگاه" , "تنظیم زمان به: " +shour+":"+sminute};
                return tmp4;
            default:
                return tmp;
        }
    }
//    public String getStringCall(){
//        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfConfigDevice);
//        switch (rGroup.getCheckedRadioButtonId()){
//            case R.id.radioButtonOfNumbersList:
//                return "1,2";
//            case R.id.radioButtonOfAddNumber:
//                EditText number = (EditText)getActivity().findViewById(R.id.addNumberToConfigList);
//                return "1,1,"+number.getText()+"*";
//            case R.id.radioButtonOfsetDate:
//                EditText year = (EditText)getActivity().findViewById(R.id.editTextOfSetYear);
//                EditText month = (EditText)getActivity().findViewById(R.id.editTextOfSetMonth);
//                EditText day = (EditText)getActivity().findViewById(R.id.editTextOfsetDay);
//                return "2,"+year.getText()+"*"+month.getText()+"*"+day.getText()+"*";
//            case R.id.radioButtonOfsetTime:
//                EditText minute = (EditText)getActivity().findViewById(R.id.editTextOfSetMinute);
//                EditText hour = (EditText)getActivity().findViewById(R.id.editTextOfSetHour);
//                return "3*"+hour.getText()+"*"+minute.getText()+"*";
//            default:
//                return "error";
//        }
//    }
}
