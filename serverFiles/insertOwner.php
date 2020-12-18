<?php
  // Star Session
  session_start();
  $username = "lalexander2";
  $password = "1765882";
  $host = "cs-database.cs.loyola.edu";
  $database = "valu";
// Establish Connection
  $mysqli = new mysqli($host, $username, $password, $database);
  if (!$mysqli)
  {
    echo("Unsuccessful connection" );
  }
  else
  {
    echo("Successfully connected<br>");
  }
  
// Form retrieval 
  $email = $_POST["email"];

  $password = $_POST["password"];
  $firstName = $_POST["firstName"];
  $hash = password_hash($password, PASSWORD_DEFAULT);
  $lastName = $_POST["lastName"];
  $streetAddress = $_POST["streetAddress"];
  $city = $_POST["city"];
  $state = $_POST["state"];
  $zip = $_POST["zip"];
  $customerID = 1;

 

// SQL Query
  $sql = "select * from PetOwner where email = '$email'";
  
  $resultselect = $mysqli->query($sql);
  $num = mysqli_num_rows($resultselect);
  echo($sqlselect);
  echo($num);

  if($num == 1)
  {
    echo("Email is already in use");
    echo('<br>');
    echo '<a href="Signup.html">Go Back</a>';

  }
  else
  {
    // GeoCoding
    $urlStart = 'https://maps.googleapis.com/maps/api/geocode/json?address=';

    $addressURL = urlencode($streetAddress) . ',' . urlencode($city) . ',' . urlencode($state) . '&key=AIzaSyBEu3sY3KWrKRN-vdkKufbbfD1_cdr8z18';
    $url = $urlStart . $addressURL;

    $resp_json = file_get_contents($url);
    $resp = json_decode($resp_json, true);

    if($resp['status']=='OK')
    {
      $latitude = isset($resp['results'][0]['geometry']['location']['lat']) ? $resp['results'][0]['geometry']['location']['lat'] : "";
      $longitude = isset($resp['results'][0]['geometry']['location']['lng']) ? $resp['results'][0]['geometry']['location']['lng'] : "";

      $sqlinsert = "insert into PetOwner values('$email', '$hash', '$firstName', '$lastName', '$streetAddress', '$city', '$state', '$zip', '$customerID', '$latitude', '$longitude')";
      $resultinsert = $mysqli->query($sqlinsert);
      header('location:Login.html');
      $_SESSION['firstname'] = $firstName;
    }
    else
    {
      // Error Checking for Geocoding
      echo "Could not locate.";
      echo ('<br>');
      echo '<a href="Signup.html">Go Back</a>';
    }
  }

  



  
$mysqli -> close();
?>
