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
    echo ("Connection unsuccessful");
  }

  // Takes in latitude, longitude, search distance, type, and breed as input
  $latitude = $_POST['latitude'];
  $longitude = $_POST['longitude'];
  $searchDistance = $_POST['searchDistance'];
  $type = $_POST['type'];
  $breed = $_POST['breed'];

  // SQL statement that will be modified by search criteria. Joins the tables and makes sure the requests are not booked and they are in the future
  $sql = "select Booking.* from Booking, PetOwner, Pet where (Booking.ownerEmail = PetOwner.email) and (Pet.ownerEmail = PetOwner.email) and (sitterEmail is null) and (startDate >= curDate())";

  // IF the user provides a type or breed, refines the SQL statement
  if (strcasecmp($type, "type") !== 0) {
    $sql .= " and (Pet.type = '$type')"; }
  if (strcasecmp($breed, "breed") !== 0) {
    $sql .= " and (Pet.breed = '$breed')";
  }

  // Run the query and get the number of results
  $result = $mysqli->query($sql);
  $rows = mysqli_num_rows($result);

  // If there are any results, loop through them
  if ($rows != 0) {
    while ($row = $result->fetch_assoc())
    {
      // Gets information about the PetOwner and Pet associated with each Booking
      $ownerEmail = $row['ownerEmail'];
      $sql2 = "select * from Pet where ownerEmail = '$ownerEmail'";
      $result2 = $mysqli->query($sql2);
      $row2 = $result2->fetch_assoc();

      $sql3 = "select * from PetOwner where email = '$ownerEmail'";
      $result3 = $mysqli->query($sql3);
      $row3 = $result3->fetch_assoc();

      // Default distance is 0
      $distance = 0;
      // If the PetOwner and PetSitter have the same latitude and longitude, distance is 0
      if (($latitude == $row3['latitude']) && ($longitude == $row3['longitude'])) {
        $distance =  0;
      }
      // Calculates the distance from PetOwner to PetSitter
      else {
        $theta = $longitude - $row3['longitude'];
        $dist = (sin(deg2rad($latitude)) * sin(deg2rad($row3['latitude']))) + (cos(deg2rad($latitude)) * cos(deg2rad($row3['latitude'])) * cos(deg2rad($theta)));
        $dist = acos($dist);
        $dist = rad2deg($dist);
        $dist = $dist * 60 * 1.1515;
        // Rounds up to the nearest integer
        $distance = ceil($dist);
      }
      // If the search distance is not specified, returns every matching Booking
      if ($searchDistance == -1) {
        echo $row['id'], "|", $row2['type'], "|", $row2['breed'], "|", $row2['photo'], "|", $distance, "|", $row['startDate'], "|", $row['endDate'], "~";
      }
      // The search distance is specified
      else
      {
        // If the calculated distance is less than the search distance, return the Booking
        if ($distance <= $searchDistance) {
          echo $row['id'], "|", $row2['type'], "|", $row2['breed'], "|", $row2['photo'], "|", $distance, "|", $row['startDate'], "|", $row['endDate'], "~";
        }
      }
    }
  }
  // Error checking for when no Bookings match the initial SQL query
  else
  {
    echo "noRequests";
  }
  // Closes the connection to the database
  $mysqli->close();

?>

