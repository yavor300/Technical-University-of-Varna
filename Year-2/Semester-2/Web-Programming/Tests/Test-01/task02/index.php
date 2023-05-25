<?php

/*
 * Задача 2.
 * 
 * Имаме едномерен числов масив. Напишете функция, която
 * премества всички нулеви елементи на масива в неговият край
 */

function moveZerosInEnd($inputArray) {

  $zeros = array();
  $nonZeros = array();

  foreach ($inputArray as $number) {
    if ($number === 0) {
      $zeros[] = $number;
    } else {
      $nonZeros[] = $number;
    }
  }

  return array_merge($nonZeros, $zeros);
}

$input = array(56, 0, 22, 0, 0, 34, 14, 6, 17, 10);
$output = moveZerosInEnd($input); //Array ( [0] => 56 [1] => 22 [2] => 34 [3] => 14 [4] => 6 [5] => 17 [6] => 10 [7] => 0 [8] => 0 [9] => 0 )
print_r($output);
?>
