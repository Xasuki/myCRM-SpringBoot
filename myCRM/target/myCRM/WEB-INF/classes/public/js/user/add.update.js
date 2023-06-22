layui.use(['form', 'layer','formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    /**
     * 添加/更新用户
     */
    form.on("submit(addOrUpdateUser)", function (data) {
        var index = layer.msg("数据正在提交中，请稍候...", {icon: 16, time: false, shade: 0.8});
        var url = ctx + "/user/add";
        if ($("input[name='id']").val()) {
            url = ctx + "/user/update";
        }
        $.post(url, data.field, function (result) {
            if (result.code == 200) {
                setTimeout(function () {
                    layer.close(index);
                    layer.msg("操作成功！", {icon: 6});
                    layer.closeAll("iframe");
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
        //得到当前iframe层索引
        var index = parent.layer.getFrameIndex(window.name);
        //关闭操作
        parent.layer.close(index);
    });


    /**
     * 加载下拉框数据
     */
    formSelects = layui.formSelects;
    var userId = $("input[name='id']").val();
    formSelects.config("selectId", {
        type: "post",
        searchUrl: ctx + "/role/queryAllRoles?userId=" + userId,
        keyName: "roleName",
        keyVal: "id"
    }, true);
});