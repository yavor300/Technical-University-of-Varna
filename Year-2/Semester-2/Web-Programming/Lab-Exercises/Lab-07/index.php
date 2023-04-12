<?php
/*
 * Създайте файл index.php, който да съдържа форма за Upload на файлове.
 */

$handle = fopen("data.txt", "w+");
fwrite($handle, 'Дисциплина Web приложения се изучава през втория семестър на втори курс. Тази дисциплина се изучава от студенти, които са специалност СИТ.');
fclose($handle);
$handle = fopen("data.txt", "a");
fwrite($handle, 'Студентите редовно си пишат домашните.');
fclose($handle);

$fp = @fopen("data.txt", "r");
if ($fp) {
  while (!feof($fp)) {
    $buffer = fgets($fp);
    echo $buffer;
  }
  fclose($fp);
}

$size = filesize("data.txt");
echo "$size";
?>

<!DOCTYPE html>
<html>
  <body>
    <form action="upload_file.php" method="post" enctype="multipart/form-data">
      Filename:
      <input type="file" name="fileToUpload" id="fileToUpload">
      <br>
      <input type="submit" value="Submit" name="submit">
    </form>
  </body>
</html>