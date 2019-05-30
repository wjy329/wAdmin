layui.config({
	base : '/js/modules/'
}).use([ 'form', 'layer', 'jquery' ], function() {
	
	var $ = layui.jquery;
	var loginUser = $("#loginUser").html();
	
	var htmlStr = '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">'
		+'大兄弟'+loginUser+'，你没有授权<br><br>请联系管理员哟!!!</div>';
	
	     // 示范一个公告层
	      layer.open({
	        type: 1
	        ,title: false // 不显示标题栏
	        ,closeBtn: false
	        ,area: '300px;'
	        ,shade: 0.8
	        ,id: 'LAY_layuipro' // 设定一个id，防止重复弹出
	        ,btn: ['确定']
	        ,btnAlign: 'c'
	        ,moveType: 1 // 拖拽模式，0或者1
	        ,content: htmlStr
	        ,yes: function(){
	           console.log("xx");
	           layer.closeAll();
	           
	          　	
	        }
	      });
	      
});
