layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery;
	
	var userTable = table.render({
	     elem: '#user_list'
	    ,url:'/user/list'
	    ,id:'id'
	    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
	        layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
	      //,curr: 5 //设定初始在第 5 页
	      ,groups: 1 //只显示 1 个连续页码
	      ,first: false //不显示首页
	      ,last: false //不显示尾页
	      
	    },request: {
	    	  pageName: 'page' //页码的参数名称，默认：page
	         ,limitName: 'rows' //每页数据量的参数名，默认：limit
		},
	    response: {
    		  statusCode: 1 //成功的状态码，默认：0
	    }      
	    ,cols: [[
	       {type:'checkbox'}
	      ,{field:'uid', width:'10%', title: 'ID',hidden:true, sort: true}
	      ,{field:'cname', width:'10%', title: '全名称'}
	      ,{field:'username', width:'20%', title: '用户名称', sort: true}
	      ,{field:'status', width:'10%', title: '状态' ,templet: '#checkboxUser', unresize: true}
	      ,{field:'email', width:'20%', title: '邮箱'}
	      ,{fixed:'right', width:250, align:'center', toolbar: '#barDemo'}
	    ]]
	  });
	
	//监听性别操作
	//监听的是checkbox 对象
	form.on('checkbox(lockUser)', function(obj){
		//如果是true
		var status = obj.elem.checked ? 0:1;
		//格式化数据
		var user = {uid:this.value,status:status};
		console.log(user);
		//没有更新成功的情况
		if(!updateUserStatu($,user)){
			 //删除成功后，删除表单数据
			 userTable.reload({url: '/user/list'});
		}
	});
	
	
	  //监听工具条
	  table.on('tool(user_form)', function(obj){
	    var data = obj.data;
	     if(obj.event === 'del'){
	      layer.confirm('真的删除['+data.cname+']用户', function(index){
	    	  layer.close(index);
	    	  if(!delUsers(data.uid)){
	    		  return false;
	    	  }
	    	  obj.del();
	      });
	    } else if(obj.event === 'edit'){

	    	//页面层
			layer.open({
			  type: 2,
			  title:"更新用户",
			  skin: 'layui-layer-rim', //加上边框
			  area: ['500px', '300px'], //宽高
			  content: "/user/update",
			  success: function(layero, index){
				  var body = layer.getChildFrame('body', index);
				  
				  body.find('input[name="username"]').val(data.username);
				  body.find('input[name="cname"]').val(data.cname);
				  body.find('input[name="email"]').val(data.email);
				  body.find('input[name="uid"]').val(data.uid);
				  
				  
			  },end:function(){ //farme销毁后  更新 列表
				//删除成功后，删除表单数据
			    userTable.reload({url: '/user/list'});
			  }
			});
			
	        
	    }else if(obj.event === 'addRole'){
	    	var uid = data.id;
	    	var name = data.username;
	    	
	    	var title = "设定["+name+"]的角色"
	    	//页面层
			layer.open({
			  type: 2,
			  title:title,
			  skin: 'layui-layer-rim', //加上边框
			  area: ['500px', '200px'], //宽高
			  content: "/user/addRole/",
			  success: function(layero, index){
				  var body = layer.getChildFrame('body', index);
				  body.find('input[name="uid"]').val(data.uid);
			  }
			});
	    }
	  });
	  
	//删除用户
	$("#delUsers").click(function(){
		 var checkStatus = table.checkStatus('id'),data = checkStatus.data;
		 
		 if(data.length == 0){
			 layer.msg('请选择用户!!!', {time: 1000, icon:2});
			 return;
		 }
		 
		
		 layer.confirm('真的删除选中用户?', function(index){
			 layer.close(index);
			 
			 var ids = new Array();
			 for(var i=0;i<data.length;i++){
				 ids.push(data[i].uid);
			 }
			 
			 //发送ajax删除数据
			 var idStr = ids.join(",");
			 console.log(idStr);
			 if(!delUsers(idStr)){
				 return false;
			 }
			 
	         //删除成功后，删除表单数据
			 userTable.reload({url: '/user/list'});
		    
	      });
		 
	});
	

	//搜索用户
	$("#keyWords").bind('input porpertychange',function(obj){
		var keywords = $("#keyWords").val();
		
		//删除成功后，删除表单数据
	    userTable.reload(
	    	{page: {
	            curr: 1 //重新从第 1 页开始
	        }
	        ,url: '/user/list/',
	    	where: {username:keywords}}
	   );
	});
	
	
	
	//删除用户信息
	function delUsers(ids){
		var flag = false;
		//数据库里面检查
		var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.1});
		$.ajax({  
			type : "get",  //使用提交的方法 post、get
		    url : "/user/delete/"+ids,   //提交的地址
		    async : false,   //配置是否异步操作
		    dataType:"json",//返回数据类型的格式
		}).done(function (result) {//回调操作
			  layer.close(index);
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
	
});

//更新用户信息
function updateUserStatu($,data){
	var flag = false;
	//数据库里面检查
	var index = layer.msg('更新中，请稍候',{icon: 16,time:false,shade:0.1});
	$.ajax({  
		type : "post",  //使用提交的方法 post、get
	    url : "/user/update/status",   //提交的地址
	    data:data,
	    async : false,   //配置是否异步操作
	    dataType:"json",//返回数据类型的格式
	}).done(function (result) {//回调操作
		  layer.close(index);
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



	