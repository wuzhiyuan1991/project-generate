$(function(){
	// 判断是否登录
	checkLogin();
	
	// 创建项目
	createProject();
});

function test(){
	$("#groupid").val("com.ueb.listing"); // 项目组
	$("#artifactid").val("ueb-listing"); // 项目名称
	$("#pkg").val("com.ueb.listing"); // 项目主包
	$("#port").val("8902"); // 项目端口
	$("#dubboport").val("22902"); // dubbo端口
	$("#restport").val("9302"); // dubbo rest端口
	$("#prjectid").val("0000"); // 项目ID
}

// 判断是否登录
function checkLogin(){
	if(!getCookie("uname")){
		// 跳转登录页面
		toPage("/html/login.html");
	}
}


//保存
function createProject(){
	$("#btn").click(function(){
		var groupid = $("#groupid").val(); // 项目组
		var artifactid = $("#artifactid").val(); // 项目名称
		var pkg = $("#pkg").val(); // 项目主包
		var port = $("#port").val(); // 项目端口
		var dubboport = $("#dubboport").val(); // dubbo端口
		var restport = $("#restport").val(); // dubbo rest端口
		var prjectid = $("#prjectid").val(); // 项目ID
		
		if(groupid == null || groupid == ""){
			alert('项目组为空');
			return;
		}
		if(artifactid == null || artifactid == ""){
			alert('项目名称为空');
			return;
		}
		if(pkg == null || pkg == ""){
			alert('项目主包为空');
			return;
		}
		if(port == null || port == ""){
			alert('项目端口为空');
			return;
		}
		if(dubboport == null || dubboport == ""){
			alert('dubbo端口为空');
			return;
		}
		if(restport == null || restport == ""){
			alert('dubbo rest端口为空');
			return;
		}
		if(prjectid == null || prjectid == ""){
			alert('项目ID为空');
			return;
		}
		
		// http://localhost:9100/project/create/ueb-listing.zip?groupid=com.ueb.listing&pkg=com.ueb.listing&port=8902&dubboport=22902&restport=9302&prjectid=0000
		// http://localhost:9100/project/create/{artifactid}.zip?groupid=&pkg=&port=&dubboport=&restport=&prjectid=
		var params = {groupid : groupid, pkg : pkg, port : port, dubboport : dubboport, restport : restport, prjectid : prjectid};
		var url = getbasePath() + "project/create/" + artifactid + ".zip?groupid=" + groupid + "&pkg=" + pkg + "&port=" + port + "&dubboport="
				+ dubboport + "&restport=" + restport + "&prjectid=" + prjectid; // 生成url
		$("#url").attr("href",url);
		$("#url")[0].click(); // 模拟点击事件
	});
}