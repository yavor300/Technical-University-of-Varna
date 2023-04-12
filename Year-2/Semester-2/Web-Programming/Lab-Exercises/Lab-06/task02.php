<?php

/* 
 * Задача 2. Да  се  напише  скрипт,  който  по  въведенитри  стойности  от 
 * потребителя проверява  дали  геометричната  фигура  е  триъгълник  и  определя
 * лицето  и вида  и  в зависимост от въведените стойности.Упътване: 
 * Използвайте Херонова формула за лице на триъгълник.
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
      <p>Лице на триъгълник</p>
      <label for="number">a:</label>
      <input id="number" type="number" name="a" />
      <label for="number">b:</label>
      <input id="number" type="number" name="b" />
      <label for="number">c:</label>
      <input id="number" type="number" name="c" />
      <input type="submit" name="submit" value="Go" />
    </form>

    <?php
    function calculateArea($length, $width) {
     return $length * $width;
    }
    
    if (isset($_POST['submit'])) {
      $length = $_POST["length"];
      $width = $_POST["width"];
      
      $area = $length * $width;
      echo "$area";
    }
    ?>
  </body>
</html>
