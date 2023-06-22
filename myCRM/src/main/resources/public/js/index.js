layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    /**
     * 用户登录 表单提交
     */
    form.on("submit(login)", function (data) {
        var fieldData = data.field;
        $.ajax({
            type: "post",
            url: ctx + "/user/login",
            data: {
                userName: fieldData.username,
                uPwd: fieldData.password
            },
            dataType: "json",
            success:function (data){
                //判断是否成功
                if(data.code==200){
                    layer.msg("登录成功！",function (){
                        var result = data.result;
                        //判断是否勾选“记住我”
                        if($("#rememberMe").prop("checked")){
                            $.cookie("userId",result.userId,{expires:7});
                            $.cookie("userName",result.userName,{expires:7});
                            $.cookie("trueName",result.trueName,{expires:7});
                        }

                        //将用户信息存入cookie中

                        $.cookie("userId",result.userId);
                        $.cookie("userName",result.userName);
                        $.cookie("trueName",result.trueName);

                        //登录成功->跳转首页
                        window.location.href = ctx+"/main";
                    });
                }
                else {
                    //登录失败
                    layer.msg(data.msg,{icon:5});
                }
            }

        });
        //阻止表单提交
        return false;
    });

});