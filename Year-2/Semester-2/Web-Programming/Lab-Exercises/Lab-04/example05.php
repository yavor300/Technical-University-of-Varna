<?php

$i = 1;
while ($i < 4) {
  $n = rand(1, 10);
// генерираме произволно число от 1 до 10
  echo "$i:$n ";
// извеждаме номер на итерация и генерираното число
  if ($n == 5) {
    echo "New iteration <br>";
    continue;
    /* Ако се генерира число 5,
      то започва нова итерация,
      $i не се увеличава */
  }
  echo "The cycle works<br>";
  $i++;
}
--$i;
echo "<br>Number of iterations $i ";
?>