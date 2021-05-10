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
<div class="container">
	<div class="row">
		<div class="col">
			<div class="jumbotron jumbotron-fluid">
				<div class="container">
					<h1 class="display-4">GadgetBadgetn</h1>
					<p class="lead">Buy innovative products in a single place</p>
				</div>
			</div>
		</div>
		<div class="col"></div>
	</div>
</div>


<div class=" mx-auto " style="margin-top: 1cm; margin-left: 1cm">
	<table style="width: 100%">
		<script>
			printItemTable();
		</script>

	</table>

	<div class="card">
		<div class="card-body">
			<div class="container">
				<form class="">
					<div class="row">
						<div class="col">
							<label for="" class="sr-only" id="singleItemNumber"
								style="width: 3cm=">please select an item</label>
						</div>
						<div class="col">
							<input type="number" class="form-control" id="itemQuantity" placeholder="Quantity"> 
							<input type="hidden" class="form-control" id="itemID">
						</div>
						<div class="col">
							<button type="button" id="sumbitItem" class="d-inline btn btn-primary">Add item to cart</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	</br>
	<p class="text-center">Your cart is here</p>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Name</th>
				<th scope="col">Quantity</th>
				<th scope="col">Actions</th>
			</tr>
		</thead>
		<tbody id="home_item_tbl_body">

		</tbody>
	</table>
	<script>
		bringCartItemHandler();
	</script>
	<div id="home_cartitems"></div>
</div>


<!-- footer -->
<%@include file="Footer.jsp"%>