<%@include file="Header.jsp"%>
<!-- header -->
<title>Profile</title>
<%@include file="NavBar.jsp"%>

<form class="ml-3 mr-3 mx-auto " style="width: 7in; margin-top: 1cm">
	<div class="form-group row">
		<label for="inputEmail3" class="col-sm-2 col-form-label">User
			name</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="profileUsername"
				placeholder="username">
		</div>
	</div>
	<div class="form-group row">
		<label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="profilePassword"
				placeholder="Password">
		</div>
	</div>


	<div class="form-group row">
		<div class="col-sm-10">
			<button type="button" class="btn btn-primary" id="profileUpdate" disabled>Update</button>
		</div>
	</div>
</form>

<!-- footer -->
<%@include file="Footer.jsp"%>

