package com.victoriamatos.valuapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This class represents the login screen of the app
 */
public class LoginActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;

    /**
     * Initializes LoginActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Login","Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        tth = new ThreadTaskHandler();
        AccountActivity.user = new User();
    }

    /**
     * Logs the user into
     * @param v, the login button pressed
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
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
            Log.w("Login", "successful login");
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Goes to RegisterActivity to register an account
     * @param v, of button pressed
     */
    public void goRegister(View v){
        Log.w("Login", "Inside  goRegister");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Gets the user's info based on their email and, if correct, their password
     * @param email, user's typed in email
     * @param password, user's typed in password
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getUserInfo(String email, String password){
        Log.w("Login","Inside getUserInfo");
        tth.postThreadTask(ThreadTaskHandler.URL_POST_LOGIN, "email=" + email + "&password=" + password);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] output = tth.getThreadOutput(); //firstName, lastName, latitude, longitude, points
        if(output.length != 5) {
            Log.w("Login", "checking if user's email and password are correct");
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
            Log.w("Login","setting user's info based on login info");
            AccountActivity.user.setFirstName(output[0]);
            AccountActivity.user.setLastName(output[1]);
            AccountActivity.user.setLatitude(Float.parseFloat(output[2]));
            AccountActivity.user.setLongitude(Float.parseFloat(output[3]));
            AccountActivity.user.setCurrDate(output[4]);
            int points = updatePoints(email);
            AccountActivity.user.setPoints(points);
            tth.postThreadTask(ThreadTaskHandler.URL_POST_POINT_UPDATE2, "email=" + email + "&points=" + points);
            return 1;
        }
    }

    /**
     * Updates the user's points based on the completion of booked requests
     * @param email, the user's email
     * @return, the number of points user has
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int updatePoints(String email){
        Log.w("Login", "Inside updatePoints");
        int points = 0;
        tth.postThreadTask(ThreadTaskHandler.URL_POST_POINT_UPDATE, "email=" + email);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] output = tth.getThreadOutput();
        String info[];
        if(output != null && output[0].equals("noPastBookings"))
            return 0;
        else{
            Log.w("Login","Determining points on dates");
            for (int i = 0; i < (output.length - 1); i++) { //year-month-day
                info = output[i].split("\\|");
                String[] startDateSplit = info[0].split("-");
                String[] endDateSplit = info[1].split("-");
                int[] start = new int[startDateSplit.length];
                int[] end = new int[endDateSplit.length];
                for(int j=0; j < startDateSplit.length; j++){
                    start[j] = Integer.parseInt(startDateSplit[j]);
                    end[j] = Integer.parseInt(endDateSplit[j]);
                }
                LocalDate startDate = LocalDate.of(start[0], start[1], start[2]);
                LocalDate endDate = LocalDate.of(end[0],end[1], end[2]);
                long numDaysBtwn = ChronoUnit.DAYS.between(startDate, endDate) + 1;
                points += ((int)numDaysBtwn);
            }
            return points;
        }
    }


}
