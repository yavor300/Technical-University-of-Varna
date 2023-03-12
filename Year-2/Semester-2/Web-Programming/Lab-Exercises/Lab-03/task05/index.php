<?php
/**
 * Задача 6.
 * Напишете скрипт, който по въведено естествено число N търси брой срещания на
 * указана цифра.
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
      <label for="number">a:</label>
      <input id="number" type="text" name="number" />
      <br/>
      <label for="digit">b:</label>
      <input id="digit" type="number" name="digit" />
      <br/>
      <input type="submit" name="submit" value="Count" style="margin-top: 10px" />
    </form>
    <?php
    if (isset($_POST['submit'])) {
      $number = $_POST["number"];
      $digit = $_POST["digit"];
      
      $count = 0;
      
      for ($i = 0; $i < strlen($number); $i++) {
        if ($number[$i] == $digit) {
          $count++;
        }
      }

      echo $count;
    }
    ?>
  </body>
</html>
