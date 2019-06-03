layui.config({
	  base: '/js/modules/' 
}).use(['form','layer','table','jquery','laypage','util'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		table = layui.table,
		$ = layui.jquery,
        util = layui.util;
	
	var tableTable = table.render({
	     elem: '#table_table'
        ,toolbar: '#toolbarDemo'
	    ,url:'/generator/list'
	    ,id:'tableName'
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
	      ,{field:'tableName',title: '表名'}
	      ,{field:'engine', title: 'Engine', sort: true}
	      ,{field:'tableComment', title: '表备注'}
	      ,{field:'createTime', title: '创建时间',templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
	    ]]
	  });

    //监听头部工具条事件
    table.on('toolbar(table_table)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id),data = checkStatus.data;

        switch(obj.event){
            case 'generator':
                console.log(obj);
                generatorCode(data);
                break;
        }
    });


    function generatorCode(data) {
        if(data.length == 0){
            layer.msg('请至少选择一条数据', {time: 1000, icon:2});
            return;
        }
        var tableNames = new Array();
        for(var i=0;i<data.length;i++){
            tableNames.push(data[i].tableName);
        }

        //发送ajax删除数据
        var tableName = tableNames.join(",");

        location.href = "/generator/code/"+tableName;


    }

});



	