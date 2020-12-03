package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_screen);
        TextView tv = findViewById(R.id.welcome_message);
        tv.setText("Welcome Back, " + LoginActivity.user.firstName + "!");
    }

    // have a back to account button or swipe back w/gesture; need a label
    public void viewBookedRequests(View v){
        Log.w("Acct","Inside viewBookedRequests");
        Intent intent = new Intent(this, VisualizeBookedActivity.class);
        startActivity(intent);
    }

    // have browse screen either goback button or swipe back w/ gesture
    public void browseRequests(View v){
        Log.w("Acct","Inside browseRequests");
        Intent intent = new Intent(this, BrowseActivity.class);
        startActivity(intent);
    }

    public void updateAcct(View v){
        Log.w("Acct","Inside updateAcct");
        /*
        start new activity? or manage in activity?
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);*/
    }

    public void deleteAcct(View v){
        Log.w("Acct","Inside deleteAcct");
    }

}
