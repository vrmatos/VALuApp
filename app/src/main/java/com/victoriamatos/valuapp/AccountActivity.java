package com.victoriamatos.valuapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the home screen of the app
 */
public class AccountActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    public static User user; //User = the model used throughout the activities

    /**
     * Initializes AccountActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Acct", "Inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_screen);
        tth = new ThreadTaskHandler();
        TextView tv = findViewById(R.id.welcome_message);
        tv.setText("Welcome " + user.firstName + " " + user.lastName + "!");
        updatePoints();
    }

    /**
     * Updates the user's points from user
     */
    public void updatePoints(){
        Log.w("Acct", "Inside updatePoints");
        int user_pts = user.points;
        TextView tv = findViewById(R.id.total_points);
        tv.setText("Total Points: " + user_pts);
        TextView tv1 = findViewById(R.id.points_to);
        tv1.setText(user_pts % 25 + "/" + User.PTS_FOR_CARD);
        TextView tv2 = findViewById(R.id.total_gift_cards);
        tv2.setText("Gift Cards: " + user_pts / 25);
    }

    /**
     * Goes to VisualizeBookedActivity, onClick
     * @param v, of button pressed
     */
    public void viewBookedRequests(View v){
        Log.w("Acct","Inside viewBookedRequests");
        Intent intent = new Intent(this, VisualizeBookedActivity.class);
        startActivity(intent);
    }

    /**
     * Goes to BrowseActivity, onClick
     * @param v, of button pressed
     */
    public void browseRequests(View v){
        Log.w("Acct","Inside browseRequests");
        Intent intent = new Intent(this, BrowseActivity.class);
        startActivity(intent);
    }

    /**
     * Goes to UpdateActivity, onClick
     * @param v, of button pressed
     */
    public void updateAcct(View v){
        Log.w("Acct","Inside updateAcct");
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    /**
     * Deletes the user's account, onClick
     * @param v, of button pressed
     */
    public void deleteAcct(View v){
        Log.w("Acct","Inside deleteAcct");
        showDeleteAcctDialog();
    }

    /**
     * Sets up the delete account dialog box
     */
    public void showDeleteAcctDialog( ) {
        Log.w("Acct", "Inside showDeleteAcctDialog");
        // build an alert box (and show it)
        AlertDialog.Builder alert = new AlertDialog.Builder( this );
        alert.setTitle("Delete Account: " + user.email);
        alert.setMessage( "Are you sure?" );

        PlayDialog playAgain = new PlayDialog();

        // set up event handling
        alert.setPositiveButton( "YES", playAgain );
        alert.setNegativeButton( "NO", playAgain );

        alert.show();
    }

    /**
     * Sets up event handling for the delete account dialog box
     */
    public class PlayDialog implements DialogInterface.OnClickListener {
        /**
         * what happens on a click in the delte game dialog box
         * @param dialog, the dialog that will be shown
         * @param which, the button clicked
         */
        public void onClick( DialogInterface dialog, int which ) {
            Log.w("Acct", "Inside PlayDialog, onClick");
            if( which == Dialog.BUTTON_POSITIVE ) { // positive button clicked
                //do account deleting
                tth.postThreadTask(ThreadTaskHandler.URL_POST_DELETE, "email=" + user.email);
                user = new User();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
            else if( which == Dialog.BUTTON_NEGATIVE ) { // negative button clicked
                dialog.dismiss();
            }
        }
    }

    /**
     * Logs the user out of their account
     * @param v, of button pressed
     */
    public void logout(View v){
        Log.w("Acct", "Inside logout");
        user = new User();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
