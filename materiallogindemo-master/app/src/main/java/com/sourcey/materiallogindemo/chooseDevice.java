package com.sourcey.materiallogindemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class chooseDevice extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    User user;
    boolean flag_icon=false;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_device);
        user=(User)getIntent().getSerializableExtra(LoginActivity.TAG);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu=menu;
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
        super.onPause();
        savePreferences();

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_gallery) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_slideshow) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_manage) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_share) {
            mdrawer.openDrawer(GravityCompat.START);

        } else if (id == R.id.nav_send) {
            mdrawer.openDrawer(GravityCompat.START);

        }

        mdrawer.closeDrawer(GravityCompat.START);
        return true;
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
    }
}
