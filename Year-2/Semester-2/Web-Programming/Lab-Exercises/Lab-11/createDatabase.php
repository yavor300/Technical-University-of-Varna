<?php

$host = 'localhost';
$dbUser = 'root';
$dbPass = '';

if (!$dbConn = mysql_connect($host, $dbUser, $dbPass)) {
  die('Не може да се осъществи връзка със сървъра:' . mysql_error());
}

$sql = 'CREATE Database info_books';

if ($queryResource = mysql_query($sql, $dbConn)) {
  echo "Базата данни е създадена.<br>";
} else {
  echo "Грешка при създаване на базата данни: " . mysql_error();
}