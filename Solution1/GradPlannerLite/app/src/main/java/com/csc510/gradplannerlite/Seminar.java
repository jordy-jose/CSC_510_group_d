package com.csc510.gradplannerlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Seminar extends AppCompatActivity {

    private Button btnRemove;
    private Button btnView;
    private static final String TAG = "Seminar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar);
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

        initializeControls();
        enableControls();

        Logger.Log(getApplicationContext(), TAG, "Started...");
    }

    private void enableControls() {
        int semCount = getSeminarCount();
        if(semCount == 0){
            setEnabled(false);
        }else{
            setEnabled(true);
        }
    }

    private void setEnabled(boolean enabled) {
        btnRemove.setEnabled(enabled);
        btnView.setEnabled(enabled);
        setBackgroundColor(enabled);
    }

    private void setBackgroundColor(boolean enabled){
        int color;
        if(enabled){
            color = Color.WHITE;
        }else{
            color = Color.GRAY;
        }
        btnRemove.setTextColor(color);
        btnView.setTextColor(color);
    }

    private void initializeControls() {
        btnRemove = (Button)findViewById(R.id.semRem);
        btnView = (Button)findViewById(R.id.semView);
    }

    private int getSeminarCount(){
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        return settings.getInt(SharedPreferencesKeys.SemNum, 0);
    }

    private int getSeminarAttended(){
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        return settings.getInt(SharedPreferencesKeys.SemAttended, 0);
    }

    public void onClickSemView(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks view button...");
        Intent intent = new Intent(this, ViewSeminar.class);
        startActivity(intent);
    }

    public void onClickSemRem(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks remove button...");
        int count = getSeminarCount();
        int attended = getSeminarAttended();
        if(count == attended){
            setSemAttendedCount(count - 1);
        }
        setSemCount(count - 1);
        //last seminar removed
        if(getSeminarCount() == 0){
            setSemAttendedCount(0);
            setEnabled(false);
        }
        showMessageRem();
        Logger.Log(getApplicationContext(), TAG, "Seminar removed...");
    }

    public void onClickSemAdd(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks add button...");
        int count = getSeminarCount();
        if(count == 0){
            setEnabled(true);
        }
        setSemCount(count + 1);
        showMessageAdd();
        Logger.Log(getApplicationContext(), TAG, "Seminar added...");
    }

    private void setSemCount(int count){
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SharedPreferencesKeys.SemNum, count);
        editor.commit();
    }

    private void setSemAttendedCount(int count){
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SharedPreferencesKeys.SemAttended, count);
        editor.commit();
    }

    private void showMessageAdd(){
        showMsg(R.string.msgSemAdded);
    }

    private void showMsg(int msg) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(msg);
        dlgAlert.setTitle(R.string.app_name);
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void showMessageRem(){
        showMsg(R.string.msgSemRemoved);
    }
}
