layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery;
	
	var gridUrl = '/role/list';
	var userTable = table.render({
	     elem: '#role_list'
	    ,url:gridUrl
	    ,id:'rid'
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
	      ,{field:'rid', width:'10%', title: 'ID',hidden:true, sort: true}
	      ,{field:'rname', width:'10%', title: '角色名称'}
	      ,{field:'note', width:'20%', title: '备注'}
	      ,{fixed:'right', width:250, align:'center', toolbar: '#barDemo'}
	    ]]
	  });
	
	  //监听工具条
	  table.on('tool(role_form)', function(obj){
	    var data = obj.data;
	    console.log(data);
	    var roleName = data.note;
	    var info  = '真的删除['+roleName+']角色';
	    if(obj.event === 'del'){
	    layer.confirm(info, function(index){
	    	  layer.close(index);
	    	  if(!delRoles($,data.rid)){
	    		  return false;
	    	  }
	    	  obj.del();
	      });
	    } else if(obj.event === 'edit'){
	    	//页面层
			layer.open({
			  type: 2,
			  title:"更新角色",
			  skin: 'layui-layer-rim', //加上边框
			  area: ['500px', '250px'], //宽高
			  content: "/role/update",
			  success: function(layero, index){
				  var body = layer.getChildFrame('body', index);
				  
				  body.find('input[name="rid"]').val(data.rid);
				  body.find('input[name="rname"]').val(data.rname);
				  body.find('input[name="note"]').val(data.note);
			  },end:function(){ //farme销毁后  更新 列表
				//删除成功后，删除表单数据
			    userTable.reload({url: gridUrl});
			  }
			});
	    }else if(obj.event === 'addPerm'){
	    	var roleName = data.rname;
	    	var titleStr = "设置["+roleName+"]的权限";
	    	//页面层
			layer.open({
			  type: 2,
			  title:titleStr,
			  skin: 'layui-layer-rim', //加上边框
			  area: ['500px', '400px'], //宽高
			  content: "/role/addPerm",
			  success: function(layero, index){
				  var body = layer.getChildFrame('body', index);
				  
				  body.find('input[name="rid"]').val(data.rid);
				  body.find('input[name="rname"]').val(data.rname);
				  body.find('input[name="note"]').val(data.note);
			  }
			});
	     
	    }
	  });
	  
	
	//删除用户
	$("#delRoles").click(function(){
		 var checkStatus = table.checkStatus('rid'),data = checkStatus.data;
		 
		 if(data.length == 0){
			 layer.msg('请选择要删除的角色!!!', {time: 1000, icon:2});
			 return;
		 }
		
		 layer.confirm('真的删除选中角色?', function(index){
			 layer.close(index);
			 
			 var ids = new Array();
			 for(var i=0;i<data.length;i++){
				 ids.push(data[i].rid);
			 }
			 
			 //发送ajax删除数据
			 var idStr = ids.join(",");
			 console.log(idStr);
			 if(!delRoles($,idStr)){
				 return false;
			 }
			 
	         //删除成功后，删除表单数据
			 userTable.reload({url: gridUrl});
	      });
	});
	
	//用户添加处理
	$("#addRole").click(function(){
		//页面层
		layer.open({
		  type: 2,
		  title:"角色添加",
		  skin: 'layui-layer-rim', //加上边框
		  area: ['500px', '250px'], //宽高
		  content: "/role/add",
		  end:function(){ //farme销毁后  更新 列表
			//删除成功后，删除表单数据
		    userTable.reload({url: gridUrl});
		  }
		});
	});
	
	
	//搜索用户
	$("#keyWords").bind('input porpertychange',function(obj){
		var keywords = $("#keyWords").val();
		console.log();
		
		//删除成功后，删除表单数据
	    userTable.reload(
	    	{page: {
	            curr: 1 //重新从第 1 页开始
	        }
	        ,url: gridUrl,
	    	where: {rname:keywords}}
	   );
	});
});


//删除角色信息
function delRoles($,ids){
	var flag = false;
	//数据库里面检查
	var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.1});
	$.ajax({  
		type : "get",  //使用提交的方法 post、get
	    url : "/role/delete/"+ids,   //提交的地址
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

	