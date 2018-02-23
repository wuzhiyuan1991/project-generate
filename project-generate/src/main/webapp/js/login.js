$(function(){
	// 初始化
	init();
	
	// 读取cookie
	initCookieValue();
	
	// 登录事件
	login();
});


//初始化
function init(){
	$userTip = $('#uin_tips'), $passTip = $('#pwd_tips'), $operateTip = $('#operate_tips'), $delUserInput = $('#uin_del'), $u = $('#u'), $p = $('#p'), $switch = $('#qrswitch_logo'),
	$capsLock = $('#caps_lock_tips'), $login = $('#login_button'), $errTip = $('#error_tips'), $qr = $('#web_qr_login_show');
	
	$('#u').add($('#p')).bind({
        'focus': function () {
            var $this = $(this), $currTip = $this.attr('id') === 'u' ? ($operateTip.show(), $userTip) : $passTip;
            $this.parent().css('background-position-y', '-45px');
            $currTip.css('color', '#ddd');
        },
        'blur': function () {
            var $this = $(this), $currTip = $this.attr('id') === 'u' ? ($operateTip.hide(), $userTip) : $passTip;
            $this.parent().css('background-position-y', '-1px');
            $currTip.css('color', '#aaa');
            if ($currTip === $passTip && $capsLock.is(':visible')) { $capsLock.hide(); }
        },
        'input': function (e) {
            var $this = $(this), $currTip = $this.attr('id') === 'u' ? $userTip : $passTip;
            if ($this.val()) {
                if ($currTip.is(':visible')) {
                    $currTip.hide();
                    if ($currTip === $userTip) { $delUserInput.show(); }
                }
            } else {
                $currTip.show();
                if ($currTip === $userTip) { $delUserInput.hide(); }
            }
        }
    });
	
	$login.click(function () {
        if (!$u.val()) {
            $errTip.find('#err_m').text('您还没有输入账号！').end().show();
            setTimeout(function () { $errTip.hide(); }, 3000);
            return false;
        } else if (!$p.val()) {
            $errTip.find('#err_m').text('您还没有输入密码！').end().show();
            setTimeout(function () { $errTip.hide(); }, 3000);
            return false;
        } else {
        	$errTip.hide();
        	$delUserInput.hide();
        	$u.attr('disabled',true);
        	$p.attr('disabled',true);
            $('#loading_tips').show();
        }
    });
	
	$delUserInput.click(function () { $u.val('').focus(); $userTip.show(); $delUserInput.hide(); });
}


// 绑定登录事件
function login(){
	$(".login_button").click(function(){
		var uname = $("#u").val(); // 账号
		var passwd = $("#p").val(); // 密码
		
		var params = {username : uname, password : passwd};
		var url = getbasePath() + "login";
		
		$.ajax({ 
		     type : "GET", //提交方式 
		     url : url,//路径 
		     data : params,//数据，这里使用的是Json格式进行传输 
		     success : function(result) {//返回数据根据结果进行相应的处理 
		    	 debugger;
		    	 if(result="success"){
		    		 setCookie("uname",$("#u").val()); // 登录成功，保存cookie
			    	 toPage("/html/index.html");
		    	 }
		     } 
		});
	});
}


//读取cookie
function initCookieValue(){
//	if($("#u").val() != null && $("#u").val != ''){
//		$('#uin_del').show();
//		$('#uin_tips').hide();
//	}
	if(getCookie("uname") != null && getCookie("uname") != ''){
		$('#remember').attr("checked", true);
		$("#u").val(getCookie("uname")); // 设置账号
		$('#uin_del').show();
		$('#uin_tips').hide();
		if(getCookie("passwd") != null && getCookie("passwd") != ''){
			$("#p").val(getCookie("passwd")); // 设置密码
			$('#pwd_tips').hide();
		}
	}
}

