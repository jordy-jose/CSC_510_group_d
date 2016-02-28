package com.csc510.gradplannerlite;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserInfo extends AppCompatActivity {

    private EditText etName;
    private EditText etDob;
    private EditText etEmail;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitializeControls();
        PopulateEditTexts();
    }

    private void PopulateEditTexts() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_USERINFO, 0);
        etName.setText(settings.getString(SharedPreferencesKeys.UserName, ""));
        etDob.setText(settings.getString(SharedPreferencesKeys.UserDOB, ""));
        etEmail.setText(settings.getString(SharedPreferencesKeys.UserEmail, ""));
        etPhone.setText(settings.getString(SharedPreferencesKeys.UserPhone, ""));
    }

    private void InitializeControls() {
        etName = (EditText)findViewById(R.id.txtName);
        etDob = (EditText)findViewById(R.id.txtDOB);
        etEmail = (EditText)findViewById(R.id.txtEmail);
        etPhone = (EditText)findViewById(R.id.txtPhone);
    }

    public void onClickSubmitBtn(View view) {
        persistUserInfo();
    }

    private void persistUserInfo() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_USERINFO, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.clear();

        editor.putString(SharedPreferencesKeys.UserName, getName());
        editor.putString(SharedPreferencesKeys.UserDOB, getDOB());
        editor.putString(SharedPreferencesKeys.UserEmail, getEmail());
        editor.putString(SharedPreferencesKeys.UserPhone, getPhone());

        editor.commit();
    }

    private String getName(){
        return etName.getText().toString();
    }
    private String getDOB(){
        return etDob.getText().toString();
    }
    private String getEmail(){
        return etEmail.getText().toString();
    }
    private String getPhone(){
        return etPhone.getText().toString();
    }
}
