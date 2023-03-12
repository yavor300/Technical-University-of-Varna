<?php
/**
 * Задача 2.
 * Да се намерят корените на квадратно уравнение.
 */
?>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Task03</title>
  </head>
  <body>
    <form method="post" action="#">
      <label for="a">a:</label>
      <input id="a" type="number" name="a" />
      <br/>
      <label for="b">b:</label>
      <input id="b" type="number" name="b" />
      <br/>
      <label for="c">c:</label>
      <input id="c" type="number" name="c" />
      <br/>
      <input type="submit" name="submit" value="Find x!" style="margin-top: 10px" />
    </form>
    <?php
    if (isset($_POST['submit'])) {
      $a = $_POST["a"];
      $b = $_POST["b"];
      $c = $_POST["c"];

      $d = pow($b, 2) - 4 * $a * $c;
      $x1 = (-$b + sqrt($d)) / (2 * $a);
      $x2 = (-$b - sqrt($d)) / (2 * $a);
      
      echo round($x1, 2);
      echo ' ';
      echo round($x2, 2);
    }
    ?>
  </body>
</html>
