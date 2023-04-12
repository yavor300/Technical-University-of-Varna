<?php
$i = 1;
while ($i) {
  $n = rand(1, 10); // генерираме произволно число от 1 до 10
  echo "<br>iteration $i->number generated: $n ";
  if ($n == 5)
    break;
  if ($n == 7)
    continue;
  echo "<br>Next iteration ...<br>";
  $i++;
}
echo "<br>Number of iterations $i The cycle stops!";
?>
