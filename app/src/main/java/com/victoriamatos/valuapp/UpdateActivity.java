package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

/**
 * This class updates the user's account
 */
public class UpdateActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    private String originalEmail;

    /**
     * Initializes UpdateActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("UA", "Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_account);

        //starts with getting the account information
        tth = new ThreadTaskHandler();
        originalEmail = AccountActivity.user.email;
        tth.postThreadTask(ThreadTaskHandler.URL_POST_UPDATE_ACCT, "oldEmail=" + AccountActivity.user.email);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] output = tth.getThreadOutput();
        updateViewInitial(output);

    }

    /**
     * Updates the update account view to have the current account information
     * @param info, the user's info to update the view with
     */
    public void updateViewInitial(String[] info){
        Log.w("UA", "Inside updateViewInitial");
        EditText ed1 = findViewById(R.id.update_first_name);
        ed1.setText(AccountActivity.user.firstName);
        EditText ed2 = findViewById(R.id.update_last_name);
        ed2.setText(AccountActivity.user.lastName);
        EditText ed3 = findViewById(R.id.update_email);
        ed3.setText(AccountActivity.user.email);
        String[] address = info[0].split(",");
        EditText ed6 = findViewById(R.id.update_address_line1);
        ed6.setText(address[0]);
        if(address.length == 2) {
            EditText ed7 = findViewById(R.id.update_address_line2);
            ed7.setText(address[1]);
        }

        EditText ed8 = findViewById(R.id.update_city);
        ed8.setText(info[1]);
        EditText ed9 = findViewById(R.id.update_state);
        ed9.setText(info[2]);
        EditText ed10 = findViewById(R.id.update_zip_code);
        ed10.setText(info[3]);
    }

    /**
     * Updates the user's account information with whatever is one the screen
     * @param v, of the button pressed
     */
    public void updateMe(View v){
        Log.w("UA", "Inside updateMe");
        //verify password information
        EditText ed4 = findViewById(R.id.update_password);
        EditText ed5 = findViewById(R.id.update_verify_password);

        if(ed4.getText().toString().equals(ed5.getText().toString())) {
            Log.w("UA", "passwords match");
            if (!ed4.getText().toString().equals(""))
                AccountActivity.user.setPassword(ed4.getText().toString());

            EditText ed1 = findViewById(R.id.update_first_name);
            AccountActivity.user.setFirstName(ed1.getText().toString());
            EditText ed2 = findViewById(R.id.update_last_name);
            AccountActivity.user.setLastName(ed2.getText().toString());
            EditText ed3 = findViewById(R.id.update_email);
            AccountActivity.user.setEmail(ed3.getText().toString());
            EditText ed6 = findViewById(R.id.update_address_line1);
            EditText ed7 = findViewById(R.id.update_address_line2);
            if(ed7.getText().toString().equals(""))
                AccountActivity.user.setStreetAddress(ed6.getText().toString());
            else
                AccountActivity.user.setStreetAddress(ed6.getText().toString() + "," + ed7.getText().toString());
            EditText ed8 = findViewById(R.id.update_city);
            AccountActivity.user.setCity(ed8.getText().toString());
            EditText ed9 = findViewById(R.id.update_state);
            AccountActivity.user.setState(ed9.getText().toString());
            EditText ed10 = findViewById(R.id.update_zip_code);
            AccountActivity.user.setZip(ed10.getText().toString());
            tth.postThreadTask(ThreadTaskHandler.URL_POST_UPDATE_ACCT2, AccountActivity.user.toThreadTaskString() + "&oldEmail=" + originalEmail);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] serverOutput = tth.getThreadOutput();
            //check if email exists elsewhere
            if (serverOutput != null && serverOutput[0].equals("email")) {
                Log.w("UA", "make toast email in use");
                Toast toast = Toast.makeText(getApplicationContext(), "Email already in use!", Toast.LENGTH_SHORT);
                toast.show();
            } else if (serverOutput[0].equals("noGeocode")){
                Log.w("UA", "make toast bad address");
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
            Log.w("UA", "passwords don't match");
            Toast toast = Toast.makeText(getApplicationContext(),"Verify Password must equal Password!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
