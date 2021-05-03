<%@include file="Header.jsp"%>
<!-- header -->
<title>Register</title>
<%@include file="NavBar.jsp"%>

<form class="ml-3 mr-3 mx-auto " style="width:7in;margin-top:1cm">
	<div class="form-group row">
		<label for="inputEmail3" class="col-sm-2 col-form-label">User name</label>
		<div class="col-sm-10">
			<input type="email" class="form-control" id="inputEmail3"
				placeholder="Email">
		</div>
	</div>
	<div class="form-group row">
		<label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="inputPassword3"
				placeholder="Password">
		</div>
	</div>
	

	<div class="form-group row">
		<div class="col-sm-10">
			<button type="submit" class="btn btn-primary">Register</button>
		</div>
	</div>
</form>

<!-- footer -->
<%@include file="Footer.jsp"%>