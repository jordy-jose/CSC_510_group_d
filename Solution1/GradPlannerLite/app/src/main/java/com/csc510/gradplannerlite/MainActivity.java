package com.csc510.gradplannerlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Logger.Log(TAG, "Started...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickMyAcc(View view) {
        Logger.Log(TAG, "User clicks my acc button...");
        Intent intent = new Intent(this, MyAcc.class);
        startActivity(intent);
    }

    public void onClickDegree(View view) {
        Logger.Log(TAG, "User clicks degree button...");
        Intent intent = new Intent(this, Degree.class);
        startActivity(intent);
    }

    public void onClickSeminar(View view) {
        Logger.Log(TAG, "User clicks seminar button...");
        Intent intent = new Intent(this, Seminar.class);
        startActivity(intent);
    }

    public void onClickMisc(View view) {
        Logger.Log(TAG, "User clicks miscellaneous button...");
        Intent intent = new Intent(this, Misc.class);
        startActivity(intent);
    }

    public void onClickCourses(View view) {
        Logger.Log(TAG, "User clicks courses button...");
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
    }

    public void onClickStatus(View view) {
        Logger.Log(TAG, "User clicks status button...");
        Intent intent = new Intent(this, Status.class);
        startActivity(intent);
    }
}
