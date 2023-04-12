<?php
/**
 *Да се напише скрипт, който извежда всички числа в интервала [1..100], със
сумата от цифрите равна на X (зададено от потребителя)
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
      <label for="digit">b:</label>
      <input id="digit" type="number" name="digit" />
      <br/>
      <input type="submit" name="submit" value="Get" style="margin-top: 10px" />
    </form>
    <?php
    if (isset($_POST['submit'])) {
      $digit = $_POST["digit"];
      $count = 0;
      
      for ($i = 0; $i < 100; $i++) {
        for ($j = 0; $j < strlen($i); $j++) {
          $sum = 0;
          if ($i[$j] == $digit) {
          $count++;
         }
        }
        
      }

      echo $count;
    }
    ?>
  </body>
</html>
