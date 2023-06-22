layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 监听submit事件
     * 实现客户的添加与更新
     */
    form.on("submit(addOrUpdateCustomer)", function (data) {
        // 提交数据时的加载层
        var index = layer.msg("数据提交中，请稍候...", {
            icon: 16, // 图标
            time: false, // 不关闭
            shade: 0.8 // 设置遮罩的透明度
        });
        //请求地址
        var url = ctx + "/customer/add";
        if ($("input[name='id']").val()) {
            url = ctx + "/customer/update";
        }
        // 发送ajax请求
        $.post(url, data.field, function (result) {
            //操作成功
            if (result.code == 200) {
                setTimeout(function () {
                    layer.msg("操作成功！", {icon: 6})
                    //关闭加载层
                    layer.close(index);
                    //关闭弹出层
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 1500);
            } else {
                layer.msg(result.msg, {icon: 5});
            }
        });
        return false;
    });

    /**
     * 关闭弹出层
     */
    $(".closeBtn").click(function (){
       var frameIndex = parent.layer.getFrameIndex(window.name);
       parent.layer.close(frameIndex);
    });


});