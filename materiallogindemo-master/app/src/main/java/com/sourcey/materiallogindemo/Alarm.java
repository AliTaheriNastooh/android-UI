package com.sourcey.materiallogindemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, " d");
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "!!!!!! یادآوری سیستم کشاورزی !!!!!!!", Toast.LENGTH_LONG).show(); // For example
        ReminderTemplate reminderTemplate=(ReminderTemplate) intent.getSerializableExtra("reminderTemplate");
        Intent intentNotification = new Intent(context, Agriculture_system.class);
        intentNotification.putExtra("device",reminderTemplate.getDevice());
        intentNotification.putExtra("positionDevice",reminderTemplate.getDevicePosition());
        intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentNotification, 0);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("یادآوری نرم افزار مدیریت کشاورزی")
                .setContentText(reminderTemplate.getDetails() +  " ساعت "+reminderTemplate.getMinuteOfWakeUp() +":"+reminderTemplate.getHourOfWakeUp())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setSound(alarmSound)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(reminderTemplate.getDetails()+  " ساعت "+reminderTemplate.getHourOfWakeUp() +":"+reminderTemplate.getMinuteOfWakeUp()   ))
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(reminderTemplate.getId(), mBuilder.build());
        wl.release();
    }

    public void setAlarm(Context context,ReminderTemplate reminderTemplate)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        i.putExtra("reminderTemplate",reminderTemplate);
//        i.putExtra("positionDevice",reminderTemplate.getDevicePosition());
        PendingIntent pi = PendingIntent.getBroadcast(context, reminderTemplate.getId(),i, 0);
        String period=reminderTemplate.getPeriod();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminderTemplate.getHourOfWakeUp());
        calendar.set(Calendar.MINUTE, reminderTemplate.getMinuteOfWakeUp());
        if (period.equals("everyDay")){
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }else {
            if (period.equals("every2Day")){
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY *2, pi);
            }else {
                if (period.equals("everyWeek")){
                    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY *7, pi);
                }else {
                    if (period.equals("everyMonth")) {
                        Calendar cal = Calendar.getInstance();
                        // get current month
                        int currentMonth = cal.get(Calendar.MONTH);

                        // move month ahead
                        currentMonth++;
                        // check if has not exceeded threshold of december

                        if (currentMonth > Calendar.DECEMBER) {
                            // alright, reset month to jan and forward year by 1 e.g fro 2013 to 2014
                            currentMonth = Calendar.JANUARY;
                            // Move year ahead as well
                            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
                        }

                        // reset calendar to next month
                        cal.set(Calendar.MONTH, currentMonth);
                        // get the maximum possible days in this month
                        int maximumDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                        // set the calendar to maximum day (e.g in case of fEB 28th, or leap 29th)
                        cal.set(Calendar.DAY_OF_MONTH, maximumDay);
                        long thenTime = cal.getTimeInMillis();
                        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), thenTime, pi);
                    }
                }
            }
        }
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 1, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}