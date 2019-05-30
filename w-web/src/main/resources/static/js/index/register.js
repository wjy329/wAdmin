layui.config({
    base: '/js/modules/'
}).use(['form','layer','table','jquery','laypage'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        table = layui.table,
        $ = layui.jquery;

    // 监听提交
    form.on('submit(registerForm)', function(data) {
        var jsonData = data.field;
        if(hasChines(jsonData.username)){

            layer.msg('登录名称不能有中文', {time: 1000, icon:2});
            return false;
        }

        if(passWord(jsonData.pswd)){
            layer.msg('密码长度至少为8位，必须包含大小写字母、数字和特殊符号', {time: 1000, icon:2});
            return false;
        }



        //提交到数据库
        var flg = registerUser(jsonData);
        if(!flg){
            return false;
        }
    });

    //-----------------------------------------------------------
    //添加job服务到服务器
    //-----------------------------------------------------------
    function registerUser(jsonData){
        var flag = false;
        //数据库里面检查
        $.ajax({
            type : "post",  //使用提交的方法 post、get
            url : "/register",   //提交的地址
            data:jsonData,
            dataType:"json",//返回数据类型的格式
        }).done(function ( result ) {//回调操作
            //
            if(resultData.requestOk(result)){
                flag = true;
                layer.msg(result.info+"5秒后跳转到登录页面", {time: 5000, icon:1, end: function () {
                    window.location.href = "/login";
                }});

            }else{
                layer.msg(result.info, {time: 1000, icon:2});
            }

        });
        return flag;
    }

});

//--------------------------------------------------
//包含了中文字符
//--------------------------------------------------
function passWord(obj){
    var reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/
    var result = obj.match(reg);
    if(result == null){
        return true;
    }
    return false;
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