package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class VisualizeBookedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualize_requests_screen);
    }

    public void viewRequest(View v){
        Log.w("VBA","Inside viewRequest");
        Intent intent = new Intent(this, IndivBookedRequestActivity.class);
        startActivity(intent);
    }

}
