//TODO  这个是菜单入口，后期将菜单的配置信息存储到数据库
var navs = null;
layui.use(['layer','jquery'],function(){
		var layer = parent.layer === undefined ? layui.layer : parent.layer,
		$ = layui.jquery;
		
		//获取菜单信息
		navs = getMenu($);
});

//获取菜单信息
function getMenu($){
	var data = null;
	var requestUrl= "/menu/navs/";
	//数据库里面检查
	$.ajax({  
	    type : "get",  //使用提交的方法 post、get
	    url : requestUrl,   //提交的地址
	    async : false,   //配置是否异步操作
	    dataType:"json",//返回数据类型的格式
	}).done(function ( result ) {//回调操作
		if(result.code == "1"){
			data = result.data;
		}else{
			layer.msg(result.info, {time: 1000, icon:2});
		}
	});
	return data;
}
