<?php
/*
  Задача 2. Изисква се да създадете списък (масив) на 10 от най-големите градове на
  света: Tokyo, Mexico City, New York City, Mumbai, Seoul, Shanghai, Lagos, Buenos
  Aires, Cairo, London. Отпечатайте градовете, разделени със запетая в прозореца на
  браузъра, използвайки оператор за цикъл. Сортирайте масива, след което отпечатайте
  списъка отново като неномериран списък, пак използвайки оператор за цикъл.
  Добавете следните нови градове: Los Angeles, Calcutta, Osaka, Beijing. Пак сортирайте
  и отпечатайте, като номериран списък и в табличен вид.
 */
?>

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Task01</title>
  </head>
  <body>
    <ol>
        <?php
        $cities = array("Tokyo", "Mexico City", "New York City", "Mumbai", "Seoul", "Shanghai", "Lagos", "Buenos Aires", "Cairo", "London");

        sort($cities);

        for ($i = 0; $i < count($cities); $i++) {
          echo "<li>" . $cities[$i] . "</li>";
        }
        
        
        ?>
    </ol>
    <table border="1">
      <tbody>
        <tr>
          <td>Key</td>
          <td>Value</td>
        </tr>
        <?php
        
        array_push($cities, "Los Angeles");
        array_push($cities, "Calcutta");
        array_push($cities, "Osaka");
        array_push($cities, "Beijing");
        
        for ($i = 0; $i < count($cities); $i++) {
          
          echo "<tr>";
          
          echo "<td>$i</td>";
          echo "<td>$cities[$i]</td>";
          
          echo "</tr>";
        }
        ?>
      </tbody>
  </body>
</html>


