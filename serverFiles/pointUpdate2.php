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
  
  // Takes in email and points as input
  $email = $_POST['email'];
  $points = $_POST['points'];

  // Updates any PetSitter with the given email to have the given number of points
  $sql = "update PetSitter set points = '$points' where email = '$email'";

  $result = $mysqli->query($sql);

  // Closes the connection to the database
  $mysqli->close();
?>

