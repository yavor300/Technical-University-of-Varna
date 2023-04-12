<?php
/*
 * Задача 1. Да се дефинира един масив students с ключове – имена на студенти, 
  стойности – среден успех. Да се отпечата масива като номериран списък, като
  неномериран списък и в табличен вид.
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
        $students = array(
          0 => array
            ("firstName" => "Иван",
            "lastName" => "Иванов",
            "grade" => 4),
          1 => array
            ("firstName" => "Драган",
            "lastName" => "Драганов",
            "grade" => 5.45),
          2 => array
            ("firstName" => "Димов",
            "lastName" => "Петров",
            "grade" => 5.67)
        );
        for ($i = 0; $i < 3; $i++) {
          echo "<li>" . "Студент" . $students[$i]["firstName"] . " " . $students[$i]["lastName"] . " " . $students[$i]["grade"] . "</li>";
        }
        ?>
    </ol>
  </body>
</html>

