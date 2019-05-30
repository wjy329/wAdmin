layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery;
	
	var rid = $("input[name='rid']").val();

    var t = $("#tree");
    var zNodes ;
    var setting = {
        treeId:"applicationTree",
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
		check:{
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps"}
		},
        data: {
            simpleData: {
                enable:true
            }
        }
    };

	// 监听提交
	form.on('submit(addRoleFrom)', function(data) {
		
		
		var rid = data.field.rid;
		var nodes = t.getCheckedNodes(true);
		var pids = new Array();
		if(nodes.length === 0){
            layer.msg("请勾选一个数据吧", {time: 1000, icon:2});
			return;
		}
        for(var i=0;i<nodes.length;i++){
        	pids.push(nodes[i].id)
        }
		var jsonData = {"pids":pids.join(","),"rId":rid};

		if(!addMenus(jsonData)){
			return ;
		}

		
		//提交到后台
		
		//关闭当前的frame
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭   
	});	
	
	//创建树对象
	createTree();
	//根据角色rid加载复选框
	checkData(rid);

	//创建树
	function createTree(){
        //页面加载
        $(document).ready(function(){
            $.ajax({
                type:'post',
                url: "/menu/treeData",
                dataType: "json",
                async: false,
                success: function (result) {
                    if(result.code == "1"){
                        zNodes = result.data;
                        t = $.fn.zTree.init(t, setting, zNodes);
                    }else{
                        //layer.msg(result.info, {time: 1000, icon:2});
                        console.log("error");
                    }

                },
                error: function (msg) {
                }
            });
        });

	}

    //加载复选框
    function checkData(rid){
        //页面加载
        $(document).ready(function(){
            $.ajax({
                type:'get',
                url: "/menu/sel/"+rid,
                dataType: "json",
                async: false,
                success: function (result) {
                    if(result.code == "1"){
                        zNodes = result.data;
                        for(var i=0;i<zNodes.length;i++){
                            t.checkNode(t.getNodeByParam("id", zNodes[i].pId,null),true, false);
                        }
                    }else{
                        layer.msg(result.info, {time: 1000, icon:2});
                    }

                },
                error: function (msg) {
                }
            });
        });

    }
//------------------------------------------------------
//获取选中的菜单id
//------------------------------------------------------
function getSelTree(rid){
	var data = null;
	var requestUrl= "/menu/sel/"+rid;
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
	
//------------------------------------------------------
//获取菜单信息
//------------------------------------------------------	
function getTree(){
	var data = null;
	var requestUrl= "/menu/tree/";
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
	
//------------------------------------------------------
//添加 menu的权限
//------------------------------------------------------	
function addMenus(jsonData){
	var flag = false;
	var requestUrl = "/role/addMenu";
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
			layer.msg(result.info, {time: 1000, icon:1});
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
//获取用户的角色
function getRolePerm($,id){
	var data = null;
	var requestUrl= "/role/perm/"+id;
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
function addPerm($,id,jsonData){
	var flag = false;
	var requestUrl = "/role/addPerm";
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
			layer.msg(result.info, {time: 1000, icon:1});
		}else{
			layer.msg(result.info, {time: 1000, icon:2});
		}
		
	});
	return flag;
}
