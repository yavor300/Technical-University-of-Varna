<?php

// Проверка дали формата е изпратена с POST метод
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  
  // Проверка дали са попълнени всички задължителни полета
  if (isset($_POST["first-name"]) && isset($_POST["last-name"]) && isset($_POST["course"]) && isset($_POST["specialty"]) && isset
($_POST["operator-type"]) && isset($_FILES["file-upload"]) && !empty($_FILES["file-upload"]["name"])) {
// Взимане на стойностите от формата
$firstName = $_POST["first-name"];
$lastName = $_POST["last-name"];
$course = $_POST["course"];
$specialty = $_POST["specialty"];
$operatorType = $_POST["operator-type"];
$comments = $_POST["comments"];

// Валидация на качения файл
$fileUpload = $_FILES["file-upload"];
$fileName = $fileUpload["name"];
$fileType = $fileUpload["type"];
$fileSize = $fileUpload["size"];
$fileError = $fileUpload["error"];
$fileTmpName = $fileUpload["tmp_name"];

// Проверка на грешки при качването на файла
if ($fileError === 0) {
  // Проверка на размера на файла
  if ($fileSize < 5000000) {
    // Проверка на разширението на файла
    $fileExt = pathinfo($fileName, PATHINFO_EXTENSION);
    $allowedFileTypes = array("jpg", "png", "txt");
    if (in_array($fileExt, $allowedFileTypes)) {
      // Генериране на уникално име за файла
      $newFileName = uniqid("", true) . "." . $fileExt;
      // Прехвърляне на файла в папка uploads
      $uploadsDir = "./uploads/";
      move_uploaded_file($fileTmpName, $uploadsDir . $newFileName);
      // Прехвърляне на файла в друга папка
      $otherDir = "./other/";
      copy($uploadsDir . $newFileName, $otherDir . $newFileName);
      echo "Файлът е качен успешно.";
    } else {
      echo "Грешка: Разрешени са само JPG, PNG и TXT файлове.";
    }
  } else {
    echo "Грешка: Максималният размер на файла е 5MB.";
  }
} else {
  echo "Грешка при качване на файла: " . $fileError;
}
} else {
echo "Моля, попълнете всички задължителни полета.";
}
}
?>