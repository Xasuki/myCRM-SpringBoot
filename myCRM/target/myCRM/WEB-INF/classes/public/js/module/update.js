layui.use(['form', 'layer'], function () {
    var form = layui.form, layer = parent.layer === undefined ? layui.layer : top.layer, $ = layui.jquery;
    form.on("submit(updateModule)", function (data) {
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //弹出loading
        $.post(ctx + "/module/update", data.field, function (res) {
            if (res.code == 200) {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("操作成功！",{icon:6});
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 1500);
            } else {
                layer.msg(res.msg, {icon: 5});
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