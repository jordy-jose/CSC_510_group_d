package com.csc510.gradplannerlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Map;
import java.util.TreeSet;

public class Courses extends AppCompatActivity {

    private static final int mHeight = 75;
    private RadioGroup.LayoutParams mParams;
    private static final String TAG = "Courses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
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

        populateCourses();

        Logger.Log(getApplicationContext(), TAG, "Started...");
    }

    private void populateCourses() {
        populateEnrolledCourses();
        populateCheckedCourses();
    }

    private void populateEnrolledCourses() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESENR, 0);
        Map<String, ?> stringMap = settings.getAll();
        for (String key : new TreeSet<String>(stringMap.keySet())) {
            addRadioButton(key, Color.WHITE);
        }
    }

    private void populateCheckedCourses() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESCHK, 0);
        Map<String, ?> stringMap = settings.getAll();
        for (String key : new TreeSet<String>(stringMap.keySet())) {
            addRadioButton(key, Color.GRAY);
        }
    }

    private void addRadioButton(String text, int color) {
        RadioGroup rgp = getRadioGrp();
        rgp.addView(getRadioButton(text, color), getParams());
    }

    private void removeRadioButton(View radioButton) {
        RadioGroup rgp = getRadioGrp();
        rgp.clearCheck();
        rgp.removeView(radioButton);
    }

    private RadioGroup.LayoutParams getParams() {
        if (mParams == null) {
            mParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHeight);
            mParams.setMargins(0, 20, 0, 0);
        }
        return mParams;
    }

    private RadioGroup getRadioGrp() {
        return (RadioGroup) findViewById(R.id.CoursesRadioGrp);
    }

    private RadioButton getRadioButton(String text, int color) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setMinimumHeight(mHeight);
        radioButton.setBackgroundColor(Color.RED);
        radioButton.setText(text);
        radioButton.setTextColor(color);
        radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        return radioButton;
    }

    public void onClickCoursesAddBtn(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks + button...");
        showMsgAdd();
        Logger.Log(getApplicationContext(), TAG, "Course added...");
    }

    public void onClickCoursesRemBtn(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks - button...");
        RadioGroup rGrp = getRadioGrp();
        int radioButtonID = rGrp.getCheckedRadioButtonId();
        if (radioButtonID == -1) return;
        String key = getSelectedText(rGrp, radioButtonID);
        if (isEnrCoursePresent(key)) {
            removeEnrCourse(key);
        } else if (isChkCoursePresent(key)) {
            removeChkCourse(key);
        }
        removeRadioButton(getRadioBtnView(rGrp, radioButtonID));
        Logger.Log(getApplicationContext(), TAG, "Course removed...");
    }

    public void onClickCoursesEditBtn(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks edit button...");
        RadioGroup rGrp = getRadioGrp();
        int radioButtonID = rGrp.getCheckedRadioButtonId();
        if (radioButtonID == -1) return;
        String text = getSelectedText(rGrp, radioButtonID);
        if (isChkCoursePresent(text)) return;
        showMsgEdit(text, getRadioButton(rGrp, radioButtonID));
        Logger.Log(getApplicationContext(), TAG, "Course edited...");
    }

    public void onClickCoursesCheckBtn(View view) {
        Logger.Log(getApplicationContext(), TAG, "User clicks check button...");
        RadioGroup rGrp = getRadioGrp();
        int radioButtonID = rGrp.getCheckedRadioButtonId();
        if (radioButtonID == -1) return;
        String text = getSelectedText(rGrp, radioButtonID);
        if (isEnrCoursePresent(text)) {
            removeEnrCourse(text);
            addChkCourse(text);
            getRadioButton(rGrp, radioButtonID).setTextColor(Color.GRAY);
        }
        Logger.Log(getApplicationContext(), TAG, "Course checked...");
    }

    private void showMsgAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add course");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                if (!isEnrCoursePresent(text) && !isChkCoursePresent(text)) {
                    addEnrCourse(text);
                    addRadioButton(text, Color.WHITE);
                } else {
                    showMsg("Course already added");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private boolean isEnrCoursePresent(String mText) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESENR, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return !settings.getString(mText, "").isEmpty();
        }
        return false;
    }

    private boolean isChkCoursePresent(String mText) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESCHK, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return !settings.getString(mText, "").isEmpty();
        }
        return false;
    }

    private void addEnrCourse(String text) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESENR, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(text, text);
        editor.commit();
    }

    private void showMsg(String msg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
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

    private String getSelectedText(RadioGroup rGrp, int radioBtnId) {
        RadioButton rBtn = getRadioButton(rGrp, radioBtnId);
        return rBtn.getText().toString();
    }

    private void removeEnrCourse(String key) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESENR, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    private void removeChkCourse(String key) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESCHK, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    private View getRadioBtnView(RadioGroup rGrp, int radioBtnId) {
        return rGrp.findViewById(radioBtnId);
    }

    private RadioButton getRadioButton(RadioGroup rGrp, int radioBtnId) {
        View radioButton = getRadioBtnView(rGrp, radioBtnId);
        int idx = rGrp.indexOfChild(radioButton);
        return (RadioButton) rGrp.getChildAt(idx);
    }

    private void showMsgEdit(final String editText, final RadioButton radioButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit requirement");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(editText);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                removeEnrCourse(editText);
                addEnrCourse(text);
                radioButton.setText(text);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void addChkCourse(String text) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_COURSESCHK, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(text, text);
        editor.commit();
    }
}
