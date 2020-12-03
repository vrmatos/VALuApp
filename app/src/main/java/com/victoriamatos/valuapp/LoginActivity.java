package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    public static User user;
    private ThreadTaskHandler tth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        tth = new ThreadTaskHandler();
        user = new User();
    }

    //login, go to the account screen
    public void login(View v){
        Log.w("Login","Inside login");

        //get email/password
        EditText ed1 = findViewById(R.id.email);
        String email = ed1.getText().toString();
        EditText ed2 = findViewById(R.id.password);
        String password = ed2.getText().toString();
        //user = new User(email,password);
        //getUserInfo(email, user.getEncryptedPassword()); //uncomment when php done

        //go to account screen
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    //go register, instead of login
    public void goRegister(View v){
        Log.w("Login", "Inside  goRegister");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public static User getUser(){ return user;}

    public void getUserInfo(String email, String password){
        tth.postThreadTask(ThreadTaskHandler.URL_POST_LOGIN, "email=" + email + "&password=" + password);
        String[] output = tth.getThreadOutput(); //firstName, lastName, latitude, longitude, points
        user.setFirstName(output[0]);
        user.setLastName(output[1]);
        user.setLatitude(Float.parseFloat(output[2]));
        user.setLongitude(Float.parseFloat(output[3]));
        user.setPoints(Integer.parseInt(output[4]));
    }

}
