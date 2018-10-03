package com.sourcey.materiallogindemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;



public class ChooseTemplateOperation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private LinearLayout getNumberOfChannel;
    public   int state=-1;
    public  String numberOfChannel;
    private Context mcontext;

    View view;


    public ChooseTemplateOperation() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_choose_template_operation, container, false);
        getNumberOfChannel=(LinearLayout)view.findViewById(R.id.layoutOfGetChannelNumber);
        getNumberOfChannel.setVisibility(View.INVISIBLE);
        NoDefaultSpinner spinner = (NoDefaultSpinner) view.findViewById(R.id.spinnerOfActivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Activity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter,"فرآیند را انتخاب کنید");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view1,
                                       int position, long id) {

                switch(position){
                    case 0:
                        TextView textOfGetChannelNumber= (TextView) view.findViewById(R.id.textViewOfGetChannelNumber);
                        state=1;
                        textOfGetChannelNumber.setText("شماره کانال مورد نظر برای روشن کردن را انتخاب کرده و دکمه مرحله بعد را بفشارید:");
                        getNumberOfChannel.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        TextView textOfGetChannelNumber1= (TextView) view.findViewById(R.id.textViewOfGetChannelNumber);
                        state=2;
                        textOfGetChannelNumber1.setText("شماره کانال مورد نظر برای خاموش کردن را انتخاب کرده و دکمه مرحله بعد را بفشارید:");
                        getNumberOfChannel.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        TextView textOfGetChannelNumber2= (TextView) view.findViewById(R.id.textViewOfGetChannelNumber);
                        state=3;
                        textOfGetChannelNumber2.setText("شماره کانال مورد نظر برای دریافت گزارش را انتخاب کرده و دکمه مرحله بعد را بفشارید:");
                        getNumberOfChannel.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        state=4;
                        getNumberOfChannel.setVisibility(View.INVISIBLE);
//                        TextView textOfGetChannelNumber3= (TextView) findViewById(R.id.textViewOfGetChannelNumber);
//                        NoDefaultSpinner spinner3 = (NoDefaultSpinner) findViewById(R.id.spinnerOfGetChannelNumber);
//                        getNumberOfChannel.setVisibility(View.VISIBLE);
//                        textOfGetChannelNumber3.setVisibility(View.INVISIBLE);
//                        spinner3.setVisibility(View.INVISIBLE);
//                        //Button button1=(Button)findViewById(R.id.buttonofCallDevice);
                        // button1.setVisibility(View.VISIBLE);
                        //setIntentToNextPage("configDevice");
                        break;
                    default:

                }

             //   Log.v("item........... :", (String) parent.getItemAtPosition(position)+"  switch : "+ simpleSwitch.isChecked());
            }


            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("item........... :", "nothing Selected");
                // TODO Auto-generated method stub
            }
        });
        NoDefaultSpinner spinner2 = (NoDefaultSpinner) view.findViewById(R.id.spinnerOfGetChannelNumber);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.channelNumber_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2,"کانال را انتخاب کنید:");
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                numberOfChannel=String.valueOf(position+1);
            }


            public void onNothingSelected(AdapterView<?> parent) {
                numberOfChannel=String.valueOf(-1);
            }
        });
        return view;
    }





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public int getState(){
        return state;
    }
    public String getNumberOfChannel(){
        return numberOfChannel;
    }
}
