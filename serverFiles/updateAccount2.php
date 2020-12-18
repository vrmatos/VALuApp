<?php
  // Required information to access the database
  $username = "lalexander2";
  $password = "1765882";
  $host = "cs-database.cs.loyola.edu";
  $database = "valu";

  // Establish the connection to the database
  $mysqli = new mysqli($host, $username, $password, $database);
  if (!$mysqli)
  {
    echo("Unsuccessful connection" );
  }

  // Takes in all user information to update every field
  $email = $_POST['email'];
  $password = $_POST['password'];
  $hash = password_hash($password, PASSWORD_DEFAULT);
  $firstName = $_POST['firstName'];
  $lastName = $_POST['lastName'];
  $streetAddress = $_POST['streetAddress'];
  $city = $_POST['city'];
  $state = $_POST['state'];
  $zip = $_POST['zip'];
  $oldEmail = $_POST['oldEmail'];

  // Gets from the PetSitter table where the email matches the given one
  $sql = "select * from PetSitter where email = '$newEmail'";
  $result = $mysqli->query($sql);

  // Gets the results of the query
  $row = $result->fetch_assoc();
  // Gets the number of results
  $rowCount = mysqli_num_rows($result);

  // If there are no results, returns an error
  if ($rowCount != 0)
  {
    echo "email";
  }
  // There are results of the query
  else
  {
    // Google Maps API for retrieving latitude/longitude from a street address
    $urlStart = 'https://maps.googleapis.com/maps/api/geocode/json?address=';

    // Contains a Google Maps API Key (DO NOT USE OUTSIDE OF THIS PROJECT)
    $addressURL = urlencode($streetAddress) . ',' . urlencode($city) . ',' . urlencode($state) . '&key=AIzaSyBEu3sY3KWrKRN-vdkKufbbfD1_cdr8z18';
    $url = $urlStart . $addressURL;

    // Using the URL as a request, returns a json containing lots of information
    $resp_json = file_get_contents($url);
    // Decodes the json
    $resp = json_decode($resp_json, true);

    // If the maps API works correctly, the returned status is "OK"
    if($resp['status']=='OK')
    {
      // Sets the latitude and longitude from the decoded json, if they are defined. Otherwise, sets to "".
      $latitude = isset($resp['results'][0]['geometry']['location']['lat']) ? $resp['results'][0]['geometry']['location']['lat'] : "";
      $longitude = isset($resp['results'][0]['geometry']['location']['lng']) ? $resp['results'][0]['geometry']['location']['lng'] : "";

      // Updates the PetSitter with the new information using the old email to identify the correct PetSitter to update
      $sql2 = "update PetSitter set email = '$email', password = '$hash', firstName = '$firstName', lastName = '$lastName', streetAddress = '$streetAddress', city = '$city', state = '$state', zip = '$zip', latitude = '$latitude', longitude = '$longitude' where email = '$oldEmail'";
      $result = $mysqli->query($sql2);
      // Returns the new latitude and longitude
      echo $latitude, "|", $longitude;
    } 
    // Error checking for if the Google Maps API fails
    else
    {
      echo "noGeocode";
    }
  }
  // Closes the connection to the database
  $mysqli -> close();
?>


