package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Channel_Off extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_channel__off, container, false);
        TextView textOfshowChanneloff=(TextView) view.findViewById(R.id.textOfChannelOff);
        textOfshowChanneloff.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        Bundle args = getArguments();
        String index = args.getString("message", "");
        TextView textOfshowChanneltimeOpne=(TextView) view.findViewById(R.id.textViewOftimeChannelOpen);
        textOfshowChanneltimeOpne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

        LinearLayout l1=(LinearLayout)view.findViewById(R.id.layoutOfChannelTimeOn);
        l1.setVisibility(View.INVISIBLE);

        LinearLayout l2=(LinearLayout)view.findViewById(R.id.layoutOfChannelStartTime);
        l2.setVisibility(View.INVISIBLE);

        LinearLayout l3=(LinearLayout)view.findViewById(R.id.layoutOfChannelPeriod);
        l3.setVisibility(View.INVISIBLE);

        RadioGroup rGroup = (RadioGroup)view.findViewById(R.id.radioGroupOfChannelOff);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId){
                    case R.id.radioButtonOfDeleteOrder:
                        LinearLayout l10=(LinearLayout)view.findViewById(R.id.layoutOfChannelTimeOn);
                        l10.setVisibility(View.INVISIBLE);

                        LinearLayout l20=(LinearLayout)view.findViewById(R.id.layoutOfChannelStartTime);
                        l20.setVisibility(View.INVISIBLE);

                        LinearLayout l30=(LinearLayout)view.findViewById(R.id.layoutOfChannelPeriod);
                        l30.setVisibility(View.INVISIBLE);

                        break;
                    case R.id.radioButtonOfOpenImmediate:
                        RadioButton checkedRadioButton1 = (RadioButton)group.findViewById(checkedId);
                        // This puts the value (true/false) into the variable
                        boolean isChecked1 = checkedRadioButton1.isChecked();
                        // If the radiobutton that has changed in check state is now checked...
                        if (isChecked1)
                        {
                            LinearLayout l11=(LinearLayout)view.findViewById(R.id.layoutOfChannelTimeOn);
                            l11.setVisibility(View.VISIBLE);
                            LinearLayout l21=(LinearLayout)view.findViewById(R.id.layoutOfChannelStartTime);
                            l21.setVisibility(View.INVISIBLE);

                            LinearLayout l31=(LinearLayout)view.findViewById(R.id.layoutOfChannelPeriod);
                            l31.setVisibility(View.INVISIBLE);
                        }

                        break;
                    case R.id.radioButtonOfOpenWithDelay:
                        RadioButton checkedRadioButton2 = (RadioButton)group.findViewById(checkedId);
                        // This puts the value (true/false) into the variable
                        boolean isChecked2 = checkedRadioButton2.isChecked();
                        // If the radiobutton that has changed in check state is now checked...
                        if (isChecked2)
                        {
                            LinearLayout l12=(LinearLayout)view.findViewById(R.id.layoutOfChannelTimeOn);
                            l12.setVisibility(View.VISIBLE);

                            LinearLayout l22=(LinearLayout)view.findViewById(R.id.layoutOfChannelStartTime);
                            l22.setVisibility(View.VISIBLE);
                            LinearLayout l32=(LinearLayout)view.findViewById(R.id.layoutOfChannelPeriod);
                            l32.setVisibility(View.INVISIBLE);
                        }


                        break;
                    case R.id.radioButtonOfPeriodicProgram:
                        RadioButton checkedRadioButton3 = (RadioButton)group.findViewById(checkedId);
                        // This puts the value (true/false) into the variable
                        boolean isChecked3 = checkedRadioButton3.isChecked();
                        // If the radiobutton that has changed in check state is now checked...
                        if (isChecked3)
                        {
                            LinearLayout l13=(LinearLayout)view.findViewById(R.id.layoutOfChannelTimeOn);
                            l13.setVisibility(View.VISIBLE);

                            LinearLayout l23=(LinearLayout)view.findViewById(R.id.layoutOfChannelStartTime);
                            l23.setVisibility(View.VISIBLE);

                            LinearLayout l33=(LinearLayout)view.findViewById(R.id.layoutOfChannelPeriod);
                            l33.setVisibility(View.VISIBLE);
                        }


                        break;
                }


            }
        });

        return view;
    }


    public String getString(){
        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfChannelOff);
        switch (rGroup.getCheckedRadioButtonId()){
            case R.id.radioButtonOfOpenImmediate:
                EditText timeOfOpen = (EditText)getActivity().findViewById(R.id.editTextOfOpenTime);
                return "1#"+timeOfOpen.getText()+"*";
            case R.id.radioButtonOfOpenWithDelay:
                EditText startTimeToOpneInHour = (EditText)getActivity().findViewById(R.id.editTextOfstartOpenInHour);
                EditText startTimeToOpneInMinute = (EditText)getActivity().findViewById(R.id.editTextOfStartToOpenInMinute);
                EditText timeOfOpen2 = (EditText)getActivity().findViewById(R.id.editTextOfOpenTime);
                return "2#"+startTimeToOpneInHour.getText()+"*"+startTimeToOpneInMinute.getText()+"*"+timeOfOpen2.getText()+"*";
            case R.id.radioButtonOfPeriodicProgram:
                EditText startTimeToOpneInHour3 = (EditText)getActivity().findViewById(R.id.editTextOfstartOpenInHour);
                EditText startTimeToOpneInMinute3 = (EditText)getActivity().findViewById(R.id.editTextOfStartToOpenInMinute);
                EditText timeOfOpen3 = (EditText)getActivity().findViewById(R.id.editTextOfOpenTime);
                EditText periodicTime3 = (EditText)getActivity().findViewById(R.id.editTextOfPeriodicTime);
                return "3#"+periodicTime3.getText()+"*"+startTimeToOpneInHour3.getText()+"*"+startTimeToOpneInMinute3.getText()+"*"+timeOfOpen3.getText()+"*";
            case R.id.radioButtonOfDeleteOrder:
                return "4#";
            default:
                return "error";
        }
    }
//    public String getStringCall(){
//        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfChannelOff);
//        switch (rGroup.getCheckedRadioButtonId()){
//            case R.id.radioButtonOfOpenImmediate:
//                EditText timeOfOpen = (EditText)getActivity().findViewById(R.id.editTextOfOpenTime);
//                return "1,"+timeOfOpen.getText()+"*";
//            case R.id.radioButtonOfOpenWithDelay:
//                EditText startTimeToOpneInHour = (EditText)getActivity().findViewById(R.id.editTextOfstartOpenInHour);
//                EditText startTimeToOpneInMinute = (EditText)getActivity().findViewById(R.id.editTextOfStartToOpenInMinute);
//                EditText timeOfOpen2 = (EditText)getActivity().findViewById(R.id.editTextOfOpenTime);
//                return "2,"+startTimeToOpneInHour.getText()+"*"+startTimeToOpneInMinute.getText()+"*"+timeOfOpen2.getText()+"*";
//            case R.id.radioButtonOfPeriodicProgram:
//                EditText startTimeToOpneInHour3 = (EditText)getActivity().findViewById(R.id.editTextOfstartOpenInHour);
//                EditText startTimeToOpneInMinute3 = (EditText)getActivity().findViewById(R.id.editTextOfStartToOpenInMinute);
//                EditText timeOfOpen3 = (EditText)getActivity().findViewById(R.id.editTextOfOpenTime);
//                EditText periodicTime3 = (EditText)getActivity().findViewById(R.id.editTextOfPeriodicTime);
//                return "3,"+periodicTime3.getText()+"*"+startTimeToOpneInHour3.getText()+"*"+startTimeToOpneInMinute3.getText()+"*"+timeOfOpen3.getText()+"*";
//            case R.id.radioButtonOfDeleteOrder:
//                return "4";
//            default:
//                return "error";
//        }
//    }


}
