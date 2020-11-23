package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
    }

    //register account, go to account screen
    public void register(View v){
        Log.w("Register","Inside register");
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    //go login, instead of register
    public void goLogin(View v){
        Log.w("Register", "Inside  goLogin");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
