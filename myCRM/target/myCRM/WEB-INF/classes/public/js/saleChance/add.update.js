layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 监听submit事件
     * 实现营销机会的添加与更新
     */
    form.on("submit(addOrUpdateSaleChance)", function (data) {
        // 提交数据时的加载层
        var index = layer.msg("数据提交中，请稍候...", {
            icon: 16, // 图标
            time: false, // 不关闭
            shade: 0.8 // 设置遮罩的透明度
        });
        //请求地址
        var url = ctx + "/sale_chance/add";
        if ($("input[name='id']").val()) {
            url = ctx + "/sale_chance/update";
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
    $("#closeBtn").click(function () {
        // 先得到当前iframe层的索引
        var frameIndex = parent.layer.getFrameIndex(window.name);
        // 关闭frameIndex
        parent.layer.close(frameIndex);
    });

    /**
     * 显示下拉框
     */
    $.post(ctx + "/user/queryAllSales", function (data) {
        //判断当前所要修改数据的指派人的值
        var assignMan = $("input[name='man']").val();
        for (var i = 0; i < data.length; i++) {
            // 当前修改记录的指派⼈的值 与 循环到的值 相等，则下拉框则选
            if (assignMan == data[i].id) {
                $("#assignMan").append('<option value="' + data[i].id + '"selected>' + data[i].uname + '</option>');
            } else {
                $("#assignMan").append('<option value="' + data[i].id + '">' + data[i].uname + '</option>');
            }
            // 重新渲染下拉框内容
            layui.form.render("select");
        }
    });
    return false;


});
