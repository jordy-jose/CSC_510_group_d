package com.csc510.gradplannerlite;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EduInfo extends AppCompatActivity {

    private Spinner sDegree;
    private Spinner sMajor;
    private Spinner sSpecialization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_info);
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

        populateDegreeSpinner();
        populateMajorSpinner();
        populateSpecialization();
    }

    private void populateDegreeSpinner() {
        sDegree = (Spinner) findViewById(R.id.spinnerDegree);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.degree_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDegree.setAdapter(adapter);
    }

    private void populateMajorSpinner() {
        sMajor = (Spinner) findViewById(R.id.spinnerMajor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.major_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMajor.setAdapter(adapter);
    }

    private void populateSpecialization() {
        sSpecialization = (Spinner) findViewById(R.id.spinnerSpecialization);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.specialization_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSpecialization.setAdapter(adapter);
    }

    public void onClickEduSandCBtn(View view) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_EDUINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();

        editor.putString(SharedPreferencesKeys.EduDegree, getDegreeVal());
        editor.putString(SharedPreferencesKeys.EduMajor, getMajorVal());
        editor.putString(SharedPreferencesKeys.EduSpec, getSpecVal());

        editor.commit();
    }

    private String getDegreeVal(){ return sDegree.getSelectedItem().toString();}
    private String getMajorVal(){ return sDegree.getSelectedItem().toString();}
    private String getSpecVal(){ return sDegree.getSelectedItem().toString();}
}
