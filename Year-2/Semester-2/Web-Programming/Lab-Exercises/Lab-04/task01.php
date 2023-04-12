/*
* Задача 1.
* Създайте скрипт, който извежда следният неномериран списък
* в прозореца на браузъра.
*/
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
  </head>
  <body>
    <ul type="square">
        <?php
        
        for ($i = 1; $i <= 10; $i++) {
          echo "<li>X = $i</li>";
          echo "<ul>";;
          $number = pow($i, 3);
          echo "<li>$number</li>";
          echo "</ul>";
        }
        ?>
    </ul>
  </body>
</html>
