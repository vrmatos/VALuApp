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
    echo("Unsuccessful connection");
  }

  // Takes in email as input
  $email = $_POST['email'];

  // Gets from the PetSitter table where the email matches the one given
  $sql = "select * from PetSitter where email = '$email'";
  $result = $mysqli->query($sql);

  // Gets the results of the query
  $row = $result->fetch_assoc();
  // Gets the number of results
  $rowCount = mysqli_num_rows($result);
  // Updates every Booking that the PetSitter signed up for to free up that request
  $sqlUpdate = "update Booking set sitterEmail = null where sitterEmail = '$email'";
  $updateResult = $mysqli->query($sqlUpdate);

  // If there are no PetSitters with the given email, returns an error
  if ($rowCount == 0)
  {
    echo "email";
  }
  // Deletes from the PetSitter table where the email matches the one given
  else
  {
    $sql2 = "delete from PetSitter where email = '$email'";
    $result2 = $mysqli->query($sql2);
    // Returns a confirmation message that the deletion has occurred
    echo "deleted";
  }
  // Closes the connection to the database
  $mysqli->close();
?>

