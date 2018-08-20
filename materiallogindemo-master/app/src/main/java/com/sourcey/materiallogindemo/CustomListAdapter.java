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

public class CustomListAdapter extends ArrayAdapter {
    //to reference the Activity
    private final Activity context;

    //to store the animal images
    private final ArrayList<Integer> imageIDarray;

    //to store the list of countries
    private final ArrayList<String> nameArray;

    //to store the list of countries
    private final ArrayList<String> infoArray;

    public CustomListAdapter(Activity context, ArrayList<String> nameArrayParam, ArrayList<String> infoArrayParam, ArrayList<Integer> imageIDArrayParam){

        super(context,R.layout.listview_row , nameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.device_name);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.device_address);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.device_image);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(nameArray.get(position));
        infoTextField.setText(infoArray.get(position));
      //  imageView.setImageResource(imageIDarray[position]);
        final ImageView img = (ImageView) rowView.findViewById(R.id.device_image);
        Glide.with(context).load(imageIDarray.get(position)).asBitmap().fitCenter().into(new BitmapImageViewTarget(img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                img.setImageDrawable(circularBitmapDrawable);
            }
        });


        return rowView;

    };
}
