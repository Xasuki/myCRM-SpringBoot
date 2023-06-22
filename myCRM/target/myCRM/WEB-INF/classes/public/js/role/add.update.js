layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 监听submit事件（添加或者更新）
     */
    form.on("submit(addOrUpdateRole)", function (obj) {
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        var url = ctx+"/role/add";
        if($("input[name='id']").val()){
            url = ctx+"/role/update";
        }
        $.post(url,obj.field,function (res){
            if(res.code == 200){
               setTimeout(function (){
                   layer.close(index);
                   layer.msg("操作成功！",{icon: 6});
                   layer.closeAll("iframe");
                   //刷新父页面
                   parent.location.reload();
               },1500);
            }else {
                layer.msg(res.msg,{icon:5});
            }
        });
        return false;

    });

    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function (){
        //得到当前iframe层的索引
         var frameIndex = parent.layer.getFrameIndex(window.name);
        //关闭iframe
        parent.layer.close(frameIndex);
    });
});