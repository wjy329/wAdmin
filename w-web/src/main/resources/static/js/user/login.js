layui.config({
    base: '/js/modules/'
}).use(['form','layer','table','jquery','laypage'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        table = layui.table,
        $ = layui.jquery;

    // 监听提交
    form.on('submit(loginForm)', function(data) {
        var jsonData = data.field;
        if(hasChines(jsonData.username)){

            layer.msg('登录名称不能有中文', {time: 1000, icon:2});
            return false;
        }

        //提交到数据库
        var flg = loginUser(jsonData);
        if(!flg){
            return false;
        }
    });

    //-----------------------------------------------------------
    //添加job服务到服务器
    //-----------------------------------------------------------
    function loginUser(jsonData){
        var flag = false;
        //数据库里面检查
        $.ajax({
            type : "post",  //使用提交的方法 post、get
            url : "/loginUser",   //提交的地址
            data:jsonData,
            dataType:"json",//返回数据类型的格式
        }).done(function ( result ) {//回调操作
            //
            if(resultData.requestOk(result)){
                flag = true;
                layer.msg(result.info, {time: 1000, icon:1});1
                parent.location.href = '/index';
            }else{
                layer.msg(result.info, {time: 1000, icon:2});
                refreshCode();
            }

        });
        return flag;
    }

});

function refreshCode(){
    var captcha = document.getElementById("captcha");
    captcha.src = "/kaptcha.jpg?t=" + new Date().getTime();
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
