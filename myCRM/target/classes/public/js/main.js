layui.use(['element', 'layer', 'layuimini','jquery','jquery_cookie'], function () {
    var $ = layui.jquery,
        layer = layui.layer,
        $ = layui.jquery_cookie($);

    // 菜单初始化
    $('#layuiminiHomeTabIframe').html('<iframe width="100%" height="100%" frameborder="0"  src="welcome"></iframe>')
    layuimini.initTab();

    /**
     * 退出系统
     */
    $(".login-out").click(function (){
        layer.confirm('确定退出系统吗？', {icon: 3,title:'系统提示'}, function(){
            //清除本地cookie值
            $.removeCookie("userId",{domain:"localhost",path:"/crm"});
            $.removeCookie("userName",{domain:"localhost",path:"/crm"});
            $.removeCookie("tureName",{domain:"localhost",path:"/crm"});

            layer.msg('退出成功！', {icon: 1});
            //延迟1.5秒后跳转到登录页
            setTimeout(function () {
                window.parent.location.href=ctx+"/index";
            }, 1500);
        }, function(){
        });
    });


});