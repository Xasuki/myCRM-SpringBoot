layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 营销机会列表展示
     */
    var tableIns = table.render({
        elem: '#saleChanceList'
        , url: ctx + '/sale_chance/list'
        , cellMinWidth: 95
        , height: "full-125"
        , limit: 10
        , limits: [10, 20, 30]
        , toolbar: "#toolbarDemo"
        , id: "saleChanceListTable"
        , page: true
        , cols: [[
            {type: "checkbox", fixed: "center"},
            {field: "id", title: '编号', fixed: "true"},
            {field: 'chanceSource', title: '机会来源', align: "center"},
            {field: 'customerName', title: '客户名称', align: 'center'},
            {field: 'cgjl', title: '成功⼏率', align: 'center'},
            {field: 'overview', title: '概要', align: 'center'},
            {field: 'linkMan', title: '联系⼈', align: 'center'},
            {field: 'linkPhone', title: '联系电话', align: 'center'},
            {field: 'description', title: '描述', align: 'center'},
            {field: 'createMan', title: '创建⼈', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'uname', title: '指派⼈', align: 'center'},
            {field: 'assignTime', title: '分配时间', align: 'center'},
            {
                field: 'state', title: '分配状态', align: 'center', templet: function (d) {
                    return formatterState(d.state);
                }
            },
            {
                field: 'devResult', title: '开发状态', align: 'center', templet: function (d) {
                    return formatterDevResult(d.devResult);
                }
            },
            {
                title: '操作', templet: '#saleChanceListBar', fixed: "right", align: "center",
                minWidth: 150
            }
        ]]
    });

    /**
     * 格式化分配状态
     * 0 - 未分配
     * 1 - 已分配
     * 其他 - 未知
     * @param state
     * @returns {string}
     */
    function formatterState(state) {
        if (state == 0) {
            return "<div style='color: #727206'>未分配</div>";
        } else if (state == 1) {
            return "<div style='color: green'>已分配</div>";
        } else {
            return "<div style='color: red'>未知</div>";
        }
    }

    /**
     * 格式化开发状态
     * 0 - 未开发
     * 1 - 开发中
     * 2 - 开发成功
     * 3 - 开发失败
     * @param value
     * @returns {string}
     */
    function formatterDevResult(value) {
        if (value == 0) {
            return "<div style='color: #8c8c10'>未开发</div>";
        } else if (value == 1) {
            return "<div style='color: #006093;'>开发中</div>";
        } else if (value == 2) {
            return "<div style='color: green'>开发成功</div>";
        } else if (value == 3) {
            return "<div style='color: red'>开发失败</div>";
        } else {
            return "<div style='color: #af0000'>未知</div>"
        }
    }

    /**
     * 绑定搜索按钮的点击事件
     */
    $(".search_btn").click(function () {
        tableIns.reload({
            where: {
                ////设定异步数据接⼝的额外参数
                customerName: $("input[name='customerName']").val(),
                createMan: $("input[name='createMan']").val(),
                state: $("#state").val()
            }, page: {
                //从第一页开始
                curr: 1
            }
        });//只重载数据
    });

    /**
     * 头部⼯具栏 监听事件 新增，删除事件
     */
    table.on("toolbar(saleChances)", function (data) {
            if (data.event == "add") {
                // 点击新增按钮，打开添加营销机会的对话框
                openAddOrUpdateSaleChanceDialog();
            } else if (data.event == "del") {
                // 点击删除按钮，打开添加营销机会的对话框
                openDeleteSaleChanceDialog(data);
            }
        }
    );

    /**
     * 行工具栏 监听事件 编辑，删除事件
     */
    table.on("tool(saleChances)", function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        var saleChanceId = data.id;
        //判断处理事件
        if ("edit" == layEvent) {
            openAddOrUpdateSaleChanceDialog(saleChanceId);
        } else if ("del" == layEvent) {
            openDeleteSaleChanceDialog(data, saleChanceId);
        }
    });

    /**
     * 打开添加/更新营销机会的对话框
     * @param saleChanceId
     */
    function openAddOrUpdateSaleChanceDialog(saleChanceId) {
        var title = "<h3>营销机会管理 - 营销机会添加</h3>";
        var url = ctx + "/sale_chance/addOrUpdateSaleChancePage";
        if (saleChanceId) {
            title = "<h3>营销机会管理 - 营销机会更新</h3>";
            url += "?saleChanceId=" + saleChanceId;
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
     * 打开删除营销机会的对话框
     * @param data
     * @param saleChanceId
     */
    function openDeleteSaleChanceDialog(data, saleChanceId) {
        //根据saleChanceId 判断是行部删除  还是 头部删除
        if (saleChanceId) {
            //行部 删除
            layer.confirm("您确定要删除这条记录吗？", {icon: 3, title: "营销机会管理"}, function (index) {
                layer.close(index);
                //发送ajax请求
                $.ajax({
                    type: "post",
                    url: ctx + "/sale_chance/delete",
                    data: {
                        ids: data.id
                    },
                    success: function (result) {
                        //判断结果
                        if (result.code == 200) {
                            layer.msg("删除成功！ ", {icon: 6});
                            //刷新表格
                            tableIns.reload();
                        } else {
                            layer.msg(result.msg, {icon: 5});
                        }
                    }
                });
                return false;
            });
        } else {
            //头部删除
            //获取数据表格中被选中的行数据
            var checkStatus = table.checkStatus("saleChanceListTable");
            //console.log(checkStatus);
            //获取所有被选中的记录对应的数据
            var saleChanceData = checkStatus.data;
            //判断用户是否选择记录
            if (saleChanceData.length < 1) {
                layer.msg("请选择要删除的记录！", {icon: 5});
                return;
            }
            layer.confirm("您确定要删除选中的记录吗？", {icon: 3, title: "营销机会管理"}, function (index) {
                layer.close(index);

                var ids = "ids=";
                for (var i = 0; i < saleChanceData.length; i++) {
                    if (i < saleChanceData.length - 1) {
                        ids = ids + saleChanceData[i].id + "&ids=";
                    } else {
                        ids = ids + saleChanceData[i].id;
                    }
                }
                $.ajax({
                    type: "post",
                    url: ctx + "/sale_chance/delete",
                    data: ids,
                    success: function (result) {
                        //判断结果
                        if (result.code == 200) {
                            layer.msg("删除成功！ ", {icon: 6});
                            //刷新表格
                            tableIns.reload();
                        } else {
                            layer.msg(result.msg, {icon: 5});
                        }
                    }
                });
                return false;
            });
        }
    }
});






