<?php
/*
  Задача 4. Като използвате цикъл for, да се напише скрипт, който проверява дали число
  въведено от потребителя е просто.
 */
?>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Task04</title>
  </head>
  <body>
    <form method="post" action="#">
      <label for="number">Check number:</label>
      <br/>
      <input id="number" type="number" name="number" />
      <br/>
      <input type="submit" name="submit" value="Go" />
    </form>

    <?php
    if (isset($_POST['submit'])) {
      $number = $_POST["number"];
      $is_prime = true;
      if ($number == 1)
        $is_prime = false;
      for ($i = 2; $i <= $number / 2; $i++) {
        if ($number % $i == 0)
          $is_prime = false;
      }
      
      if ($is_prime) {
        echo "Просто";
      } else {
        echo "Не е просто";
      }
    }
    ?>
  </body>
</html>
