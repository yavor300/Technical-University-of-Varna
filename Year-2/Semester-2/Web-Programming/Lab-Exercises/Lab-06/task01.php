<?php

/* 
 * Задача 1. Създайте скрипт, съдържащ функция recArea, която получава 2 параметъра,
 * за дължина $l и ширина $w на правоъгълник и изчислява лицето му $area. Извежда
 * следния текст в прозореца на браузъра.
 */

?>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Task01</title>
  </head>
  <body>
    <h2>Лице на правоъгълник</h2>
    <form method="post" action="#">
      <p>Въведете дължина и ширина на правоъгълника</p>
      <label for="number">Дължина:</label>
      <input id="number" type="number" name="length" />
      <label for="number">Ширина:</label>
      <input id="number" type="number" name="width" />
      <input type="submit" name="submit" value="Go" />
    </form>

    <?php
    function calculateArea($length, $width) {
     return $length * $width;
    }
    if (isset($_POST['submit'])) {
      $length = $_POST["length"];
      $width = $_POST["width"];
      
      $area = calculateArea($length, $width);
      echo "$area";
    }
    ?>
  </body>
</html>

