<?php
/**
 * Задача 1. Един ресторант дава 50% отстъпка за деца на и под 6 години и за възрастни на и над
 * 65. Напишете php код, който изчислява отстъпката при дадени (или въвеждани от
 * клавиатурата) възраст и цена на поръчката, например $age=5; $price=45.0.
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
      <label for="age">Please enter your age:</label>
      <br/>
      <input id="age" type="number" name="age" />
      <br/>
      <label for="price">Please enter price:</label>
      <br/>
      <input id="price" type="text" name="price" />
      <br/>
      <input type="submit" name="submit" value="Go" />
    </form>
    <?php
    if (isset($_POST['submit'])) {
      $age = $_POST["age"];
      $price = $_POST["price"];

      if ($age <= 6 || $age >= 65) {
        echo $price * 0.5;
      } else {
        echo $price;
      }
    }
    ?>
  </body>
</html>
