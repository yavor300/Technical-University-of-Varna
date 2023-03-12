<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        $a=10;
        $b=10;
        if ($a > $b) {
            echo "$a е по-голямо от $b";
        } elseif ($a == $b) {
            echo "$a е равно на $b";
        } else {
            echo "$a е по-малко от $b";
        }
        ?>
    </body>
</html>
