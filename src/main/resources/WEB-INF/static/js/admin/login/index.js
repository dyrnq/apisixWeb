//var bcrypt = dcodeIO.bcrypt;

function login() {
//	$("#authCode").val($("#codeInput").val());

//	var name = $("#name").val();
//	var pass = $("#pass").val();
    var name = Base64.encode(Base64.encode($("#name").val()));
    var pass = Base64.encode(Base64.encode($("#pass").val()));
//	var code = Base64.encode(Base64.encode($("#code").val()));
//	var authCode = Base64.encode(Base64.encode($("#authCode").val()));

//    var salt = bcrypt.genSaltSync(12);
//    var hashPass = bcrypt.hashSync(pass, salt);
//    console.log(hashPass);



	$.ajax({
		type: 'POST',
		url: ctx + '/token/getToken',
		data: {
			name : name,
			pass : pass
		},
		dataType: 'json',
		success: function(data) {
			if (data.code == 200) {
                Cookies.set(COOK_NAME.token, data.data, { expires: 1, path: '/' })
                location.href = ctx + "/admin";
			} else {
				layer.msg(commonStr.errorInfo);
				//refreshCode('codeImg');
			}
		},
		error: function() {
			layer.alert(commonStr.errorInfo);
		}
	});
}

function getKeyCode() {
	if (event.keyCode == 13) {
		login();
	}
}
