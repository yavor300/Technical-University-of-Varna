<?php

$host = 'localhost';
$dbUser = 'root';
$dbPass = '';
$dbName = 'info_books';

if (!$dbConn = mysql_connect($host, $dbUser, $dbPass)) {
  die('Не може да се осъществи връзка със сървъра:' . mysql_error());
}if (!mysql_select_db($dbName, $dbConn)) {
  die('Не може да се селектира базата от данни:' . mysql_error());
}

mysql_query("SET NAMES 'UTF8'");

?>