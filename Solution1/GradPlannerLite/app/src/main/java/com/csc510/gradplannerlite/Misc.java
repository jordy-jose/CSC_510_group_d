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

public class Misc extends AppCompatActivity {

    private static final int mHeight = 75;
    private RadioGroup.LayoutParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);
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

        populateRequirements();
    }

    private void populateRequirements() {
        populatePendingReq();
        populateCheckedReq();
    }

    private void populatePendingReq() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCINFO, 0);
        Map<String, ?> stringMap = settings.getAll();
        for (String key : new TreeSet<String>(stringMap.keySet())) {
            addRadioButton(key, Color.WHITE);
        }
    }

    private void populateCheckedReq() {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCCHK, 0);
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
        return (RadioGroup) findViewById(R.id.MiscRadioGrp);
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

    public void onClickMiscCheckBtn(View view) {
        RadioGroup rGrp = getRadioGrp();
        int radioButtonID = rGrp.getCheckedRadioButtonId();
        if (radioButtonID == -1) return;
        String text = getSelectedText(rGrp, radioButtonID);
        if (isReqPresent(text)) {
            removeRequirement(text);
            addRequirementChk(text);
            getRadioButton(rGrp, radioButtonID).setTextColor(Color.GRAY);
        }
    }

    public void onClickMiscEditBtn(View view) {
        RadioGroup rGrp = getRadioGrp();
        int radioButtonID = rGrp.getCheckedRadioButtonId();
        if (radioButtonID == -1) return;
        String text = getSelectedText(rGrp, radioButtonID);
        if (isChkPresent(text)) return;
        showMsgEdit(text, getRadioButton(rGrp, radioButtonID));
    }

    public void onClickMiscRemBtn(View view) {
        RadioGroup rGrp = getRadioGrp();
        int radioButtonID = rGrp.getCheckedRadioButtonId();
        if (radioButtonID == -1) return;
        String key = getSelectedText(rGrp, radioButtonID);
        if (isReqPresent(key)) {
            removeRequirement(key);
        } else if (isChkPresent(key)) {
            removeRequirementChk(key);
        }
        removeRadioButton(getRadioBtnView(rGrp, radioButtonID));
    }

    public void onClickMiscAddBtn(View view) {
        showMsgAdd();
    }

    private String getSelectedText(RadioGroup rGrp, int radioBtnId) {
        RadioButton rBtn = getRadioButton(rGrp, radioBtnId);
        return rBtn.getText().toString();
    }

    private RadioButton getRadioButton(RadioGroup rGrp, int radioBtnId) {
        View radioButton = getRadioBtnView(rGrp, radioBtnId);
        int idx = rGrp.indexOfChild(radioButton);
        return (RadioButton) rGrp.getChildAt(idx);
    }

    private View getRadioBtnView(RadioGroup rGrp, int radioBtnId) {
        return rGrp.findViewById(radioBtnId);
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

    private void addRequirement(String text) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(text, text);
        editor.commit();
    }

    private void addRequirementChk(String text) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCCHK, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(text, text);
        editor.commit();
    }

    private void removeRequirement(String key) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCINFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    private void removeRequirementChk(String key) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCCHK, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    private boolean isReqPresent(String mText) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCINFO, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return !settings.getString(mText, "").isEmpty();
        }
        return false;
    }

    private boolean isChkPresent(String mText) {
        SharedPreferences settings = getSharedPreferences(SharedPreferencesKeys.PREFS_MISCCHK, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return !settings.getString(mText, "").isEmpty();
        }
        return false;
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
                removeRequirement(editText);
                addRequirement(text);
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

    private void showMsgAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add requirement");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = input.getText().toString();
                if (!isReqPresent(text) && !isChkPresent(text)) {
                    addRequirement(text);
                    addRadioButton(text, Color.WHITE);
                } else {
                    showMsg("Requirement already exists");
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
}
