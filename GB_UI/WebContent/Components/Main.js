function isEmpty(str_un, str_pw) {
  if (str_un == "" || str_pw == "") {
    return 0;
  }
  return 1;
}
function loginValidation(response, status) {
  var obj = JSON.parse(response);
  if (obj.validation_status == "valid") {
    //console.log( "2#" + obj.key);
    document.cookie = "1#" + obj.key;
    window.location.href = "Home.jsp";
    return true;
  } else {
    $("#login_invalid").css("visibility", "visible");
    return false;
  }
}

var loginClickHandler = function (e) {
  var str_un = $("#txt_login_un").val();
  var str_pw = $("#txt_login_pw").val();
  item = {};
  item["username"] = str_un;
  item["password"] = str_pw;

  if (isEmpty(item["username"], item["password"])) {
    $.ajax({
      url: "http://localhost:8081/authentication-service/API/session",
      type: "POST",
      async: true,
      data: JSON.stringify(item),
      processData: false,
      contentType: "application/json; charset=UTF-8",
      complete: function (response, status) {
        loginValidation(response.responseText, status);
      },
    });
    e.stopImmediatePropagation();
    return false;
  }
};

$("#btn_login").one("click", loginClickHandler);

//add item to cart

//print table
function printItemTable() {
  //loading.gif
  var count = 1;

  var j;

  document.write("<tr>");
  for (j = 0; j < 8; j++) {
    document.write(
      `<td><div class="card" style="width: 12rem;">
        <img class="card-img-top" src="Assets/item.svg"
        alt="Card image cap">
        <div class="card-body">
        <h5 class="card-title">item
        ${count}
        </h5>
        <p class="card-text">Description</p>
        <div class="d-block">
        <button class="d-inline btn btn-primary" name="pushToForm" data-itemnumber=${count}>Add</button>
        </div>
        </div></td>`
    );
    count++;
  }
  document.write("</tr>");
}

//read cart table

function printCarInfo(response, status) {
  var obj = JSON.parse(response);
  var data = "";
  var i = 1;
  console.log(obj);
  obj.items.forEach((singleItem) => {
    data += `<tr> <td>${i}</td>  <td>Item-ID ${singleItem.product_id}</td>  <td>${singleItem.quantity}</td> <td>
		
		<div class="btn-group" role="group">
  		<button type="button" class="btn btn btn-success btnUpdateSingle" name="btnUpdateSingle" data-itemid="${singleItem.product_id}">Update</button>
  		<button type="button" class="btn btn btn-danger btnDeleteSingle" name="btnDeleteSingle" data-itemid="${singleItem.product_id}">Delete </button>
		</div>
		
		</td> </tr>`;
    i++;
  });
  $("#home_item_tbl_body").html(data);

}
var bringCartItemHandler = function (e) {
  var x = document.cookie;
  var res = x.split("#");
  var key = res[1];
  var uid = res[0];

  $.ajax({
    url: `http://localhost:8081/payment-management-service/API/cartItems?key=${key}&user_id=${uid}`,
    type: "GET",
    async: true,
    processData: false,
    complete: function (response, status) {
      printCarInfo(response.responseText, status);
    },
  });
  return false;
};

//delete item
function onDelete(response, status) {
  var obj = JSON.parse(response);
  if (obj.status == "done") {
    bringCartItemHandler();
	makeNotification(`<span class="badge alert-secondary ">deleted</span>`);
    return true;
  } else {
    return false;
  }
}

$(document).on("click", 'button[name="btnDeleteSingle"]', function (event) {
  event.stopPropagation();
  event.stopImmediatePropagation();
  var x = document.cookie;
  var res = x.split("#");
  var key = res[1];
  var uid = res[0];
  var itemID = $(this).data("itemid");

  var reqbody = {
    'key': key,
    'user_id': uid,
    'product_id': itemID,
  };
  $.ajax({
    url: "http://localhost:8081/payment-management-service/API/cartItems",
    type: "DELETE",
    async: true,
    data: JSON.stringify(reqbody),
    processData: false,
    contentType: "application/json; charset=UTF-8",
    complete: function (response, status) {
      onDelete(response.responseText, status);
    },
  });
  return false;
});

//push item to form from list
$(document).on("click", 'button[name="pushToForm"]', function (event) {
  try {
      $("#updateitem").attr("id","sumbitItem");
  } catch (error) {
    console.log(error)
  }

  event.stopPropagation();
  event.stopImmediatePropagation();
  var itemID = $(this).data("itemnumber");

  $('#singleItemNumber').html(`Item - ${itemID}`);
  $('#itemQuantity').val('1');
  $('#itemID').val(itemID);
});


// submit item
function onSubmit(response, status) {
  var obj = JSON.parse(response);
  if (obj.status == "done") {
    bringCartItemHandler();
    $('#singleItemNumber').html(`Select another`);
	makeNotification(`<span class="badge alert-secondary ">inserted</span>`);
    $('#itemQuantity').val('');
    $('#itemID').val('');
    return true;
  } else {
    return false;
  }
}

$(document).on("click", '#sumbitItem', function (event) {
  event.stopPropagation();
  event.stopImmediatePropagation();
  var x = document.cookie;
  var res = x.split("#");
  var key = res[1];
  var uid = res[0];
  var itemID = $('#itemID').val();
  if(itemID==""){return false}
  var quantity = $('#itemQuantity').val();
  if(quantity == "0" || quantity == ""){return false}

  var reqbody = {
    'key': key,
    'user_id': uid,
    'product_id': itemID,
    'quantity':quantity
  };
  $.ajax({
    url: "http://localhost:8081/payment-management-service/API/cartItems",
    type: "POST",
    async: true,
    data: JSON.stringify(reqbody),
    processData: false,
    contentType: "application/json; charset=UTF-8",
    complete: function (response, status) {
      onSubmit(response.responseText, status);
    },
  });
  return false;
});


// update item

$(document).on("click", 'button[name="btnUpdateSingle"]', function (event) {
  $("#sumbitItem").attr("id","updateitem");
  var itemID = $(this).data("itemid");
  var qty = $(this).closest("tr").find('td:eq(2)').text();
  $("#itemQuantity").val(qty);
  $("#itemID").val(itemID);
  $("#singleItemNumber").html("update Item-"+itemID);
});

//temp del

function onUpdateStep1(response, status) {
  var obj = JSON.parse(response);
  if (obj.status == "done") {
    bringCartItemHandler();
    console.log("clicked2");
    return true;
  } else {
    return false;
  }
}


$(document).on("click", '#updateitem', function (event) {
  event.stopPropagation();
  event.stopImmediatePropagation();
  var x = document.cookie;
  var res = x.split("#");
  var key = res[1];
  var uid = res[0];
  console.log("clicked1");
  var itemID = $("#itemID").val();
  var reqbody = {
    "key": key,
    "user_id": uid,
    "product_id": itemID
  };
  $.ajax({
    url: "http://localhost:8081/payment-management-service/API/cartItems",
    type: "DELETE",
    async: true,
    data: JSON.stringify(reqbody),
    processData: false,
    contentType: "application/json; charset=UTF-8",
    complete: function (response, status) {
      if(onUpdateStep1(response.responseText, status))
      {
        onUpdateStep2(event);
      }
    },
  });
  return false;

});


function onSubmitupdate(response, status) {
  var obj = JSON.parse(response);
  if (obj.status == "done") {
    bringCartItemHandler();
    $('#singleItemNumber').html(`Select another`);
	makeNotification(`<span class="badge alert-secondary ">updated</span>`);
    $('#itemQuantity').val('');
    $('#itemID').val('');
    return true;
  } else {
    return false;
  }
}
//
function onUpdateStep2(event) {
  event.stopPropagation();
  event.stopImmediatePropagation();
  var x = document.cookie;
  var res = x.split("#");
  var key = res[1];
  var uid = res[0];
  var itemID = $('#itemID').val();
  var quantity = $('#itemQuantity').val();

  var reqbody = {
    'key': key,
    'user_id': uid,
    'product_id': itemID,
    'quantity':quantity
  };
  $.ajax({
    url: "http://localhost:8081/payment-management-service/API/cartItems",
    type: "POST",
    async: true,
    data: JSON.stringify(reqbody),
    processData: false,
    contentType: "application/json; charset=UTF-8",
    complete: function (response, status) {
      onSubmitupdate(response.responseText, status);
    },
  });
  return false;
};


//notification
function makeNotification(content){
	 $('#notification').html(content);
}

/////profile///////////
//change un pw
function onUpdateProfile(response, status) {
  var obj = JSON.parse(response);
  if (obj.status == "done") {
	    var x = document.cookie;
	    var res = x.split("#");
		var key = res[1];
		var uid = res[0];
		logOut(key,uid); 
		
		return true;
  } else {
    return false;
  }
}

var profileUpdateClickHandler = function (e) {
	e.stopImmediatePropagation();
  var x = document.cookie;
  var res = x.split("#");
  var key = res[1];
  var uid = res[0];
  var str_un = $("#profileUsername").val();
  var str_pw = $("#profilePassword").val();
  jdata = {
    "key":key,
    "user_id":uid,
	"username":str_un,
	"password":str_pw
};

  if (isEmpty(jdata["username"], jdata["password"])) {
    $.ajax({
      url: "http://localhost:8081/authentication-service/API/credentials",
      type: "PUT",
      async: true,
      data: JSON.stringify(jdata),
      processData: false,
      contentType: "application/json; charset=UTF-8",
      complete: function (response, status) {
        onUpdateProfile(response.responseText, status);
      },
    });
    return false;
  }
};
$("#profileUpdate").one("click", profileUpdateClickHandler);

//logout
function logOut(key,id){
	jdata = {
    "key":key,
	"user_id":id
};

    $.ajax({
      url: "http://localhost:8081/authentication-service/API/session",
      type: "DELETE",
      async: true,
      data: JSON.stringify(jdata),
      processData: false,
      contentType: "application/json; charset=UTF-8",
      complete: function (response, status) {
		document.cookie="";
        window.location.href = "Login.jsp";
		
      },
    });
    return false;
  
}
//----validations------

//password
//username
$("#profilePassword  , #profileUsername").change(function() {
	//
		var str = $("#profileUsername").val();
	if(str == ""){
		
		$("#profileUpdate").prop('disabled', true);
	}
	else{
		$("#profileUpdate").prop('disabled', false);
	}
	//
    var str = $("#profilePassword").val();
	if(str.length < 8 || str == ""){
		
		$("#profileUpdate").prop('disabled', true);
	}
	else{
		$("#profileUpdate").prop('disabled', false);
	}
	//

	
});


//for number
$('#itemQuantity').change(function() {
    var qty = $("#itemQuantity").val();
	if(qty >100 || qty < 1){
		
		$("#sumbitItem").prop('disabled', true);
	}
	else{
		$("#sumbitItem").prop('disabled', false);
	}
	
	
});