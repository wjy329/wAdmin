layui.config({
    base: '/js/modules/'
}).use(['form','layer','table','jquery','laypage','tree'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        table = layui.table,
        tree = layui.tree,
        $ = layui.jquery;

    //创建树
    createTree();

    //zTree参数设置
    //加载资源树
    var t = $("#tree");
    var zNodes ;
    var setting = {
        treeId:"applicationTree",
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        edit: {
            drag: {
                autoExpandTrigger: true,
                prev: dropPrev,
                inner: dropInner,
                next: dropNext
            },
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
//                key:{
//                    name:"applicationName"
//                },
            simpleData: {
                enable:true
            }
        },
        callback: {
            onClick: zTreeOnClick,
            beforeDrag: beforeDrag, //拖拽前：捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
            beforeDrop: beforeDrop, //拖拽中：捕获节点操作结束之前的事件回调函数，并且根据返回值确定是否允许此拖拽操作
            beforeDragOpen: beforeDragOpen, //拖拽到的目标节点是否展开：用于捕获拖拽节点移动到折叠状态的父节点后，即将自动展开该父节点之前的事件回调函数，并且根据返回值确定是否允许自动展开操作
            onDrag: onDrag, //捕获节点被拖拽的事件回调函数
            onDrop: onDrop, //捕获节点拖拽操作结束的事件回调函数
            onExpand: onExpand //捕获节点被展开的事件回调函数
        }
    };

    /*//删除成功后，删除表单数据
   */
    //渲染表格
    var menuTable = table.render({
        elem: '#job_menu'
        ,url:'/menu/tableList'
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
            {field:'pid',  title: 'ID', sort: true}
            ,{field:'title', title: '菜单名称'}
            ,{field:'href',  title: '链接地址'}
            ,{field:'code',  title: '权限标识'}
            ,{field:'type', title: '菜单类型',templet: "#menuType"}
            ,{field:'orders',  title: '菜单排序'}
            ,{field:'icon', title: '菜单图标' ,templet: "#iconTpl" }
            ,{fixed:'right',  align:'center', toolbar: '#barDemo'}
        ]]
    });

    //监听工具条
    table.on('tool(menu_form)', function(obj){
        var data = obj.data;
        if(obj.event === 'edit'){

            //开启添加界面
            layer.open({
                type: 2,
                title:"菜单添加",
                skin: 'layui-layer-rim', //加上边框
                area: ['500px', '350px'], //宽高
                content: "/menu/update",
                success: function(layero, index){
                    var body = layer.getChildFrame('body', index);
                    //id
                    body.find('input[name="pid"]').val(data.pid);
                    //标题
                    body.find('input[name="title"]').val(data.title);
                    //连接
                    body.find('input[name="href"]').val(data.href);
                    body.find('input[name="code"]').val(data.code);
                    body.find("input[name='type'][value='" + data.type +"']").prop('checked',true);
                    body.find('input[name="orders"]').val(data.orders);
                    body.find('input[name="icon"]').val(data.icon);
                    form.render();
                },end:function(){ //farme销毁后  更新 列表
                    var t = $("#tree");
                    $.ajax({
                        type:'post',
                        url: "/menu/treeData",
                        dataType: "json",
                        async: false,
                        success: function (result) {
                            if(result.code == "1"){
                                t = $.fn.zTree.init(t, setting, result.data);
                            }else{
                                //layer.msg(result.info, {time: 1000, icon:2});
                                console.log("error");
                            }

                        },
                        error: function (msg) {
                        }
                    });
                }
            });

        }
    });



    function zTreeOnClick(treeId, nodes, treeNode) {
        //alert(treeNode.tId + ", " + treeNode.name+"treeId"+treeNode.pId);
        menuTable.reload({
            url: '/menu/getTableData'
            ,where: {id:treeNode.id}
        });
    }
    function dropPrev(treeId, nodes, targetNode) {
        var pNode = targetNode.getParentNode();
        if (pNode && pNode.dropInner === false) {
            return false;
        } else {
            for (var i=0,l=curDragNodes.length; i<l; i++) {
                var curPNode = curDragNodes[i].getParentNode();
                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }
    function dropInner(treeId, nodes, targetNode) {
        if (targetNode && targetNode.dropInner === false) {
            return false;
        } else {
            for (var i=0,l=curDragNodes.length; i<l; i++) {
                if (!targetNode && curDragNodes[i].dropRoot === false) {
                    return false;
                } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }
    function dropNext(treeId, nodes, targetNode) {
        var pNode = targetNode.getParentNode();
        if (pNode && pNode.dropInner === false) {
            return false;
        } else {
            for (var i=0,l=curDragNodes.length; i<l; i++) {
                var curPNode = curDragNodes[i].getParentNode();
                if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                    return false;
                }
            }
        }
        return true;
    }

    var log, className = "dark", curDragNodes, autoExpandNode;
    function beforeDrag(treeId, treeNodes) {
        className = (className === "dark" ? "":"dark");
        for (var i=0,l=treeNodes.length; i<l; i++) {
            if (treeNodes[i].drag === false) {
                curDragNodes = null;
                return false;
            } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
                curDragNodes = null;
                return false;
            }
        }
        curDragNodes = treeNodes;
        return true;
    }
    function beforeDragOpen(treeId, treeNode) {
        autoExpandNode = treeNode;
        return true;
    }
    function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
        className = (className === "dark" ? "":"dark");
        return true;
    }
    function onDrag(event, treeId, treeNodes) {
        className = (className === "dark" ? "":"dark");
    }
    function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
        className = (className === "dark" ? "":"dark");
        //拖拽结束之后
        /*alert("拖拽节点的id:"+treeNodes[0].id);
        alert("拖拽节点的新父节点的id:"+targetNode.id);*/
        //更新拖拽节点的父级目录id
        $.ajax({
            type:'post',
            url: "updateApplicationTree?id="+treeNodes[0].id+"&applicationParentId="+targetNode.id,
            dataType: "text",
            async: false,
            success: function (data) {
            },
            error: function (msg) {
            }
        });
    }
    function onExpand(event, treeId, treeNode) {
        if (treeNode === autoExpandNode) {
            className = (className === "dark" ? "":"dark");
        }
    }
    function createTree() {
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

    $('#addMenu').click(function (){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length === 0){
            layer.msg("请选中新菜单的父节点！！！", {time: 2000, icon:2});
            return false;
        }

        if(nodes[0].level >1){
            layer.msg("目前二级菜单最多支持添加按钮类型，添加菜单类型无法显示！！！", {time: 2000, icon:2,offset: 'rb'});
        }
        var data = nodes[0];
        //开启添加界面
        layer.open({
            type: 2,
            title:"菜单添加",
            skin: 'layui-layer-rim', //加上边框
            area: ['500px', '450px'], //宽高
            content: "/menu/add",
            success: function(layero, index){
                var body = layer.getChildFrame('body', index);
                //id
                body.find('input[name="pid"]').val(data.id);
                //标题
                body.find('input[name="pName"]').val(data.name);

            },end:function(){ //farme销毁后  更新 列表
                refreshTree();
            }
        });

    });

    $('#delMenu').click(function (){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length === 0){
            layer.msg("请选中删除的节点！！！", {time: 2000, icon:2});
            return false;
        }

        if(nodes[0].id == 0 || nodes[0].id == 1){
            layer.msg("不能删除此节点", {time: 2000, icon:2});
            return;
        }

        var title = "真的删除【"+nodes[0].name+"】菜单吗？";
        //判断是否是父节点
        if(nodes[0].isParent){
            title = "菜单中包含有"+nodes[0].children.length+"个子菜单<br/>"+title;
        }
        layer.confirm(title, function(index){
            layer.close(index);
            var flg = delMenu(nodes[0].id);
            //重新获取树
            if(flg){
                refreshTree();
            }
        });


    });

    //获取菜单信息
    function delMenu(id){
        //删除数据
        var flag = false;
        //数据库里面检查
        var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type : "get",  //使用提交的方法 post、get
            url : "/menu/delete/"+id,   //提交的地址
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

    //自动刷新树
    function refreshTree(){
        var t = $("#tree");
        $.ajax({
            type:'post',
            url: "/menu/treeData",
            dataType: "json",
            async: false,
            success: function (result) {
                if(result.code == "1"){
                    t = $.fn.zTree.init(t, setting, result.data);
                }else{
                    //layer.msg(result.info, {time: 1000, icon:2});
                    console.log("error");
                }

            },
            error: function (msg) {
            }
        });
    }

    var treeData = [];
    $.ajax({
        type:'post',
        url: "/menu/layTreeData",
        dataType: "json",
        async: false,
        success: function (result) {
            if(result.code == "1"){
                treeData = result.data;
            }else{
                //layer.msg(result.info, {time: 1000, icon:2});
                console.log("error");
            }

        },
        error: function (msg) {
        }
    });

  /*  //渲染
    var inst1 = tree.render({
        elem: '#layui-tree',  //绑定元素
        data: treeData,
        expandClick : true,
        click: function(obj){
            menuTable.reload({
                url: '/menu/getTableData'
                ,where: {id:obj.data.id}
            });
        }
    });*/

});



