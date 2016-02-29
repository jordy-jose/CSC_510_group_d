package com.csc510.gradplannerlite;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class ViewSeminar extends AppCompatActivity {

    private LinearLayout mParent;
    private static final int mHeight = 75;
    private static final String TAG = "ViewSeminar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_seminar);
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

        setParentLayout();
        createSeminars();

        Logger.Log(getApplicationContext(), TAG, "Started...");
    }

    private void createSeminars() {
        int count = getSeminarCount();
        int attended = getSeminarAttended();

        for (int i = 0; i < count; i++) {
            LinearLayout layout = createLinearLayout();
            LinearLayout.LayoutParams params = getLayoutParams();
            CheckBox chkBox;
            int num = i + 1;
            if (attended > 0) {
                attended--;
                chkBox = getCheckBox("\tSEMINAR " + num, true);
            } else {
                chkBox = getCheckBox("\tSEMINAR " + num, false);
            }

            addCheckBoxToLayout(layout, chkBox);
            addLayoutToParent(layout, params);
        }
    }

    private void addCheckBoxToLayout(LinearLayout layout, CheckBox chkBox) {
        layout.addView(chkBox);
    }

    private void addLayoutToParent(LinearLayout layout, LinearLayout.LayoutParams params) {
        mParent.addView(layout, params);
    }

    @NonNull
    private CheckBox getCheckBox(String seminarNo, boolean checked) {
        CheckBox chkBox = new CheckBox(this);
        chkBox.setChecked(checked);
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                  int attended = getSeminarAttended();
                                                  if (isChecked) {
                                                      setSemAttendedCount(attended + 1);
                                                  } else {
                                                      setSemAttendedCount(attended - 1);
                                                  }
                                              }
                                          }
        );
        chkBox.setBackgroundColor(Color.RED);
        chkBox.setText(seminarNo);
        chkBox.setTextColor(Color.WHITE);
        chkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        return chkBox;
    }

    @NonNull
    private LinearLayout.LayoutParams getLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                mHeight);
        params.setMargins(0, 20, 0, 0);
        return params;
    }

    @NonNull
    private LinearLayout createLinearLayout() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundColor(Color.RED);
        layout.setMinimumHeight(mHeight);
        return layout;
    }

    private void setParentLayout() {
        mParent = (LinearLayout) findViewById(R.id.viewSeminarLL);
    }

    private int getSeminarCount() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        return settings.getInt(SharedPreferencesKeys.SemNum, 0);
    }

    private int getSeminarAttended() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        return settings.getInt(SharedPreferencesKeys.SemAttended, 0);
    }

    private void setSemAttendedCount(int count){
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_SEMINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(SharedPreferencesKeys.SemAttended, count);
        editor.commit();
    }
}
