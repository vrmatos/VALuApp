<?php
  // Required information to access the database
  $username = "lalexander2";
  $password = "1765882";
  $host = "cs-database.cs.loyola.edu";
  $database = "valu";

  // Establishes the connection to the defined database
  $mysqli = new mysqli($host, $username, $password, $database);
  if (!$mysqli)
  {
    echo("Unsuccessful connection" );
  }

  // Takes in id, latitude, and longitude as input
  $reqID = $_POST['id'];
  $latitude1 = $_POST['latitude'];
  $longitude1 = $_POST['longitude'];

  // Gets from the Booking table where the id matches the given one
  $sql = "select * from Booking where id = '$reqID'"; 
  $result = $mysqli->query($sql);
  // Gets the number of results
  $rowCount = mysqli_num_rows($result);

  // There are more than 0 results
  if ($rowCount != 0) {
    // Gets the associated information to the Booking
    $row = $result->fetch_assoc();

    // Gets the information associated with the PetOwner and Pet for that Booking
    $ownerEmail = $row['ownerEmail'];

    $sql2 = "select * from Pet where ownerEmail = '$ownerEmail'";
    $result2 = $mysqli->query($sql2);
    $row2 = $result2->fetch_assoc();

    $sql3 = "select * from PetOwner where email = '$ownerEmail'";
    $result3 = $mysqli->query($sql3);
    $row3 = $result3->fetch_assoc();

    // Default distance is 0
    $distance = 0;
    // If the PetOwner and PetSitter's latitude and longitude are the same, distance is 0
    if (($latitude1 == $row3['latitude']) && ($longitude1 == $row3['longitude'])) {
      $distance =  0;
    }
    // Calculates the distance from Petowner to PetSitter
    else {
      $theta = $longitude1 - $row3['longitude'];
      $dist = (sin(deg2rad($latitude1)) * sin(deg2rad($row3['latitude']))) + (cos(deg2rad($latitude1)) * cos(deg2rad($row3['latitude'])) * cos(deg2rad($theta)));
      $dist = acos($dist);
      $dist = rad2deg($dist);
      $dist = $dist * 60 * 1.1515;
      // Rounds up to the nearest integer
      $distance = ceil($dist);
    }
    // Returns relevant information to this one request from the given ID
    echo $ownerEmail, "|", $row['startDate'], "|", $row['endDate'], "|", $row3['firstName'], "|", $row3['lastName'], "|", $row3['zip'], "|", $row2['name'], "|", $row2['type'], "|", $row2['breed'], "|", $row2['description'], "|", $row2['photo'], "|", $distance;
  }
  // Error checking for if the post ID does not match any Booking in the database
  else
  {
    echo "noExist";
  }
  // Closes the connection to the database
  $mysqli->close();

?>
