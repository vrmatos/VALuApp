package com.victoriamatos.valuapp;

public class User {
    public static final int PTS_FOR_CARD = 25;
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

    //for LoginActivity
    public User(String newEmail, String pass){
        email = newEmail;
        encrypt(pass);
    }

    public User(String first, String last, String newEmail, String pass, String street, String newCity, String newState, String newZip){
        firstName = first;
        lastName = last;
        email = newEmail;
        encrypt(pass);
        streetAddress = street;
        city = newCity;
        state = newState;
        zip = newZip;
        convertZipToCoords();
    }

    //default
    public User() {
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

    //setters
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

    //getters
    public String getEncryptedPassword(){return password;}

    public void encrypt(String pass){
        //encrypt password
        password = pass;
    }

    public void convertZipToCoords(){
        //latitude =
        //longitude =
    }

    public void postUserToDatabase(ThreadTaskHandler tth, String url){
        tth.postThreadTask(url,"str");
    }

    public String toThreadTaskString(){
        convertZipToCoords();
        return "email=" + email + "&password=" + password + "&firstName=" + firstName + "&lastName=" + lastName
                + "&streetAddress=" + streetAddress + "&city=" + city + "&state=" + state + "&zip=" + zip + "&latitude=" + latitude + "&longitude=" + longitude;
    }

}
