layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage','iconPicker'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery,
        iconPicker = layui.iconPicker;

    iconPicker.render({
        // 选择器，推荐使用input
        elem: '#iconPicker',
        // 数据类型：fontClass/unicode，推荐使用fontClass
        type: 'unicode',
        // 是否开启搜索：true/false
        search: true,
        // 是否开启分页
        page: true,
        // 每页显示数量，默认12
        limit: 12,
        // 点击回调
        click: function (data) {
            console.log(data);
        },
        // 渲染成功后的回调
        success: function(d) {
            console.log(d);
        }
    });
	
	// 监听提交
	form.on('submit(updateMenuFrom)', function(data) {
		var jsonData = data.field;
		
		console.log(jsonData);
		//提交到数据库
		var flg = updateMenu($,jsonData);
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
function updateMenu($,jsonData){
	var flag = false;
	//数据库里面检查
	$.ajax({  
	    type : "post",  //使用提交的方法 post、get
	    url : "/menu/update",   //提交的地址
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
