package com.sourcey.materiallogindemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

public class AutoStart extends BroadcastReceiver
{
    Alarm alarm = new Alarm();
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            MyArrayList<ReminderTemplate> tmpReminder=loadContent(context);
            for (int i=0;i<tmpReminder.size();i++){
                alarm.setAlarm(context,tmpReminder.get(i));
            }
            //alarm.setAlarm(context);
        }
    }
    public MyArrayList<ReminderTemplate> loadContent(Context context){
        File cacheDir;
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir= context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        MyArrayList<ReminderTemplate> tmpReminder=new MyArrayList<>();

        MyArrayList<ReminderTemplate> tmpReminder2 = tmpReminder.getObject(context,cacheDir,"reminderTemplate");

        if(tmpReminder2!= null) {
            return tmpReminder2;
        }
        return tmpReminder;
    }
}
