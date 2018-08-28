package com.sourcey.materiallogindemo;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class chooseDevice extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    User user;
    boolean flag_icon=false;
    private Menu menu;
    ListView listView;
    int counter=1;
    int request_code=1;
    int requset_code_agriculture=2;
    boolean flagUser=false;
    CustomListAdapter whatever;
    ArrayList<String> nameArray=new ArrayList<String>();
    MyArrayList<Device> devices=new MyArrayList<Device>();
    public File cacheDir;
    Device tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_device);


        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"MyCustomObject");
        else
            cacheDir= getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();

        user=(User)getIntent().getSerializableExtra(LoginActivity.TAG);
        if(user==null){
            flagUser=false;
        }
        else{
            flagUser=true;
        }
        whatever = new CustomListAdapter(this, nameArray, devices);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(whatever);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(devices.get(position).getModel().equals("agriculture")) {
                    Intent i = new Intent(getApplicationContext(), Agriculture_system.class);
                    i.putExtra("device", devices.get(position));
                    i.putExtra("positionDevice",position);
                    startActivityForResult(i,requset_code_agriculture);
                }
            }
        });
        registerForContextMenu(listView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SetDeviceDetails.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.push_left_in, R.anim.push_left_out);
              //  startActivity(i, options.toBundle());
                i.putExtra("editVersion",false);
                startActivityForResult(i, request_code,options.toBundle());

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        changeDataOfNavigationDrawble();
                    }
                }
        );
        loadPreferences();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_code) {
            if (resultCode == RESULT_OK) {

                tmp=(Device) data.getSerializableExtra("device");
                boolean editVersion=data.getBooleanExtra("editVersion",false);
                if (editVersion){
                    int devicePosition1=data.getIntExtra("devicePosition",-1);
                    devices.set(devicePosition1,tmp);
                    nameArray.set(devicePosition1,tmp.getName());
                }else{
                    devices.add(tmp);
                    nameArray.add(tmp.getName());
                }
                whatever.notifyDataSetChanged();
                //setContentOfListView(tmp.getName());
            }
        }
        if (requestCode == requset_code_agriculture) {
            if (resultCode == RESULT_OK) {
                Device deviceIntent = (Device) data.getSerializableExtra("device");
                int devicePosition = data.getIntExtra("positionDevice",0);
                devices.set(devicePosition,deviceIntent);
                whatever.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(devices.get(info.position).getName());
            String[] menuItems = getResources().getStringArray(R.array.createMenuOption_array);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.createMenuOption_array);
        if(menuItemIndex==0){
            Intent i = new Intent(getApplicationContext(), SetDeviceDetails.class);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.push_left_in, R.anim.push_left_out);
            //  startActivity(i, options.toBundle());
            i.putExtra("editVersion",true);
            i.putExtra("device",devices.get(info.position));
            i.putExtra("devicePosition",info.position);
            startActivityForResult(i, request_code,options.toBundle());
        }else{
            if(menuItemIndex==1){
                AlertDialog.Builder adb=new AlertDialog.Builder(chooseDevice.this);
                adb.setTitle("Delete?");
                adb.setMessage("شما مطمئن هستید که میخواهید " +devices.get(info.position).getName()+ "را حذف کنید" );
                final int positionToRemove = info.position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nameArray.remove(positionToRemove);
                        devices.remove(positionToRemove);

                        whatever.setDevices(devices);

                        whatever.notifyDataSetChanged();
                    }});
                adb.show();


            }
        }

        return true;
    }
    public void setContentOfListView(String deviceName){
        nameArray.add(deviceName);

        whatever.notifyDataSetChanged();

    }
    public void changeDataOfNavigationDrawble(){
        TextView tUserName=(TextView)findViewById(R.id.textViewName);
        TextView tEmail=(TextView)findViewById(R.id.textViewEmail);
        TextView tMobile=(TextView)findViewById(R.id.textViewMobile);
        tUserName.setText(user.getUsername());
        tEmail.setText(user.getEmail());
        tMobile.setText(user.getMobile());
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu=menu;
        if(!flagUser) {
            user = getObject(getApplicationContext(), cacheDir);
        }
        if (user.getRememberMe().equals("true")) {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.lock_open));
            flag_icon=true;
        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.lock_close));
            flag_icon=false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.lockIcon) {
            flag_icon=!flag_icon;
            if(flag_icon){
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.lock_open));
            }else{
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.lock_close));
            }
            user.setRememberMe(String.valueOf(flag_icon));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause() {
        savePreferences();
        super.onPause();

    }
    @Override
    public void onResume(){
        super.onResume();

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout mdrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int id = item.getItemId();

        if (id == R.id.nav_device) {
            // Handle the camera action
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_history) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_setting) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_aboutUs) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_connenctWithUs) {
            mdrawer.openDrawer(GravityCompat.START);

        }

        mdrawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loadPreferences(){

        MyArrayList<Device> devicestmp = devices.getObject(getApplicationContext(),cacheDir,"Device");

        if(devicestmp!= null) {
            devices=devicestmp;
            whatever.setDevices(devices);
            //Toast.makeText(this, "Retrieved object", Toast.LENGTH_LONG).show();
            for(int i=0;i<devices.size();i++){
                setContentOfListView(devices.get(i).getName());
            }
        }else {

            //Toast.makeText(this, "Error retrieving object", Toast.LENGTH_LONG).show();

        }
    }
    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        String unameValue = user.getUsername();
        String passwordValue = user.getPassworde();
        String emailValue=user.getEmail();
        String mobileValue=user.getMobile();
        String rememberValue=user.getRememberMe();
        System.out.println("onPause save username: " + unameValue);
        System.out.println("onPause save password: " + passwordValue);
        System.out.println("onPause save mobile" + mobileValue);
        System.out.println("onPause save email: " + emailValue);
        System.out.println("onPause save rememberMe: " + rememberValue);

        editor.putString(MainActivity.PREF_UNAME, unameValue);
        editor.putString(MainActivity.PREF_PASSWORD, passwordValue);
        editor.putString(MainActivity.PREF_EMAIL, emailValue);
        editor.putString(MainActivity.PREFS_MOBILE, mobileValue);
        editor.putString(MainActivity.PREF_REMEMBER, rememberValue);
        editor.commit();
        boolean result =devices.saveObject(devices,cacheDir,"Device");
        boolean resultUser=user.saveObject(user,cacheDir);
    }
}
