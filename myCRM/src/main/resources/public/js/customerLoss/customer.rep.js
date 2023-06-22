layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    // 暂缓列表展示
    var tableIns = table.render({
        elem: '#customerRepList',
        url: ctx + '/customer_rep/list?lossId='+ $("input[name='id']").val(),
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "customerRepListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "true"},
            {field: 'measure', title: '暂缓措施', align: "center"},
            {field: 'createDate', title: '创建时间', align: "center"},
            {field: 'updateDate', title: '更新时间', align: "center"},
            {title: '操作', fixed: "right", align: "center", minWidth: 150, templet: "#customerRepListBar"}
        ]]
    });

    /**
     * 头部工具栏
     */
    table.on("toolbar(customerReps)", function (obj) {
        //	添加暂缓
        if ("add" == obj.event) {
            // 打开添加/更新暂缓数据的页面
            openAddOrUpdateCustomerReprDialog();
        }
        //  确认流失
        else if ("confirm" == obj.event) {
            // 更新流失客户的流失状态
            updateCustomerLossState();
        }
    });

    /**
     * 行工具栏
     */
    table.on("tool(customerReps)", function (obj) {
        //  更新
        if ("edit" == obj.event) {
            // 打开添加/更新暂缓数据的页面
            openAddOrUpdateCustomerReprDialog(obj.data.id);
        }
        //  删除
        else if ("del" == obj.event) {
            // 删除暂缓数据
            deleteCustomerReprDialog(obj.data.id);
        }
    });

    /**
     * 打开添加/更新暂缓页面
     * @param id
     */
    function openAddOrUpdateCustomerReprDialog(id) {
        var url = ctx + "/customer_rep/toAddOrUpdateCustomerReprieve?lossId="+$("input[name='id']").val();
        var title = "暂缓管理 - 暂缓项添加";
        if (id) {
            url = url + "&id=" + id;
            console.log(url);
            title = "暂缓管理 - 暂缓项更新";
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["500px", "300px"],
            maxmin: true,
            content: url
        });
    }

    /**
     * 删除暂缓数据
     * @param id
     */
    function deleteCustomerReprDialog(id) {
        // 弹出确认框，询问用户是否确认删除
        var checkStatus = table.checkStatus("customerRepListTable");
        if(checkStatus.data.length>1){
            layer.msg("暂不支持多行删除操作！",{icon:5});
            return;
        }
        layer.confirm('确定要删除该记录吗？', {icon: 3, title: "暂缓管理"}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 发送ajax请求，删除记录
            $.ajax({
                type: "post",
                url: ctx + "/customer_rep/delete",
                data: {
                    id: id
                },
                success: function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功！", {icon: 6});
                        // 刷新表格
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            });
            return false;
        });
    }

    /**
     * 更新流失状态
     */
    function updateCustomerLossState() {
        var checkStatus = table.checkStatus("customerRepListTable");
        if(checkStatus.data.length==0){
            layer.msg("请选择要流失的客户！",{icon:5});
            return;
        }
        if(checkStatus.data.length>1){
            layer.msg("暂不支持多行客户流失操作！",{icon:5});
            return;
        }
        // 弹出确认框，询问用户是否确认流失
        layer.confirm('确定标记当前客户为确认流失吗？',{icon:3, title:"客户流失管理"}, function (index) {
            // 关闭确认框
            layer.close(index);
            // prompt层  输入框
            layer.prompt({title: '请输入流失原因', formType: 2}, function(text, index){
                // 关闭输入框
                layer.close(index);
                /**
                 * 发送请求给后台，更新指定流失客户的流失状态
                 *  1. 指定流失客户   流失客户ID （隐藏域）
                 *  2. 流失原因      输入框的内容（text）
                 */
                $.ajax({
                    type:"post",
                    url:ctx + "/customer_loss/updateCustomerLossStateById",
                    data:{
                        id:$("[name='id']").val(), // 流失客户的ID
                        lossReason:text // 流失原因
                    },
                    dataType:"json",
                    success:function (result) {
                        if (result.code == 200) {
                            layer.msg('确认流失成功！', {icon:6});
                            // 关闭窗口
                            layer.closeAll("iframe");
                            // 刷新父页面
                            parent.location.reload();
                        } else {
                            layer.msg(result.msg, {icon:5});
                        }
                    }
                });
                return false;
            });
        });
    }
});
