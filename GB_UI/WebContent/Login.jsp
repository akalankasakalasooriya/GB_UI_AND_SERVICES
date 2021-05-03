<%@include file="Header.jsp"%>
<link rel="stylesheet" href="Components/style.css">
<!-- header -->

<title>Sign in</title>
<body class="text-center">

	<main class="form-signin">
		<form>
			<div class="jumbotron">
				<h1 class="display-4">GadgetBadget</h1>
			</div>
			<h1 class="h3 mb-3 fw-normal">sign in</h1>

			<div class="form-floating">
				<input type="text" class="form-control" id="txt_login_un"
					placeholder="Username"> <label for="floatingInput">Username</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="txt_login_pw"
					placeholder="Password"> <label for="floatingPassword">Password</label>
				<span id="login_invalid" class="badge badge-danger"
					style="color: red; visibility: hidden;">invalid credentials</span>
			</div>

			<br>
			<button class="w-100 btn btn-lg btn-primary" type="button"
				id="btn_login">Sign in</button>

			<a href="Register.jsp"><button type="button"
					class="w-100 btn btn-lg btn-secondary mt-1">Register</button></a>
			<p class="mt-5 mb-3 text-muted">2021-GB</p>
		</form>
	</main>
</body>

<!-- footer -->
<%@include file="Footer.jsp"%>