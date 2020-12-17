package com.victoriamatos.valuapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class handles all the posting to php scripts and output from them
 */
public class ThreadTaskHandler {
    /* The URLs for the server-side php scripts */
    public static final String URL_POST_LOGIN = "http://valu.cs.loyola.edu/appLogin.php";
    public static final String URL_POST_REGISTER = "http://valu.cs.loyola.edu/appSignup.php";
    public static final String URL_POST_BROWSE_REQUEST = "http://valu.cs.loyola.edu/browseRequests.php";
    public static final String URL_POST_VIEW_REQUEST = "http://valu.cs.loyola.edu/viewRequest.php";
    public static final String URL_POST_UPDATE_ACCT = "http://valu.cs.loyola.edu/updateAccount.php";
    public static final String URL_POST_UPDATE_ACCT2 = "http://valu.cs.loyola.edu/updateAccount2.php";
    public static final String URL_POST_DELETE = "http://valu.cs.loyola.edu/deleteAccount.php";
    public static final String URL_POST_BOOK_REQUEST = "http://valu.cs.loyola.edu/bookRequest.php";
    public static final String URL_POST_BOOKED_REQUESTS = "http://valu.cs.loyola.edu/bookedRequests.php";
    public static final String URL_POST_SEARCH_REQUESTS = "http://valu.cs.loyola.edu/searchRequests.php";
    public static final String URL_POST_POINT_UPDATE = "http://valu.cs.loyola.edu/pointUpdate.php";
    public static final String URL_POST_POINT_UPDATE2 = "http://valu.cs.loyola.edu/pointUpdate2.php";

    private String[] threadOutput;

    /**
     * Posts String info to a given URL
     * @param url, the url to post info to
     * @param toWrite, the string to send to the URL
     */
    public void postThreadTask(String url, String toWrite){
        Log.w("TTH","Inside postThreadTask");
        ThreadTaskUrlPost task = new ThreadTaskUrlPost(this, url, toWrite);
        task.start();
    }

    /**
     * Sets the output from a thread to the threadOutput
     * @param str, the String to set
     */
    public void setThreadOutput(String str){
        Log.w("TTH","Inside setThreadOutput");
        threadOutput = processOutput(str);
    }

    /**
     * Gets the threadOutput variable
     * @return, threadOutput
     */
    public String[] getThreadOutput(){
        Log.w("TTH","Inside getThreadOutput");
        return threadOutput;
    }

    /**
     * Processes the output of a url into an array
     * @param str, the string from output to process
     * @return, the array to set threadOutput to
     */
    public String[] processOutput(String str){
        Log.w("TTH", "Inside processOutput: " + str);
        String[] split;
        if(str == null)
            return null;
        if(str.contains("~")) {
            split = str.split("~");
        }else if(str.contains("|"))
            split = str.split("\\|");
        else{
            split = new String[1];
            split[0] = str;
        }
        return split;
    }


}
