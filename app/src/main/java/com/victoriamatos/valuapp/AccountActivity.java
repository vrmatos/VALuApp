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

public class AccountActivity extends AppCompatActivity {
    private ThreadTaskHandler tth;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_screen);
        tth = new ThreadTaskHandler();
        TextView tv = findViewById(R.id.welcome_message);
        tv.setText("Welcome " + user.firstName + " " + user.lastName + "!");
        updatePoints();
    }

    public void updatePoints(){
        int user_pts = user.points;
        TextView tv = findViewById(R.id.total_points);
        tv.setText("Total Points: " + user_pts);
        TextView tv1 = findViewById(R.id.points_to);
        tv1.setText(user_pts % 25 + "/" + User.PTS_FOR_CARD);
        TextView tv2 = findViewById(R.id.total_gift_cards);
        tv2.setText("Gift Cards: " + user_pts / 25);
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
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    public void deleteAcct(View v){
        Log.w("Acct","Inside deleteAcct");
        showDeleteAcctDialog();
    }

    /**
     * sets up the new game dialog box
     */
    public void showDeleteAcctDialog( ) {
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
     * sets up event handling for the new game dialog box
     */
    public class PlayDialog implements DialogInterface.OnClickListener {
        /**
         * what happens on a click in the new game dialog box
         * @param dialog, the dialog that will be shown
         * @param which, the button clicked
         */
        public void onClick( DialogInterface dialog, int which ) {
            Log.w("AcctAct", "Inside PlayDialog, onClick");
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

    public void logout(View v){
        user = new User();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
