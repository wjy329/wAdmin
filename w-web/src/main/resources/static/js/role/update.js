layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery;
	
	// 监听提交
	form.on('submit(updateRoleFrom)', function(data) {
		var jsonData = data.field;
		
		//提交到数据库
		var flg = updateRole($,jsonData);
		if(!flg){
			return false;
		}
		
		//关闭当前的frame
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭   
	});	
});
//-----------------------------------------------------------
//添加job服务到服务器
//-----------------------------------------------------------
function updateRole($,jsonData){
	console.log(jsonData);
	var flag = false;
	//数据库里面检查
	$.ajax({  
    type : "post",  //使用提交的方法 post、get
    url : "/role/update",   //提交的地址
    data:jsonData,
    async : false,   //配置是否异步操作
    dataType:"json",//返回数据类型的格式
	}).done(function ( result ) {//回调操作
		//
		if(resultData.requestOk(result)){
			flag = true;
			layer.msg(result.info, {time: 1000, icon:1});
		}else{
			layer.msg(result.info, {time: 1000, icon:2});
		}
		
	});
	return flag;
}
//--------------------------------------------------
//包含了中文字符
//--------------------------------------------------
function hasChines(obj){
	var reg = /^[\u4e00-\u9fa5]/
	for(var i=0;i<obj.length;i++){
		if(reg.test(obj[i])){
			return true;
		}
	}
	return false;
}
	