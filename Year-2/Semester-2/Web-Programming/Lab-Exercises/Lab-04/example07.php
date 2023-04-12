<?php

function Footer() {
// функция Footer
  include ("params.php");
  /* включваме файл params.php.
    Сега параметрите могат да се използват само във функцията */
  $str = "Today: $today <br>";
  $str .= "THE PAGE IS CREATED BY $user";
  echo "$str";
}

Footer();
echo "$user, $today";
// вижда се, че тези променливи са видни само във функцията
?>