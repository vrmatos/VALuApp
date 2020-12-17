package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the user registering their account
 */
public class RegisterActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;

    /**
     * Initializes RegisterActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Register","Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        tth = new ThreadTaskHandler();
    }

    /**
     * Register the user's account based on the provided info, onClick
     * @param v, the button pressed
     */
    public void register(View v){
        Log.w("Register","Inside register");

        //verify password information
        EditText ed4 = findViewById(R.id.sitter_password);
        EditText ed5 = findViewById(R.id.verify_sitter_password);

        if(ed4.getText().toString().equals(ed5.getText().toString())){
            Log.w("Register","registering the account");
            AccountActivity.user.setPassword(ed4.getText().toString());
            EditText ed1 = findViewById(R.id.sitter_first_name);
            AccountActivity.user.setFirstName(ed1.getText().toString());
            EditText ed2 = findViewById(R.id.sitter_last_name);
            AccountActivity.user.setLastName(ed2.getText().toString());
            EditText ed3 = findViewById(R.id.sitter_email);
            AccountActivity.user.setEmail(ed3.getText().toString());
            EditText ed6 = findViewById(R.id.sitter_address_line1);
            EditText ed7 = findViewById(R.id.sitter_address_line2);
            if(ed7.getText().toString().equals(""))
                AccountActivity.user.setStreetAddress(ed6.getText().toString());
            else
                AccountActivity.user.setStreetAddress(ed6.getText().toString() + "," + ed7.getText().toString());
            EditText ed8 = findViewById(R.id.sitter_city);
            AccountActivity.user.setCity(ed8.getText().toString());
            EditText ed9 = findViewById(R.id.sitter_state);
            AccountActivity.user.setState(ed9.getText().toString());
            EditText ed10 = findViewById(R.id.sitter_zip_code);
            AccountActivity.user.setZip(ed10.getText().toString());

            Log.w("Register", AccountActivity.user.toThreadTaskString());

            //push to database
            tth.postThreadTask(ThreadTaskHandler.URL_POST_REGISTER, AccountActivity.user.toThreadTaskString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] serverOutput = tth.getThreadOutput();

            //check if email exists elsewhere
            if(serverOutput != null && serverOutput[0].equals("email")){
                Log.w("Register","make toast for email in use");
                Toast toast = Toast.makeText(getApplicationContext(),"Email already in use. Login!", Toast.LENGTH_SHORT);
                toast.show();
            }else if (serverOutput[0].equals("noGeocode")){
                Log.w("Register", "make toast for bad address");
                Toast toast = Toast.makeText(getApplicationContext(), "Bad address!", Toast.LENGTH_SHORT);
                toast.show();
            } else{
                //go to account screen
                AccountActivity.user.latitude = Float.parseFloat(serverOutput[0]);
                AccountActivity.user.longitude = Float.parseFloat(serverOutput[1]);
                Intent intent = new Intent(this, AccountActivity.class);
                startActivity(intent);
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Verify Password must equal Password!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Goes to the LoginActivity
     * @param v, of the button pressed
     */
    public void goLogin(View v){
        Log.w("Register", "Inside  goLogin");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
