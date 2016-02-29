package com.csc510.gradplannerlite;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.Map;
import java.util.TreeSet;

public class Status extends AppCompatActivity {

    private EditText mCourses;
    private EditText mSeminars;
    private EditText mOthers;
    private static final String TAG = "Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
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
        populateControls();

        Logger.Log(getApplicationContext(), TAG, "Started...");
    }

    private void populateControls() {
        populateCoursesPending();
        populateSeminarsPending();
        populateOther();
    }

    private void populateOther() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCINFO, 0);
        int size = settings.getAll().size();
        mOthers.setText(Integer.toString(size));
    }

    private void populateSeminarsPending() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        int total = settings.getInt(SharedPreferencesKeys.SemNum, 0);
        int attended = settings.getInt(SharedPreferencesKeys.SemAttended, 0);
        mSeminars.setText(Integer.toString(total - attended));
    }

    private void populateCoursesPending() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESENR, 0);
        int size = settings.getAll().size();
        mCourses.setText(Integer.toString(size));
    }

    private void initializeControls() {
        mCourses = (EditText)findViewById(R.id.txtCoursesPend);
        mCourses.setEnabled(false);
        mSeminars = (EditText)findViewById(R.id.txtSemPend);
        mSeminars.setEnabled(false);
        mOthers = (EditText)findViewById(R.id.txtOtherPend);
        mOthers.setEnabled(false);
    }

}
