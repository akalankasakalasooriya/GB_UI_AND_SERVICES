<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!doctype html>
<html lang="en">
<link rel="stylesheet" href="Components/bootstrap/bootstrap.min.css">
<script src="Components/jquery.js"></script>
<script src="Components/Main.js"></script>
<script type="text/javascript">
	var x = document.cookie;
	var res = x.split('#')
	console.log(res[1]);
	if (res[1] == "") {
		window.location.href = "Login.jsp";
	}
</script>
<head>
<meta charset="utf-8">
<link rel="icon" href="Assets/logo.ico">


</head>