package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    //login, go to the account screen
    public void login(View v){
        Log.w("Login","Inside login");
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);

    }

    //go register, instead of login
    public void goRegister(View v){
        Log.w("Login", "Inside  goRegister");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
