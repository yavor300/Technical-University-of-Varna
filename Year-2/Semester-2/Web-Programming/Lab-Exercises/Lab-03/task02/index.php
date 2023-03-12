<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<form method="post" action="#">
Please enter the day /Mon...Sun/: <br />
<input type="text" name="data" />
<p />
<input type="submit" name="submit" value="Go" />
</form>
<?php
if (isset($_POST['submit'])){ //Retrieve string from form submission.
$d = $_POST["data"];
if ($d==date("D"))
echo 'The day is '.$d;
else
echo 'The actual day is not '.$d.'<br>The day is '.date("D");
}
?>
</body>
</html>