<?php
session_start();
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
  // Form retrieval
  $ownerEmail = $_POST["ownerEmail"];
  $name = $_POST["name"];
  $type = $_POST["type"];
  $breed = $_POST["breed"];
  $description = $_POST["description"];
  $target_dir = "uploads/";
  $target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
  $uploadOk = 1;
  $imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));

  // Check if image file is a actual image or fake image
  if(isset($_POST["submit"])) {
    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if($check !== false) {
      echo "File is an image - " . $check["mime"] . ".";
      $uploadOk = 1;
    } else {
      echo "File is not an image.";
      $uploadOk = 0;
    }
  }

  // Check if file already exists
  if (file_exists($target_file)) {
    echo "Sorry, file already exists.";
    $uploadOk = 0;
  }

  // Allow certain file formats
  if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" ) {
    //echo "Sorry, only JPG, JPEG, and PNG files are allowed.";
    $uploadOk = 0;
  }

  // Check if $uploadOk is set to 0 by an error
  if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
  // if everything is ok, try to upload file
  } else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
      echo "The file ". htmlspecialchars( basename( $_FILES["fileToUpload"]["name"])). " has been uploaded.";
    } else {
      echo "Sorry, there was an error uploading your file.";
    }
  }
  
  // Gets from the Pet table where the email and name match the ones given
  $sql = "select * from Pet where ownerEmail = '$ownerEmail' && name = '$name'";

  $resultselect = $mysqli->query($sql);
  // Gets the number of results
  $num = mysqli_num_rows($resultselect);

  // If there is anything matching the query, that pet is already in the database
  if($num == 1)
  {
    echo("Pet already in Database");
    echo('<br>');
    echo '<a href="Petcreation.php">Go Back</a>';
  }
  // Otherwise, the file uploaded by the user is added to the uploads directory
  else {
    $filename = basename($_FILES["fileToUpload"]["name"]);
    if($filename==null)
    {
      echo "Pet needs Photo cant be added";
      echo '<a href="Petcreation.php">Go Back</a>';
    }
    // The Pet is not already in the database
    else
    {
      // Adds the Pet to the database using provided input
      $sql2 = "insert into Pet values (null, '$ownerEmail', '$name', '$type', '$breed', '$description', '$filename')";
      echo $sql2;
      $result = $mysqli->query($sql2);
      header('location:PetMenu.php');
    }
  }
  // Closes the connection to the database
  $mysqli->close();
?>

