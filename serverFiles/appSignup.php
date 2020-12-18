<?php
  // Required information to connect to the database
  $username = "lalexander2";
  $password = "1765882";
  $host = "cs-database.cs.loyola.edu";
  $database = "valu";

  // Establish the connection to defined database
  $mysqli = new mysqli($host, $username, $password, $database);
  if (!$mysqli)
  {
    echo("Unsuccessful connection" );
  }

  // Take in an email as input
  $email = $_POST['email'];

  // Gets from the PetSitter table where the email matches the one given
  $sqlCheck = "select * from PetSitter where email = '$email'";
  $check = $mysqli->query($sqlCheck);
  // Checks the number results
  $rows = mysqli_num_rows($check);
  // If there are no matches to the given email, the email is not yet in use
  if ($rows == 0)
  {
    // Takes in required sign-up information as input
    $password = $_POST['password'];
    // Encrypts the password
    $hash = password_hash($password, PASSWORD_DEFAULT);
    $firstName = $_POST['firstName'];
    $lastName = $_POST['lastName'];
    $streetAddress = $_POST['streetAddress'];
    $city = $_POST['city'];
    $state = $_POST['state'];
    $zip = $_POST['zip'];

    // Google Maps API for retrieving latitude/longitude from a street address
    $urlStart = 'https://maps.googleapis.com/maps/api/geocode/json?address=';
    // Contains a Google Maps API Key (DO NOT USE OUTSIDE OF THIS PROJECT)
    $addressURL = urlencode($streetAddress) . ',' . urlencode($city) . ',' . urlencode($state) . '&key=AIzaSyBEu3sY3KWrKRN-vdkKufbbfD1_cdr8z18';
    $url = $urlStart . $addressURL;
    // Using the URL as a request, gets a .json containing lots of information
    $resp_json = file_get_contents($url);
    // Decodes the json
    $resp = json_decode($resp_json, true);

    // If the maps API works correctly, status is "OK".
    if($resp['status']=='OK')
    {
      // Sets latitude and longitude from the decoded json, if they are defined. Otherwise, set to "".
      $latitude = isset($resp['results'][0]['geometry']['location']['lat']) ? $resp['results'][0]['geometry']['location']['lat'] : "";
      $longitude = isset($resp['results'][0]['geometry']['location']['lng']) ? $resp['results'][0]['geometry']['location']['lng'] : "";
      // Adds to the PetSitter table using the given inputs and lat/long values
      $sql = "insert into PetSitter values('$email', '$hash', '$firstName', '$lastName', '$streetAddress', '$city', '$state', '$zip', '$latitude', '$longitude', '0')";
      $result = $mysqli->query($sql);
      // Returns the latitude/longitude of the new user
      echo $latitude, "|", $longitude;
    }
    // Error checking for if the Google Maps API fails
    else
    {
      echo "noGeocode";
    }
  }
  // Error checking for if the provided email is already in use.
  else
  {
    echo "email";
  }
  // Closes the connection to the database
  $mysqli -> close();
?>

