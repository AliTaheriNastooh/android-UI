package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    User user=new User();
    @BindView(R.id.input_username_login) EditText _usernameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    public void goToChooseDevicePage(){
        Intent intent = new Intent(getApplicationContext(), chooseDevice.class);
        intent.putExtra(TAG, user);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity

                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                //finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        String getUserName = _usernameText.getText().toString();
        String getPassword = _passwordText.getText().toString();
        if(user.getUsername().equals(getUserName) && user.getPassworde().equals(getPassword)){
//nothing
        }else{
            Toast.makeText(getApplicationContext(),"wrong Usernam or Password",Toast.LENGTH_SHORT).show();
            _loginButton.setEnabled(true);
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        savePreferences();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                //
                //
                //this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        goToChooseDevicePage();
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !(email.length()>=3)) {
            _usernameText.setError("enter a valid username biger than 3 character");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
    public void rememberMeTapped(View view){
        CheckBox cb=(CheckBox)findViewById(R.id.checkBox);
        user.setRememberMe(String.valueOf(cb.isChecked()));
    }
    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
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
    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME,
                Context.MODE_PRIVATE);
         String DefaultUnameValue = "";
        String DefaultPasswordValue = "";
        String DefaultMobieValue = "";
        String DefaultEmailValue = "";
        String DefaultRememberValue = "";
        // Get value
        String unameValue = settings.getString(MainActivity.PREF_UNAME, DefaultUnameValue);
        String passwordValue = settings.getString(MainActivity.PREF_PASSWORD, DefaultPasswordValue);
        String mobieValue = settings.getString(MainActivity.PREFS_MOBILE, DefaultMobieValue);
        String emailValue = settings.getString(MainActivity.PREF_EMAIL, DefaultEmailValue);
        String rememberValue = settings.getString(MainActivity.PREF_REMEMBER, DefaultRememberValue);
        user.setContent(unameValue,passwordValue,emailValue,mobieValue,rememberValue);
        if(user.getRememberMe().equals("true")){
            Toast.makeText(getApplicationContext(),"hey",Toast.LENGTH_SHORT).show();
            goToChooseDevicePage();
        //    finish();
        }
    }
}
