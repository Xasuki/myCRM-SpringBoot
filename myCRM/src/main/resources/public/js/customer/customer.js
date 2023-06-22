layui.use(['table', 'layer', "form"], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;

    //客户列表展示
    var tableIns = table.render({
        elem: '#customerList',
        url: ctx + '/customer/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "customerListTable",
        cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "true"},
            {field: 'name', title: '客户名', align: "center"},
            {field: 'fr', title: '法人', align: 'center'},
            {field: 'khno', title: '客户编号', align: 'center'},
            {field: 'area', title: '地区', align: 'center'},
            {field: 'cusManager', title: '客户经理', align: 'center'},
            {field: 'myd', title: '满意度', align: 'center'},
            {field: 'level', title: '客户级别', align: 'center'},
            {field: 'xyd', title: '信用度', align: 'center'},
            {field: 'address', title: '详细地址', align: 'center'},
            {field: 'postCode', title: '邮编', align: 'center'},
            {field: 'phone', title: '电话', align: 'center'},
            {field: 'webSite', title: '网站', align: 'center'},
            {field: 'fax', title: '传真', align: 'center'},
            {field: 'zczj', title: '注册资金', align: 'center'},
            {field: 'yyzzzch', title: '营业执照', align: 'center'},
            {field: 'khyh', title: '开户行', align: 'center'},
            {field: 'khzh', title: '开户账号', align: 'center'},
            {field: 'gsdjh', title: '国税', align: 'center'},
            {field: 'dsdjh', title: '地税', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'updateDate', title: '更新时间', align: 'center'},
            {title: '操作', templet: '#customerListBar', fixed: "right", align: "center", minWidth: 150}
        ]]
    });

    /**
     * 添加搜索点击事件
     */
    $(".search_btn").click(function () {
        table.reload("customerListTable", {
            page: {
                curr: 1
            },
            where: {
                cusName: $("input[name='name']").val(),
                cusNo: $("input[name='khno']").val(),
                level: $("#level").val()
            }
        });

    });


    /**
     * 头部⼯具栏 监听事件 新增，删除事件
     */
    table.on("toolbar(customers)", function (data) {
        var checkStatus = table.checkStatus("customerListTable");
            if (data.event == "add") {
                // 点击新增按钮，打开添加客户的对话框
                openAddOrUpdateCustomerDialog();
            } else (data.event == 'order')
            {
                // 点击新增按钮，打开添加订单查看的对话框
                openCustomerOrderDialog(checkStatus);
            }
        }
    );

    table.on("tool(customers)", function (data) {
        if (data.event == 'edit') {
            openAddOrUpdateCustomerDialog(data.data.id);
        } else if (data.event == 'del') {
            openDeleteCustomerDialog(data)
        }
    })

    /**
     * 打开添加/更新客户页面
     * @param id
     */
    function openAddOrUpdateCustomerDialog(id) {
        var title = "<h3>客户管理 - 客户添加</h3>";
        var url = ctx + "/customer/addOrUpdateCustomerPage";
        if (id) {
            title = "<h3>客户管理 - 客户更新</h3>";
            url += "?id=" + id;
        }
        layui.layer.open({
            title: title,
            type: 2,
            content: url,
            area: ["500px", "620px"],
            maxmin: true
        });
    }

    /**
     * 打开客户删除页面
     * @param data
     * @param id
     */
    function openDeleteCustomerDialog(data) {
        //行工具栏删除
        layer.confirm("您确定要删除这条记录吗？", {icon: 3, title: "客户管理"}, function (index) {
            layer.close(index);
            $.ajax({
                url: ctx + "/customer/delete",
                data: {
                    id: data.data.id
                },
                type: "post",
                success: function (res) {
                    if (res.code == 200) {
                        layer.msg("删除成功!", {icon: 6});
                        tableIns.reload();
                    } else {
                        layer.msg(res.msg, {icon: 5});
                    }
                }

            });
            return false;
        });
    }

    /**
     * 打开订单查看
     */
    function openCustomerOrderDialog(checkStatus){
        // console.log(checkStatus.data);
        if(checkStatus.data.length == 0){
            layer.msg("请选择订单信息！",{icon:5});
            return;
        }
        if(checkStatus.data.length>1){
            layer.msg("暂不支持批量查看操作！",{icon:5});
            return;
        }
        // console.log(checkStatus.data[0].id);
        var url = ctx+"/customer/toCustomerOrderPage?id="+checkStatus.data[0].id;
        layui.layer.open({
            title:"客户管理 - 订单信息展示",
            content: url,
            type:2,
            area:["700px","500px"],
            maxmin:true
        });
    }
});
