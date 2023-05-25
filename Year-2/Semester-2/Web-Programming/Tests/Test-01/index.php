<?php
/*
 * Задача 1.
 */
?>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Task 01</title>
  </head>
  <body>
    <form method="post">

      <p>Имот:</p>

      <input type="radio" name="purpose" value="Търся под наем" id="Търся под наем">      
      <label for="Търся под наем">Търся под наем</label><br>

      <input type="radio" name="purpose" value="Давам под наем" id="Давам под наем">      
      <label for="Давам под наем">Давам под наем</label><br>

      <p>Моля изберете вид на имота:</p>
      <input type="checkbox" name="types[]" value="Двустаен Апартамент" id="Двустаен Апартамент">
      <label for="Двустаен Апартамент">Двустаен апартамент</label><br>

      <input type="checkbox" name="types[]" value="Тристаен Апартамент" id="Тристаен Апартамент">
      <label for="Тристаен Апартамент">Тристаен апартамент</label><br>

      <input type="checkbox" name="types[]" value="Гарсониера" id="Гарсониера">
      <label for="Гарсониера">Гарсониера</label><br>

      <input type="checkbox" name="types[]" value="Боксониера" id="Боксониера">
      <label for="Боксониера">Боксониера</label><br>

      <input type="checkbox" name="types[]" value="Къща" id="Къща">
      <label for="Къща">Къща</label><br>

      <input type="checkbox" name="types[]" value="Вила" id="Вила">
      <label for="Вила">Вила</label><br>

      <p>Въведете минимална и максимална цена:</p>
      <label for="minPrice">Минимална цена</label>
      <input type="number" name="minPrice" step="0.01" id="minPrice" min="0"><br>
      <label for="maxPrice">Максимална цена</label>
      <input type="number" name="maxPrice" step="0.01" id="maxPrice" min="0"><br>

      <p>Въведете следната информация:</p>

      <label for="town">Град/Село:</label>
      <input type="text" name="town" id="town"><br>

      <label for="info">Допълнителна информация:</label><br>
      <textarea id="info" name="info" rows="5" cols="40"></textarea><br><br>

      <input type="submit" name="submit" value="Запис">
      <input type="reset" name="reset" value="Отказ">
    </form>

    <?php
    if (isset($_POST['submit'])) {

      $file = fopen("data.txt", "w+");

      if (!isset($_POST['purpose'])) {
        echo "Не сте избрали причина за търсене или предлагане на имота.";
        echo "<br>";
        fwrite($file, "Не сте избрали причина за търсене или предлагане на имота.\n");
      } else {
        $purpose = $_POST["purpose"];
        echo "$purpose";
        echo "<br>";
        fwrite($file, "$purpose\n");
      }


      if (!isset($_POST['types'])) {
        echo "Не сте избрали тип на имота.";
        echo "<br>";
        fwrite($file, "Не сте избрали тип на имота.\n");
      } else {
        $types = $_POST['types'];
        echo "<ul>";
        foreach ($types as $type) {
          echo "<li>" . $type . "</li>";
          fwrite($file, "$type\n");
        }
        echo "</ul>";
      }

      $minPrice = 0;
      if (!isset($_POST['minPrice']) || $_POST['minPrice'] == '') {
        echo "Не сте избрали минимална цена.";
        echo "<br>";
        fwrite($file, "Не сте избрали минимална цена.\n");
      } else {
        $minPrice = $_POST["minPrice"];
      }

      $maxPrice = 0;
      if (!isset($_POST['maxPrice']) || $_POST['maxPrice'] == '') {
        echo "Не сте избрали максимална цена.";
        echo "<br>";
        fwrite($file, "Не сте избрали максимална цена.\n");
      } else {
        $maxPrice = $_POST["maxPrice"];
      }

      $averagePrice = ($minPrice + $maxPrice) / 2;
      echo "Средна цена: $averagePrice";
      echo "<br>";
      fwrite($file, "Средна цена: $averagePrice\n");

      if (!isset($_POST['town']) || $_POST['town'] == '') {
        echo "Не сте избрали град.";
        echo "<br>";
        fwrite($file, "Не сте избрали град.\n");
      } else {
        $town = $_POST["town"];
        echo "Гр $town";
        echo "<br>";
        fwrite($file, "Гр $town\n");
      }

      if (!isset($_POST['info']) || $_POST['info'] == '') {
        echo "Не сте подали информация.";
        echo "<br>";
        fwrite($file, "Не сте подали информация.\n");
      } else {
        $info = $_POST["info"];
        echo "$info";
        fwrite($file, "$info");
      }

      fclose($file);
    }
    ?>
  </body>
</html>
