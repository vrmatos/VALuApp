package com.victoriamatos.valuapp;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represent the user's who information is changed/processed/used throughout the app
 */

public class User {
    public static final int PTS_FOR_CARD = 25;

    /* The User's information */
    public String currDate;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String streetAddress;
    public String city;
    public String state;
    public String zip;
    public float latitude;
    public float longitude;
    public int points;

    /**
     * The constructor that initializes User with its email and password
     * @param newEmail, the User's email
     * @param pass, the User's password
     */
    public User(String newEmail, String pass){
        Log.w("User", "Inside User's login constructor");
        email = newEmail;
        password = pass;
        firstName = "User";
        lastName = "";
        streetAddress = "";
        city = "";
        state = "";
        zip = "";
        latitude = 0.0f;
        longitude = 0.0f;
        points = 0;
    }

    /**
     * The default constructor
     */
    public User() {
        Log.w("User", "Inside User's default constrcutor");
        firstName = "User";
        lastName = "";
        email = "";
        password = "";
        streetAddress = "";
        city = "";
        state = "";
        zip = "";
        latitude = 0.0f;
        longitude = 0.0f;
        points = 0;
    }

    /* Setters for the class */
    public void setFirstName(String first){firstName = first;}
    public void setLastName(String last){lastName = last;}
    public void setEmail(String newEmail){email = newEmail;}
    public void setPassword(String pass){password = pass;}
    public void setStreetAddress(String street){streetAddress = street;}
    public void setCity(String newCity){city = newCity;}
    public void setState(String newState){state = newState;}
    public void setZip(String newZip){zip = newZip;}
    public void setLatitude(float lat){latitude = lat;}
    public void setLongitude(float newLong){longitude = newLong;}
    public void setPoints(int pts){points = pts;}
    public void setCurrDate(String s) {currDate = s;}

    /* Getters for the class */
    public String getEncryptedPassword(){return password;}

    /**
     * Turns the User's information into a string for posting to a script
     * @return, said string
     */
    public String toThreadTaskString(){
        Log.w("User", "Inside toThreadTaskString");
        return "email=" + email + "&password=" + password + "&firstName=" + firstName + "&lastName=" + lastName
                + "&streetAddress=" + streetAddress + "&city=" + city + "&state=" + state + "&zip=" + zip;
    }

    /**
     * Compares the current date, stored with User's info, to given date
     * @param date, to compare to current
     * @return, 1 iff current date occurs after given date, 0 else
     */
    public int compareToCurrDate(String date){
        Log.w("User", "Inside compareToCurrDate");
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date curr = null;
        Date owner = null;
        try {
            curr = sdformat.parse(currDate);
            owner = sdformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(curr.compareTo(owner) > 0) {
            //curr occurs after ownerEnd
            return 1;
        } else {
            //curr occurs before ownerEnd
            return 0;
        }
    }

}
