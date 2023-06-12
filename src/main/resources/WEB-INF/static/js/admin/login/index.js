var bcrypt = dcodeIO.bcrypt;

function login() {
	//$("#authCode").val($("#codeInput").val());

	var name = $("#name").val();
	var pass = $("#pass").val();
	//var code = Base64.encode(Base64.encode($("#code").val()));
	//var authCode = Base64.encode(Base64.encode($("#authCode").val()));

    var salt = bcrypt.genSaltSync(12);
    var hashPass = bcrypt.hashSync(pass, salt);
    //console.log(hashPass);

	$.ajax({
		type: 'POST',
		url: ctx + '/token/getToken',
		data: {
			name : name,
			pass : hashPass
		},
		dataType: 'json',
		success: function(data) {
		    //alert(JSON.stringify(data));
		    //console.log(data);
			if (data.code == 200) {
			//	if($("#remember").prop("checked")){
			//		window.localStorage.setItem("time", new Date().getTime());
			//		window.localStorage.setItem("adminId",data.obj.id);
			//	} else {
			//		window.localStorage.removeItem("adminId");
			//	}

                Cookies.set(COOK_NAME.token, data.data, { expires: 1, path: '/' })

//                var cname="TOKEN"
//                var cvalue=data.data
//                //console.log(cvalue)
//                var exdays=1
//                const d = new Date();
//                d.setTime(d.getTime() + (exdays*24*60*60*1000));
//                let expires = "expires="+ d.toUTCString();
//                document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
                location.href = ctx + "/admin";
			} else {
			    //alert("登陆失败");
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
