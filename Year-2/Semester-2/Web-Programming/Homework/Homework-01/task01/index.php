<?php
/*
 * Задача 3. Изисква се да създадете форма, изискваща от потребителя да избере опции за
 * времето, през даден месец. В отделни полета се въвеждат името на града, месеца и годината.
 * Под тези полета се изреждат списък от checkboxes със стойност: rain, sunshine, clouds, hail,
 * sleet, snow, wind. Създайте масив $weather[], от избраните опции, по следния начин:
 * <input type="checkbox" name="weather[]" value="sunshine" />Sunshine<br />
 */
?>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task01</title>
    </head>
    <body>
        <p>Please enter your information:</p>
        <form method="post" action="#">
            City:
            <input type="text" name="inputLocal[]"/>
            Month:
            <input type="text" name="inputLocal[]" />
            Year:
            <input type="text" name="inputLocal[]" />

            <p>Please choose the kinds of weather you experienced from the list below.</p>
            <p>Choose all that apply.</p>
            <input type="checkbox" name="weather[]" value="sunshine" />Sunshine<br />
            <input type="checkbox" name="weather[]" value="clouds" />Clouds<br />
            <input type="checkbox" name="weather[]" value="rain" />Rain<br />
            <input type="checkbox" name="weather[]" value="hail" />Hail<br />
            <input type="checkbox" name="weather[]" value="sleet" />Sleet<br />
            <input type="checkbox" name="weather[]" value="snow" />Snow<br />
            <input type="checkbox" name="weather[]" value="wind" />Wind<br />
            <input type="checkbox" name="weather[]" value="cold" />Cold<br />
            <input type="checkbox" name="weather[]" value="heat" />Heat<br />
            <input type="submit" name="submit" value="Go" />
        </form>
        <?php
        if (isset($_POST['submit'])) {
          $weather = $_POST["weather"];
          $inputLocal = $_POST["inputLocal"];
          $city = $inputLocal[0];
          $month = $inputLocal[1];
          $year = $inputLocal[2];

          echo "In $city in the month of $month $year, you observed the following weather:";
          echo '<ul>';
          for ($i = 0; $i < count($weather); $i++) {
            echo "<li>" . "$weather[$i]" . "</li>";
          }
          echo '</ul>';
        }
        ?>
    </body>
</html>