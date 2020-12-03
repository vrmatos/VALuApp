package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        tth = new ThreadTaskHandler();
    }

    //register account, go to account screen
    public void register(View v){
        Log.w("Register","Inside register");

        //verify password information
        EditText ed4 = findViewById(R.id.sitter_password);
        EditText ed5 = findViewById(R.id.verify_sitter_password);

        if(ed4.getText().toString().equals(ed5.getText().toString())){
            //get information
            LoginActivity.user.setPassword(ed4.getText().toString());
            EditText ed1 = findViewById(R.id.sitter_first_name);
            LoginActivity.user.setFirstName(ed1.getText().toString());
            EditText ed2 = findViewById(R.id.sitter_last_name);
            LoginActivity.user.setLastName(ed2.getText().toString());
            EditText ed3 = findViewById(R.id.sitter_email);
            LoginActivity.user.setEmail(ed3.getText().toString());
            EditText ed6 = findViewById(R.id.sitter_address_line1);
            EditText ed7 = findViewById(R.id.sitter_address_line2);
            LoginActivity.user.setStreetAddress(ed6.getText().toString() + " " + ed7.getText().toString());
            EditText ed8 = findViewById(R.id.sitter_city);
            LoginActivity.user.setCity(ed8.getText().toString());
            EditText ed9 = findViewById(R.id.sitter_state);
            LoginActivity.user.setState(ed9.getText().toString());
            EditText ed10 = findViewById(R.id.sitter_zip_code);
            LoginActivity.user.setZip(ed10.getText().toString());

            Log.w("RA",LoginActivity.user.toThreadTaskString());

            //push to database
            tth.postThreadTask(ThreadTaskHandler.URL_POST_REGISTER, LoginActivity.user.toThreadTaskString());
            String[] serverOutput = tth.getThreadOutput();
            if(serverOutput != null)
                Log.w("RA", serverOutput[0]);

            //check if email exists elsewhere
            if(serverOutput != null && serverOutput[0].equals("")){
                Toast toast = Toast.makeText(getApplicationContext(),"Account already exists!", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                //go to account screen
                Intent intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Verify Password incorrect!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //go login, instead of register
    public void goLogin(View v){
        Log.w("Register", "Inside  goLogin");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
