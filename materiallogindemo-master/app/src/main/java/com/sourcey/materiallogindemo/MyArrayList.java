package com.sourcey.materiallogindemo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MyArrayList<E> extends ArrayList<E> implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean saveObject(MyArrayList obj,File cacheDir) {
        final File suspend_f=new File(cacheDir, "test");

        FileOutputStream fos  = null;
        ObjectOutputStream oos  = null;
        boolean            keep = true;

        try {
            fos = new FileOutputStream(suspend_f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (Exception e) {
            keep = false;
        } finally {
            try {
                if (oos != null)   oos.close();
                if (fos != null)   fos.close();
                if (keep == false) suspend_f.delete();
            } catch (Exception e) { /* do nothing */ }
        }

        return keep;
    }
    public MyArrayList getObject(Context c,File cacheDir) {
        final File suspend_f=new File(cacheDir, "test");

        MyArrayList<Device> simpleClass= null;
        FileInputStream fis = null;
        ObjectInputStream is = null;

        try {
            fis = new FileInputStream(suspend_f);
            is = new ObjectInputStream(fis);
            simpleClass = (MyArrayList<Device>) is.readObject();
        } catch(Exception e) {
            String val= e.getMessage();
        } finally {
            try {
                if (fis != null)   fis.close();
                if (is != null)   is.close();
            } catch (Exception e) { }
        }

        return simpleClass;
    }
}
