package com.victoriamatos.valuapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IndivBookedRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_booked_request_screen);
    }

    //eventually a rate method
    public void rate(View v){
        //do the rating - go to a rate screen?
        finish();
    }
}
