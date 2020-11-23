package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IndivRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_request_screen);
    }

    public void bookRequest(View v){
        Log.w("IRA","Inside bookRequest");
        // do the booking, update server
        finish();

    }

}
