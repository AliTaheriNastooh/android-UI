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
    private MyArrayList<Operation> operations;
    private String whichClass="nothing";

    public CustomListAdapter(Activity context, ArrayList<String> nameArrayParam, MyArrayList<Device> device){

        super(context,R.layout.listview_row , nameArrayParam);
        whichClass="chooseDevice";
        this.context=context;
        devices=device;

    }


    public CustomListAdapter(Activity context, MyArrayList<Operation> operation, ArrayList<String> nameArrayParam){

        super(context,R.layout.operation_listview_row , nameArrayParam);
        whichClass="agriculture_system";
        this.context=context;
        operations=operation;

    }
    public void setOperations(MyArrayList<Operation> _operation){
        operations=_operation;
    }
    public void setDevices(MyArrayList<Device> _devices){
        devices=_devices;
    }
    public View getView(int position, View view, ViewGroup parent) {
        if (whichClass.equals("chooseDevice")) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listview_row, null, true);

            //this code gets references to objects in the listview_row.xml file
            TextView nameTextField = (TextView) rowView.findViewById(R.id.device_name);
            TextView infoTextField = (TextView) rowView.findViewById(R.id.device_address);
            //ImageView imageView = (ImageView) rowView.findViewById(R.id.device_image);

            //this code sets the values of the objects to values from the arrays
            nameTextField.setText(devices.get(position).getName());
            infoTextField.setText(devices.get(position).getAddress());
            //  imageView.setImageResource(imageIDarray[position]);
            if (devices.get(position).getDefaultImage()) {
                final ImageView img = (ImageView) rowView.findViewById(R.id.device_image);
                Glide.with(context).load(devices.get(position).getImage()).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        img.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
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
        }else{
            if(whichClass.equals("agriculture_system")){
                LayoutInflater inflater = context.getLayoutInflater();
                View rowView = inflater.inflate(R.layout.operation_listview_row, null, true);

                //this code gets references to objects in the listview_row.xml file
                TextView titleTextField = (TextView) rowView.findViewById(R.id.operationTitle);
                TextView infoTextField = (TextView) rowView.findViewById(R.id.operationInfo);
                TextView dateAndTimeTextField = (TextView) rowView.findViewById(R.id.operationDateAndTime);
                TextView statusTextField = (TextView) rowView.findViewById(R.id.operationStatus);
                //ImageView imageView = (ImageView) rowView.findViewById(R.id.device_image);

                //this code sets the values of the objects to values from the arrays
                titleTextField.setText(operations.get(position).getTitle());
                infoTextField.setText(operations.get(position).getInfo());
                String concatinate= "زمان: " + operations.get(position).getTime()+"     " +"تاریخ:  "+operations.get(position).getDate();
                dateAndTimeTextField.setText(concatinate);
                statusTextField.setText(operations.get(position).getTextOfImage());
                //  imageView.setImageResource(imageIDarray[position]);

                final ImageView img = (ImageView) rowView.findViewById(R.id.operation_image);
                Glide.with(context).load(operations.get(position).getImage()).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        img.setImageDrawable(circularBitmapDrawable);
                    }
                });



                return rowView;
            }
        }
        return null;
    };

}
