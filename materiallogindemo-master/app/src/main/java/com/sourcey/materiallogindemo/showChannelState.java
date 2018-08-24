package com.sourcey.materiallogindemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class showChannelState extends Fragment {
    public static showChannelState newInstance(String index) {
        showChannelState f = new showChannelState();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("index", index);
        f.setArguments(args);
        return f;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_show_channel_state, container, false);
        TextView showChannelState=(TextView)view.findViewById(R.id.textViewOfShowChannelState);
        Bundle args = getArguments();
        String index = args.getString("message", "");
        showChannelState.setText(index);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageViewShowChannelState);
        if(index.equals("عملیات با موقیت انجام شد")){
            imageView.setImageResource(R.drawable.yes_check);
            showChannelState.setTextColor(R.color.green);
        }else {
            if (index.equals("عملیات انجام نگرفت")) {
                imageView.setImageResource(R.drawable.no_check);
            }else{
                imageView.setVisibility(View.INVISIBLE);
            }
        }
       showChannelState.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f);
        return view;
    }


}
