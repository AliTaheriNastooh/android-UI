package com.sourcey.materiallogindemo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String rememberMe;
    public User(){

    }
    public User(String _usernam,String _password,String _email,String _mobile,String _rememberMe){
        username=_usernam;
        password=_password;
        email=_email;
        mobile=_mobile;
        rememberMe=_rememberMe;
    }
    public void setContent(String _usernam,String _password,String _email,String _mobile,String _rememberMe){
        username=_usernam;
        password=_password;
        email=_email;
        mobile=_mobile;
        rememberMe=_rememberMe;
    }
    public void setRememberMe(String s){
        rememberMe=s;
    }
    public String getUsername(){
        return username;
    }
    public String getPassworde(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getMobile(){
        return mobile;
    }
    public String getRememberMe(){
        return rememberMe;
    }

    public boolean saveObject(User obj,File cacheDir) {
        final File suspend_f=new File(cacheDir, "user");

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
    public User getObject(Context c, File cacheDir) {
        final File suspend_f=new File(cacheDir, "user");

        User simpleClass= null;
        FileInputStream fis = null;
        ObjectInputStream is = null;

        try {
            fis = new FileInputStream(suspend_f);
            is = new ObjectInputStream(fis);
            simpleClass = (User) is.readObject();
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
