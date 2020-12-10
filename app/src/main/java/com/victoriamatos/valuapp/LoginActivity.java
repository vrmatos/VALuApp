package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        tth = new ThreadTaskHandler();
        AccountActivity.user = new User();
    }

    //login, go to the account screen
    public void login(View v){
        Log.w("Login","Inside login");

        //get email/password
        EditText ed1 = findViewById(R.id.email);
        String email = ed1.getText().toString();
        EditText ed2 = findViewById(R.id.password);
        String password = ed2.getText().toString();
        AccountActivity.user = new User(email,password);
        int works = getUserInfo(email, AccountActivity.user.getEncryptedPassword());
        if(works == 1){
            //go to account screen
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }
    }

    //go register, instead of login
    public void goRegister(View v){
        Log.w("Login", "Inside  goRegister");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public int getUserInfo(String email, String password){
        tth.postThreadTask(ThreadTaskHandler.URL_POST_LOGIN, "email=" + email + "&password=" + password);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] output = tth.getThreadOutput(); //firstName, lastName, latitude, longitude, points
        if(output.length != 6) {
            Toast toast;
            if(output[0].equals("email"))
                toast = Toast.makeText(getApplicationContext(), "Email doesn't have account. Sign up!", Toast.LENGTH_SHORT);
            else if(output[0].equals("password"))
                toast = Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT);
            else
                toast = Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_SHORT);
            toast.show();
            return 0;
        }
        else{
            AccountActivity.user.setFirstName(output[0]);
            AccountActivity.user.setLastName(output[1]);
            AccountActivity.user.setLatitude(Float.parseFloat(output[2]));
            AccountActivity.user.setLongitude(Float.parseFloat(output[3]));
            AccountActivity.user.setPoints(Integer.parseInt(output[4]));
            AccountActivity.user.setCurrDate(output[5]);
            return 1;
        }
    }

}
