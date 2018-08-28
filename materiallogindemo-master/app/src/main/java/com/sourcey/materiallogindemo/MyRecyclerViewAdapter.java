package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private final Activity context;
    private MyArrayList<Operation> operations;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, MyArrayList<Operation> data) {
        this.mInflater = LayoutInflater.from(context);
        this.operations = data;
        this.context= (Activity) context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.operation_listview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titleTextField.setText(operations.get(position).getTitle());
        holder.infoTextField.setText(operations.get(position).getInfo());
        String concatinate= "زمان: " + operations.get(position).getTime()+"     " +"تاریخ:  "+operations.get(position).getDate();
        holder.dateAndTimeTextField.setText(concatinate);
        holder. statusTextField.setText(operations.get(position).getTextOfImage());
        Glide.with(context).load(operations.get(position).getImage()).asBitmap().fitCenter().into(new BitmapImageViewTarget(holder.img) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.img.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return operations.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextField;
        TextView infoTextField ;
        TextView dateAndTimeTextField ;
        TextView statusTextField;
        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);

            titleTextField = (TextView) itemView.findViewById(R.id.operationTitle);
            infoTextField = (TextView) itemView.findViewById(R.id.operationInfo);
            dateAndTimeTextField = (TextView) itemView.findViewById(R.id.operationDateAndTime);
            statusTextField = (TextView) itemView.findViewById(R.id.operationStatus);
            img = (ImageView) itemView.findViewById(R.id.operation_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}