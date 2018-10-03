package com.sourcey.materiallogindemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private final Activity context;
    private MyArrayList<Operation> operations;
    private MyArrayList<ReminderTemplate> reminders;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<String> selectedIds = new ArrayList<>();
    private String whichClass;
    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, MyArrayList<Operation> data) {
        this.mInflater = LayoutInflater.from(context);
        this.operations = data;
        this.context= (Activity) context;
        whichClass="operations";
    }
    MyRecyclerViewAdapter(Context context, MyArrayList<ReminderTemplate> data,boolean empty) {
        this.mInflater = LayoutInflater.from(context);
        this.reminders = data;
        this.context= (Activity) context;
        whichClass="reminders";
    }
    public void setOperations(MyArrayList<Operation> _operation){
        operations=_operation;
    }
    public void setReminders(MyArrayList<ReminderTemplate> _reminders){
        reminders=_reminders;
    }
    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.operation_listview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(whichClass.equals("operations")) {
            holder.titleTextField.setText(operations.get(position).getTitle());
            holder.infoTextField.setText(operations.get(position).getInfo());
            String concatinate = "زمان: " + operations.get(position).getTime() + "     " + "تاریخ:  " + operations.get(position).getDate();
            holder.dateAndTimeTextField.setText(concatinate);
            holder.statusTextField.setText(operations.get(position).getTextOfImage());
            Glide.with(context).load(operations.get(position).getImage()).asBitmap().fitCenter().into(new BitmapImageViewTarget(holder.img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.img.setImageDrawable(circularBitmapDrawable);
                }
            });


            String id = operations.get(position).getId();

            if (selectedIds.contains(id)) {
                //if item is selected then,set foreground color of FrameLayout.
                holder.rootView.setBackgroundResource(R.color.colorActionMode);
            } else {
                //else remove selected item color.
                holder.rootView.setBackgroundResource(android.R.color.transparent);
            }
        }else{
            holder.titleTextField.setText(" یادآوری در دستگاه "+reminders.get(position).getDevice().getName());
            holder.infoTextField.setText(" مربوط به قالب "+reminders.get(position).getTemplate().getName()+" "+" تکرار " +reminders.get(position).getPersianOfPeriod() );
            String concatinate = "زمان یادآوری: " +reminders.get(position).getHourOfWakeUp()  + ":" +reminders.get(position).getMinuteOfWakeUp()  ;
            holder.dateAndTimeTextField.setText(concatinate);
            holder.statusTextField.setText("یادآوری");
            Glide.with(context).load(R.drawable.ic_reminder).asBitmap().fitCenter().into(new BitmapImageViewTarget(holder.img) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.img.setImageDrawable(circularBitmapDrawable);
                }
            });


            String id = reminders.get(position).getId_string();

            if (selectedIds.contains(id)) {
                //if item is selected then,set foreground color of FrameLayout.
                holder.rootView.setBackgroundResource(R.color.colorActionMode);
            } else {
                //else remove selected item color.
                holder.rootView.setBackgroundResource(android.R.color.transparent);
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (whichClass.equals("operations")){
            return operations.size();
        }
        return reminders.size();
    }


    public Operation getItem(int position){
        return operations.get(position);
    }
    public ReminderTemplate getReminderItem(int position){
        return reminders.get(position);
    }
    public void setSelectedIds(List<String> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextField;
        TextView infoTextField ;
        TextView dateAndTimeTextField ;
        TextView statusTextField;
        ImageView img;
        LinearLayout rootView;
        ViewHolder(View itemView) {
            super(itemView);

            titleTextField = (TextView) itemView.findViewById(R.id.operationTitle);
            infoTextField = (TextView) itemView.findViewById(R.id.operationInfo);
            dateAndTimeTextField = (TextView) itemView.findViewById(R.id.operationDateAndTime);
            statusTextField = (TextView) itemView.findViewById(R.id.operationStatus);
            img = (ImageView) itemView.findViewById(R.id.operation_image);
            itemView.setOnClickListener(this);
            rootView = itemView.findViewById(R.id.root_view);
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