<?php

$i = 1;
while ($i) {
  $n = rand(1, 10);
// генерираме произволно число
// от 1 до 10
  switch ($n) {
    case 5:
      echo "Изход от switch (n=$n)";
      break 1; // прекратява се работата на switch
    case 10:
      echo "Exit from switch and while (n=$n)";
      break 2;
// прекратява се работата на switch и while
    default:
      echo "switch works (n=$n), ";
  }
  echo " while works – step $i <br>";
  $i++;
}
echo "<br>Number of iterations $i ";
?>