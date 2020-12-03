package com.victoriamatos.valuapp;

import android.util.Log;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class ThreadTaskUrl extends Thread {
    private String getUrl;
    private ThreadTaskHandler tth;

    public ThreadTaskUrl(ThreadTaskHandler handler, String newUrl){
        tth = handler;
        getUrl = newUrl;
    }

    public void run(){
        //update View
        Log.w("TTU", "Inside run");

        try {
            Log.w("TTU", "Inside try");
            //create a URL (a class)
            URL url = new URL(getUrl);
            //create an input stream for the URL
            InputStream is = url.openStream();
            //read from that input stream
            Scanner scan = new Scanner(is);
            String s = "";
            while (scan.hasNext())
                s += scan.nextLine();
            Log.w("TTU","the string: " + s);
            //activity.updateView(s);
            tth.setThreadOutput(s);
        } catch (Exception e){
            Log.w("TTU", "exception: " + e.getMessage());
        }
    }

}
