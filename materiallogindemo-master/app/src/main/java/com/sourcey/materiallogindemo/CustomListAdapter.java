package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import javax.crypto.spec.DESedeKeySpec;

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private  MyArrayList<Device> devices;


    public CustomListAdapter(Activity context, ArrayList<String> nameArrayParam, MyArrayList<Device> device){

        super(context,R.layout.listview_row , nameArrayParam);

        this.context=context;
        devices=device;

    }
    public void setDevices(MyArrayList<Device> _devices){
        devices=_devices;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.device_name);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.device_address);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.device_image);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(devices.get(position).getName());
        infoTextField.setText(devices.get(position).getAddress());
      //  imageView.setImageResource(imageIDarray[position]);
        if(devices.get(position).getDefaultImage()){
            final ImageView img = (ImageView) rowView.findViewById(R.id.device_image);
            Glide.with(context).load(devices.get(position).getImage()).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });
        }else {
            final ImageView img = (ImageView) rowView.findViewById(R.id.device_image);
            Glide.with(context).load(devices.get(position).getCustomImage()).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    img.setImageDrawable(circularBitmapDrawable);
                }
            });
        }



        return rowView;

    };
}
