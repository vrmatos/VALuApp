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

  // Takes in the old email as input
  $oldEmail = $_POST['oldEmail'];

  // Gets from the PetSitter table where the old email matches the given email
  $sql = "select * from PetSitter where email = '$oldEmail'";
  $result = $mysqli->query($sql);

  // Gets the results of the query
  $row = $result->fetch_assoc();
  // Gets the number of results
  $rowCount = mysqli_num_rows($result);

  // If there are no results of the query, returns an error statement
  if ($rowCount == 0)
  {
    echo "email";
  }
  // There are results of the query
  else
  {
    // Returns relevant user information
    echo $row['streetAddress'], "|", $row['city'], "|", $row['state'], "|", $row['zip'];
  }

  // Closes the connection to the database
  $mysqli -> close();
?>

