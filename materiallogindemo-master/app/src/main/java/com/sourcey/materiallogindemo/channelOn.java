package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


public class channelOn extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_channel_on, container, false);
        TextView textOfshowChannelOn=(TextView) view.findViewById(R.id.TextOfShowChannelOn);
        textOfshowChannelOn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        return view;
    }

    public String getString(){
        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfChannelOn);
        switch (rGroup.getCheckedRadioButtonId()){
            case R.id.radioButtonOfDeleteProject:
                return "1#"+"2#";
            case R.id.radioButtonOfCloseChannel:
                return "1#"+"1#";

            default:
                return "error";
        }
    }

    public String[] getStringForOperation(){
        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfChannelOn);
        switch (rGroup.getCheckedRadioButtonId()){
            case R.id.radioButtonOfDeleteProject:
                String[] tmp={"بستن کانال          " ,"پاک کردن پروژه " };
                return tmp;
            case R.id.radioButtonOfCloseChannel:
                String[] tmp2={"بستن کانال          " ,"بستن کانال " };
                return tmp2;

            default:
                String[] tmp3={"error          " ,"error" };
                return tmp3;
        }
    }
//    public String getStringCall(){
//        RadioGroup rGroup = (RadioGroup)getActivity().findViewById(R.id.radioGroupOfChannelOn);
//        switch (rGroup.getCheckedRadioButtonId()){
//            case R.id.radioButtonOfDeleteProject:
//                return "2";
//            case R.id.radioButtonOfCloseChannel:
//                return "1";
//
//            default:
//                return "error";
//        }
//    }
}
