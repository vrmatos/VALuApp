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
    echo("Unsuccessful connection" );
  }

  // Takes in an email and password as input
  $email = $_POST['email'];
  $password = $_POST['password'];
  // Encrypts the password
  $hash = password_hash($password, PASSWORD_DEFAULT);
  
  // Gets from the PetSitter table where the email matches the one given
  $sql = "select * from PetSitter where email = '$email'";
  $result = $mysqli->query($sql);

  // Get the results of the query
  $row = $result->fetch_assoc();
  // Get the number of results
  $rowCount = mysqli_num_rows($result); 

  // If there are no entries, return an error
  if ($rowCount == 0)
  {
    echo "email";
  }
  // Otherwise, if the password doesn't match return an error
  else if (!password_verify($password, $row['password']))	
  {
    echo "password";
  }
  // If the email and password match, return information needed for the home page
  else
  {
    echo $row['firstName'], "|", $row['lastName'], "|", $row['latitude'], "|", $row['longitude'], "|", date('Y-m-d');
  }
  // Close the connection to the database
  $mysqli->close();
?>

