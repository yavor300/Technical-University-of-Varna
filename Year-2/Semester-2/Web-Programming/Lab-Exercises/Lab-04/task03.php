
/*
* Задача 3. Създайте скрипт, който извежда следната таблица в прозореца на браузъра.
*/

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset=UTF-8">
    <title></title>
  </head>
  <body>
    <table border="1">
      <tbody>
        <?php
        for ($i = 1; $i <= 7; $i++) {
          
          echo "<tr>";
          
          for ($j = 1; $j <= 7; $j++) {
            $result = $j * $i;
            echo "<td>$result</td>";
          }
          
          echo "</tr>";
        }
        ?>
      </tbody>
    </table>
  </body>
</html>



