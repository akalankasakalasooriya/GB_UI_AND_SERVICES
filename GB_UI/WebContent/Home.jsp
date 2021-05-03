<%@include file="Header.jsp"%>
<!-- header -->
<title>Home</title>
<%@include file="NavBar.jsp"%>
<script type="text/javascript">
	var x = document.cookie;
	var res = x.split('#')
	console.log(res[1]);
	if (res[1] == "") {
		window.location.href = "Login.jsp";
	}
</script>
<div class="jumbotron jumbotron-fluid">
	<div class="container">
		<h1 class="display-4">GadgetBadgetn</h1>
		<p class="lead">Buy innovative products in a single place</p>
	</div>
</div>

<div class=" mx-auto " style="width: 7in; margin-top: 1cm">
	<table style="width: 100%">
		<script>
		//loading.gif
			var count =1;
			var i;
			var j;
			for (i = 0; i < 3; i++) {
				document.write("<tr>");
				for (j = 0; j < 3; j++) {
					
					document.write('<td><div class="card" style="width: 18rem;">'+
					'<img class="card-img-top" src="Assets/item.svg"'+
								'alt="Card image cap">'+
							'<div class="card-body">'+
								'<h5 class="card-title">item '+count+'</h5>'+
								'<p class="card-text">Description</p>'+
								'<div class="d-block"><button class="d-inline btn btn-primary">Add</button>'+
								'<button class="d-inline btn btn-danger" style="visibility: hidden;">Remove</button></div>'+
							'</div>'+
						'</div></td>');
					count++;
					
					

				}
				document.write("</tr>");
			}
			
		</script>

	</table>
</div>


<!-- footer -->
<%@include file="Footer.jsp"%>