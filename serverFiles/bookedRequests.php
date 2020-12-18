<?php
  // Required information to connect to the database
  $username = "lalexander2";
  $password = "1765882";
  $host = "cs-database.cs.loyola.edu";
  $database = "valu";

  // Establish the connection to the defined database
  $mysqli = new mysqli($host, $username, $password, $database);
  if (!$mysqli)
  {
    echo ("Connection unsuccessful");
  }

  // Takes in email, latitude, and longitude as input
  $email = $_POST['email'];
  $latitude = $_POST['latitude'];
  $longitude = $_POST['longitude'];

  // Gets from the Booking table where the PetSitter's email matches that of the Booking
  $sql = "select * from Booking where sitterEmail = '$email'";
  $result = $mysqli->query($sql);
  // Gets the number of results
  $rows = mysqli_num_rows($result);

  // If there are any Bookings that match the email
  if ($rows != 0) {
    // Loop through each Booking from the results
    while ($row = $result->fetch_assoc())
    {
      // Fetches information about the Pet and PetOwner associated with the Booking
      $ownerEmail = $row['ownerEmail'];
      $sql2 = "select * from Pet where ownerEmail = '$ownerEmail'";
      $result2 = $mysqli->query($sql2);
      $row2 = $result2->fetch_assoc();

      $sql3 = "select * from PetOwner where email = '$ownerEmail'";
      $result3 = $mysqli->query($sql3);
      $row3 = $result3->fetch_assoc();
      // Default distance is 0
      $distance = 0;
      // If the Sitter and Owner's latitude and longitude match, distance is 0
      if (($latitude == $row3['latitude']) && ($longitude == $row3['longitude'])) {
        $distance =  0;
      }
      else {
        // Calculates the distance from PetSitter to PetOwner in miles
        $theta = $longitude - $row3['longitude'];
        $dist = (sin(deg2rad($latitude)) * sin(deg2rad($row3['latitude']))) + (cos(deg2rad($latitude)) * cos(deg2rad($row3['latitude'])) * cos(deg2rad($theta)));
        $dist = acos($dist);
        $dist = rad2deg($dist);
        $dist = $dist * 60 * 1.1515;
        // Rounds the distance up to the nearest integer
        $distance = ceil($dist);
      }
      // Returns relevant information about each Booking
      echo $row['id'], "|", $row2['type'], "|", $row2['breed'], "|", $row2['photo'], "|", $distance, "|", $row['startDate'], "|", $row['endDate'], "~";
    }
  }
  // Error checking for when no requests/bookings match the Pet Sitter
  else
  {
    echo "noRequests";
  }
  // Close the connection to the database
  $mysqli->close();

?>

