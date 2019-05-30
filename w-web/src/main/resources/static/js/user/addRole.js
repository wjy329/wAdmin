layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery;
	
	var id = $("input[name='uid']").val();
	
	//获取到角色的数据
	var data = getUserRole(id);
	//设定checkbox
	var checkHtml= getRoleInfo(data);
	$("#userRoles").html(checkHtml);
	
	//重新渲染一下
	form.render();
	
//	<input th:value="${role.value}" th:title="${role.name}"  type="checkbox" th:each="role,index: ${roles}"/>
	// 监听提交
	form.on('submit(addRoleFrom)', function(data) {
		
		//获取到选中的
		var roleIds = new Array();
		var checkeds = $(".layui-form-checked");
		for(var i=0;i<checkeds.length;i++){
			var key = $(checkeds[i]).find("span").html();
			var val = $("input[title='"+key+"'").val();
			roleIds.push(val);
		}
		
		
		
		var jsonData = {"userId":id,"roleIds":roleIds.join(",")};
		//提交到数据库
		var flg = addRole(id,jsonData);
		if(!flg){
			return false;
		}
	
		//关闭当前的frame
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭   
	});	
	
	//获取用户的角色
	function getUserRole(id){
		var data = null;
		var requestUrl= "/user/role/"+id;
		//数据库里面检查
		$.ajax({  
		    type : "get",  //使用提交的方法 post、get
		    url : requestUrl,   //提交的地址
		    async : false,   //配置是否异步操作
		    dataType:"json",//返回数据类型的格式
		}).done(function ( result ) {//回调操作
			if(resultData.requestOk(result)){
				data = result.data;
			}else{
				layer.msg(result.info, {time: 1000, icon:2});
			}
		});
		return data;
	}

	
	//-----------------------------------------------------------
	//添加job服务到服务器
	//-----------------------------------------------------------
	function addRole(id,jsonData){
		var flag = false;
		var requestUrl = "/user/addRole";
		//数据库里面检查
		$.ajax({  
		    type : "post",  //使用提交的方法 post、get
		    url : requestUrl,   //提交的地址
		    data:jsonData,
		    async : false,   //配置是否异步操作
		    dataType:"json",//返回数据类型的格式
		}).done(function ( result ) {//回调操作
			//
			if(resultData.requestOk(result)){
				flag = true;
				layer.msg("角色更新成功，重新登录后生效!!!", {time: 1000, icon:1});
			}else{
				layer.msg(result.info, {time: 1000, icon:2});
			}
		});
		return flag;
	}
});
//获取角色信息
function getRoleInfo(data){
	var checkHtml = "";
	for(var i=0;i<data.length;i++){
		var label = data[i];
		if(label.checked){
			checkHtml +='<input value="'+label.value+'" checked="" title="'+label.name+'" type="checkbox"/>';
		}else{
			checkHtml +='<input value="'+label.value+'" title="'+label.name+'" type="checkbox"/>';
		}
	}
	return checkHtml;
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
	