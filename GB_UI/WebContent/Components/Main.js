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

var loginClickHandler = function(e){
  var str_un = $("#txt_login_un").val();
  var str_pw = $("#txt_login_pw").val();
  item = {};
  item["username"] = str_un;
  item["password"] = str_pw;

  if (isEmpty(item["username"], item["password"]) == 1) {
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

}

$('#btn_login').one('click', loginClickHandler);


//add item to cart

