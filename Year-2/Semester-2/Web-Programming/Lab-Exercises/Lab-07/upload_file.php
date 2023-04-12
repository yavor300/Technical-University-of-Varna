<?php

$target_dir = "upload/";
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$uploadOk = 1;
$imageFileType = pathinfo($target_file, PATHINFO_EXTENSION);

// Check if image file is a actual image or fake image
if (isset($_POST["submit"])) {
  // Check file size
  if ($_FILES["fileToUpload"]["size"] > 200000) {
    echo "Sorry, your file is too large.";
    $uploadOk = 0;
  }

// Allow certain file formats
  if ($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg" && $imageFileType != "gif" && $imageFileType != "txt") {
    echo "Sorry, only TXT, JPG, JPEG, PNG & GIF files are allowed.";
    $uploadOk = 0;
  }
  
  if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
  } else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
      echo "The file " . basename($_FILES["fileToUpload"]["name"]) . " has been uploaded.";
    } else {
      echo "Sorry, there was an error while uploading the file.";
    }
  }
}
?>