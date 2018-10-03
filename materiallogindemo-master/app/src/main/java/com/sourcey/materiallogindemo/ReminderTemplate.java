package com.sourcey.materiallogindemo;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ReminderTemplate implements Serializable {
    private Device device;
    private TemplateOperation template;
    private String period;
    private int hourOfWakeUp;
    private int minuteOfWakeUp;
    private int devicePosition;
    private int id;
    private String id_string;

    public ReminderTemplate (Device _device,TemplateOperation _templateOperation,String _period,int _hourOfWakeUp,int _minuteOfWakeUp ,int _devicePosition){
        device=_device;
        template=_templateOperation;
        period=_period;
        hourOfWakeUp=_hourOfWakeUp;
        devicePosition=_devicePosition;
        minuteOfWakeUp=_minuteOfWakeUp;
        generateId();
    }
    private void generateId(){
        id_string= UUID.randomUUID().toString();
        id=id_string.hashCode();
    }
    public Device getDevice() {
        return device;
    }
    public String getDetails(){
        return   "قالب" + template.getName()+" مربوط به دستگاه " + device.getName()+"را اعمال کن" +" ----- "+" مربوط به یادآوری " +getPersianOfPeriod();
    }


    public String getPersianOfPeriod() {
        if (period.equals("everyDay")){
            return "هر روز" ;
        }else {
            if (period.equals("every2Day")){
                return "هر دو روز یکبار" ;
            }else {
                if (period.equals("everyWeek")){
                    return "هر هفته" ;
                }else {
                    if (period.equals("everyMonth")) {
                        return "هر ماه" ;
                    }
                }
            }
        }
        return "error";
    }

    public int getId() {
        return id;
    }
    public int getDevicePosition() {
        return devicePosition;
    }

    public int getMinuteOfWakeUp() {
        return minuteOfWakeUp;
    }

    public int getHourOfWakeUp() {
        return hourOfWakeUp;
    }

    public String getPeriod() {
        return period;
    }

    public String getId_string() {
        return id_string;
    }

    public TemplateOperation getTemplate() {
        return template;
    }

}
