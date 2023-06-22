layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on("submit(saveBtn)",function (data){
        var fieldData = data.field;
        //打印表单数据
        // console.log(fieldData);

        //提交ajax请求
        $.ajax({
            type:"post",
            url:ctx+"/user/updatePassword",
            data:{
                oldPassword:fieldData.old_password,
                newPassword:fieldData.new_password,
                confirmPassword:fieldData.again_password
            },
            dataType:"json",
            success:function (data){
                //判断是否修改成功
                if(data.code==200){
                    layer.msg("用户修改密码成功！系统将在3秒后自动退出...",function (){
                        //清除本地cookie值
                        $.removeCookie("userId",{domain:"localhost",path:"/crm"});
                        $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                        $.removeCookie("tureName",{domain:"localhost",path:"/crm"});
                        //返回登录页面
                        window.parent.location.href=ctx+"/index";
                    });
                }else {
                    layer.msg(data.msg,{icon:5});
                }


            }
        });
        // 阻止表单提交
        return false;
    });
});