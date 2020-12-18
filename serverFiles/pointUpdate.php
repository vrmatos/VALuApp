<?php
  // Required information to access the database
  $username = "lalexander2";
  $password = "1765882";
  $host = "cs-database.cs.loyola.edu";
  $database = "valu";

  // Establish the connection to the defined database
  $mysqli = new mysqli($host, $username, $password, $database);
  if (!$mysqli)
  {
    echo("Unsuccessful connection" );
  }

  // Takes in email as input
  $email = $_POST['email'];

  // Gets the dates of every Booking occurring in the past that matches the given email
  $sql = "select startDate, endDate from Booking where endDate < curdate() and sitterEmail = '$email'";
  $result = $mysqli->query($sql);

  // Gets the number of results
  $rowCount = mysqli_num_rows($result);

  // There are more than 0 Bookings matching the query
  if ($rowCount != 0)
  {
    // Loop through the Bookings
    while ($row = $result->fetch_assoc())
    {
      // Returns the dates
      echo $row['startDate'], "|", $row['endDate'], "~";
    }
  }
  // There are no Booking in the past associated with the given email
  else
  {
    echo "noPastBookings";
  }

  // Closes the connection with the database
  $mysqli->close();
?>



  

