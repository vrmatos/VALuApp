package com.victoriamatos.valuapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String URL_POST = "http://valu.cs.loyola.edu/insertSitter.php";
    public static final String URL = "http://valu.cs.loyola.edu/selectPet.php";

    //new line btwn owners, | btwn entries
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ThreadTaskUrl task = new ThreadTaskUrl(this);
       // ThreadTaskUrlPost task = new ThreadTaskUrlPost(this);
        //task.start();

    }

    public void updateView(String s) {
        Log.w("MA", "Inside update view, s= "+s);
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(s);
    }
}