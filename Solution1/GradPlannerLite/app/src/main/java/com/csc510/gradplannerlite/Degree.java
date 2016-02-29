package com.csc510.gradplannerlite;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Degree extends AppCompatActivity {

    private Spinner sDegree;
    private EditText etMajor;
    private EditText etSpec;
    private Button btnSubmit;
    private static final String TAG = "Degree";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degree_info);
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
        populateDegreeSpinner();
        PopulateEditTexts();
        enableRequiredControls();

        Logger.Log(getApplicationContext(), TAG, "Started...");
    }

    private void PopulateEditTexts() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_EDUINFO, 0);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.degree_array, android.R.layout.simple_spinner_item);
        sDegree.setSelection(adapter.getPosition(settings.getString(SharedPreferencesKeys.EduDegree, "")));
        etMajor.setText(settings.getString(SharedPreferencesKeys.EduMajor, ""));
        etSpec.setText(settings.getString(SharedPreferencesKeys.EduSpec, ""));
    }

    private void InitializeControls() {
        etMajor = (EditText)findViewById(R.id.txtMajor);
        etSpec = (EditText)findViewById(R.id.txtSpec);
        btnSubmit = (Button)findViewById(R.id.btnDegSubmit);
    }

    private void populateDegreeSpinner() {
        sDegree = (Spinner) findViewById(R.id.spinnerDegree);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.degree_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDegree.setAdapter(adapter);
    }

    public void onClickDegSubmitBtn(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks submit button...");
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_EDUINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();

        editor.putString(SharedPreferencesKeys.EduDegree, getDegreeVal());
        editor.putString(SharedPreferencesKeys.EduMajor, getMajor());
        editor.putString(SharedPreferencesKeys.EduSpec, getSpec());

        editor.commit();
        setEnabledForControls(false);
        Logger.Log(getApplicationContext(), TAG, "User submits degree details...");
    }

    private String getDegreeVal(){ return sDegree.getSelectedItem().toString();}
    private String getMajor(){
        return etMajor.getText().toString();
    }
    private String getSpec(){
        return etSpec.getText().toString();
    }

    public void onClickEditBtn(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks edit button...");
        setEnabledForControls(true);
    }

    private void enableRequiredControls() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if(!getDegreeVal().isEmpty() ||
                    !getMajor().isEmpty() ||
                    !getSpec().isEmpty()){
                setEnabledForControls(false);
            }
        }
    }

    private void setEnabledForControls(boolean enabled){
        sDegree.setEnabled(enabled);
        etMajor.setEnabled(enabled);
        etSpec.setEnabled(enabled);
        btnSubmit.setEnabled(enabled);
        setBackgroundColor(enabled);
    }

    private void setBackgroundColor(boolean enabled){
        int color;
        if(enabled){
            color = Color.WHITE;
        }else{
            color = Color.GRAY;
        }
        etMajor.setTextColor(color);
        etSpec.setTextColor(color);
        btnSubmit.setTextColor(color);
    }
}
