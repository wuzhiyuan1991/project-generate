window.isDebug = false;
window.basePath = '';
function jeach(json, fn) {
    //fn 回调函数
    var index = 0;

    //对 json 对象遍历 取出 key 和 val
    for (var key in json) {
        //获取 json  val
        var val = json[key];
        //判断 fn 是否是函数
        if (typeof fn == "function") {
            // 回调函数返回结果  r 接收(result)
            var r = fn(key, val, index);
            // 返回值有两种
            // 1. true | false
            // 2. json格式对象 {$:*,s:*,t:*,val:*,k.*} *代表变量
            index++;
            //判断 r 是否有返回结果，没有 不处理
            if (r == undefined || r == false || r == null) {
                continue;
            }
            //返回有结果
            //  r.t   （type） 要处理的html标签  例如 return {t:'input,img,select'} 默认即可，已经自动处理好了
            //  r.s   （selector） jquery 选择器 默认为 class 选择器 可以传入   return {s:'#'} id 选择器
            //  r.val （value） 如果返回将替换 遍历json 的默认值，通常处理图片用的  return {val:imgProject+v}
            //  r.k   （key）返回的 新的key ,例如 json 返回默认是 name 修改  return {k:'username'}  <input type='text' class='username'/> //自动赋值
            // 默认  <input type='text' class='name'/>
            // r.k 和 r.val 根据自己的需要，不需要改变值 默认即可

            //  r.$   （parent Node） 模板   为了避免冲突 修改 为 _$

            var t = r.t;
            var s = r.s;
            var rv = r.val;
            var k = r.key;
            var _$ = r.$;  //  r.$

            //如果 r.$ 没有返回 默认 为 $("*"); 相当于 $('body')
            if (_$ == undefined) {
                _$ = $("*");
            }
            //如果 r.val 没有新的值，默认遍历的 json.val
            if (rv != undefined) {
                val = rv;
            }
            //如果 r.k 没有新的值，默认遍历的 json.key
            if (k != undefined) {
                key = k;
            }
            //选择器为空 默认是 class  .
            if (s == undefined) {
                s = ".";
            }
            /**
             * 例如一段html  <div>  <span class='name'> </span> <span class='money'> </span> </div>
             * json 格式如下 {name:'abc',money='1000',name:'ddd',money='1000'}
             *
             * 当前遍历的json  key = name  val = 'abc'
             * _$  =  div
             * key = 'name'
             * val = 'abc'
             *
             * s + key = '.' +'name' 处理结果如下：
             * $('div').find('.name').text('abc');
             *
             */

            //获取 节点名称 例如 name 等于  input,img,div,span ......等等
            // 根据 节点名称不同 赋值方式不同  div 赋值 text ，input 赋值 value
            var name = _$.find(s + key)[0] == undefined ? "" : _$.find(s + key)[0].nodeName.toUpperCase();
            var obj = _$.find(s + key);// class 选择器
            var obj2 = _$.find(key);// 标签选择器

            //如果返回 结果是 字符类型 $= string  return {$:'abc'} 直接赋值
            if (typeof r == "string") {
                $("." + key).text(r);
                $(key).text(r);
            } else if (typeof r == "object") {// json 类型

                //判断 如果是否自定义属性 , 例如  ${attr:'href'}       $('div').find('.name').attr('href','abc')
                if (r.attr) {
                    obj.attr(r.attr, val);
                    continue;
                }
                //根据不同的标签 赋值
                if (t == undefined) {
                    $.each(_$.find(s + key),
                        function (i, v) {
                            name = $(v)[0] == undefined ? "" : $(v)[0].nodeName.toUpperCase();
                            if (isNaN(val) == false) {
                                $(v).eq(0).css('font-family', '微软雅黑,Source Sans Pro,Helvetica Neue,Helvetica,Arial,sans-serif');
                            }
                            if (name == "IMG") {
                                $(v).eq(0).attr("src", val);
                            }
                            if (name == "INPUT") {
                                $(v).eq(0).val(val);
                                $("[name='" + key + "']").val(val);
                            }
                            if (name != "INPUT" && name != "IMG") {
                                $(v).eq(0).text(val);
                                obj2.text(val);
                            }
                        });
                    continue;
                }
                if (t.indexOf("s") != -1 && name == "IMG") {
                    obj.attr("src", val);
                }
                if (t.indexOf("v") != -1 && (name == "INPUT") || name.toUpperCase() == "SELECT") {
                    obj.val(val);
                }
            } else if (r == true) { // 返回 boolean 类型
                if (isNaN(val) == false) {
                    obj.css('font-family', '黑体,微软雅黑,Source Sans Pro,Helvetica Neue,Helvetica,Arial,sans-serif');
                }
                if (name == "IMG") {
                    obj.attr("src", val);
                }
                if (name == "INPUT") {
                    obj.val(val);
                    $("[name='" + key + "']").val(val);
                }
                if (name != "INPUT" && name != "IMG") {
                    obj.text(val);
                    obj2.text(val);
                }
            }
        } else if (fn == true) { // 默认处理
            $("." + key).text(val);
            $(key).text(val);
        }
    }
}


function ajax(params, url, callback, _async, _type) {
	
    log('params',params)
    log('url',url)
    if (url == undefined) {
        return;
    } else {
        url = basePath + url;
    }
    var data = {};
    var error = function () {
        if (typeof callback.error == "function") {
            callback.error(msg);
        }
    };
    var success = function () {

    };
    var beforeSend = function () {

    };
    var async = true;
    var dataType = "json";
    var type = "post";
    var fn = "function";
    if (callback == undefined) {
        callback = {};
    }
    if (params == undefined) {
        params = {};
    }
    if (typeof callback == fn) {
        success = callback;
    }
    if (typeof callback.success == fn) {
        success = callback.success;
    }
    if (typeof callback.beforeSend == fn) {
        beforeSend = callback.beforeSend;
    }
    if (typeof callback.error == fn) {
        error = callback.error;
    }
    if (callback.async == false) {
        async = callback.async;
    }
    if (callback.dataType) {
        dataType = callback.dataType;
    }
    if (callback.type) {
        type = callback.type;
    }
    if (typeof params == "object") {
        $.each(params,
            function (key, val) {
                if (val != undefined && val != null) {
                    data[key] = val;
                }
            });
    } else {
        return;
    }
    if (_async == false) {
        async = _async;
    }
    if (_type) {
        dataType = _type;
    }
    try {
        $.ajax({
            url: url,
            type: type,
            data: data,
            dataType: dataType,
            async: async,
            error: function (msg) {
                log('err',msg)
                error(msg);
            },
            success: function (json) {
                log('result',json)
                success(json);
            }
        });
    } catch (e) {
        console.log(e)
    }
};


function  log(prefix,msg){
    prefix = prefix||'log';
    if(isDebug){
        console.log(prefix + ' : '+(typeof msg =='string' ?msg:''));
        if(typeof msg == 'object'){
            console.log(msg);
        }
    }
};

// 访问controller包的基本路径
function getbasePath() {
	return "/";
}

// 页面跳转
function toPage(url) {
	window.location.href = url;
}

// 按钮设置可用
function available(buttonId){
	$('#' + buttonId).attr('disabled',false);
}

// 按钮设置不可用
function disabled(buttonId){
	$('#' + buttonId).attr('disabled',true);
}

// 写cookies，浏览器关闭失效
function setCookie(name,value)
{
	document.cookie = name + "="+ escape (value);
}

// 获取cookie
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
