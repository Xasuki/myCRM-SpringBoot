layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 用户列表展示
     */
    var tableIns = table.render({
        elem: '#userList',
        url: ctx + '/user/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "userListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: "id", title: '编号', fixed: "true", width: 80},
            {field: 'userName', title: '⽤户名', minWidth: 50, align: "center"},
            {field: 'email', title: '⽤户邮箱', minWidth: 100, align: 'center'},
            {field: 'phone', title: '⽤户电话', minWidth: 100, align: 'center'},
            {field: 'trueName', title: '真实姓名', align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center', minWidth: 150},
            {field: 'updateDate', title: '更新时间', align: 'center', minWidth: 150},
            {title: '操作', minWidth: 150, templet: '#userListBar', fixed: "right", align: "center"}
        ]]
    });

    /**
     * 多条件搜索
     */
    $(".search_btn").click(function () {
        table.reload("userListTable", {
            page: {
                curr: 1 //重第一页开始
            },
            where: {
                userName: $("input[name='userName']").val(),
                email: $("input[name='email']").val(),
                phone: $("input[name='phone']").val()
            }
        });
    });

    /**
     * 头部工具栏事件
     */
    table.on("toolbar(users)", function (obj) {
        /*  var checkStatus = table.checkStatus(obj.config.id);
          switch (obj.event){
              case "add":
                  openAddOrUpdateUserDialog();
                  break;
              case "del":
                  deleteUserDialog();
                  break;
          }*/
        if ("add" == obj.event) {
            openAddOrUpdateUserDialog();
        } else if ("del" == obj.event) {
            openDeleteUserDialog(obj);
        }
    });

    /**
     * 行工具栏事件
     */
    table.on("tool(users)", function (obj) {
        var layEvent = obj.event;
        if (layEvent == "edit") {
            openAddOrUpdateUserDialog(obj.data.id);
        } else if (layEvent == "del") {
            openDeleteUserDialog(obj, obj.data.id);
        }
    });

    /**
     * 打开添加/更新用户页面
     * @param userId
     */
    function openAddOrUpdateUserDialog(userId) {
        var url = ctx + "/user/openAddOrUpdateUserPage";
        var title = "用户管理-用户添加";
        //console.log(userId);
        if (userId) {
            url += "?id=" + userId;
            title = "用户管理-用户更新";
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["650px", "400px"],
            maxmin: true,
            content: url
        });

    }

    /**
     * 打开删除用户记录页面
     */
    function openDeleteUserDialog(data, userId) {
        //根据saleChanceId 判断是行部删除  还是 头部删除
        if (userId) {
            //行部 删除
            layer.confirm("您确定要删除这条记录吗？", {icon: 3, title: "用户管理"}, function (index) {
                layer.close(index);
                //发送ajax请求
                $.ajax({
                    type: "post",
                    url: ctx + "/user/delete",
                    data: {
                        ids: data.data.id
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
            var checkStatus = table.checkStatus("userListTable");
            //console.log(checkStatus);
            //获取所有被选中的记录对应的数据
            var userData = checkStatus.data;
            //判断用户是否选择记录
            //console.log(userData.length);
            if (userData.length < 1) {
                layer.msg("请选择要删除的记录！", {icon: 5});
                return;
            }
            layer.confirm("您确定要删除选中的记录吗？", {icon: 3, title: "用户管理"}, function (index){
                layer.close(index);

                var ids = "ids=";
                for (var i = 0; i < userData.length; i++) {
                    if (i < userData.length - 1) {
                        ids = ids + userData[i].id + "&ids=";
                    } else {
                        ids = ids + userData[i].id;
                    }
                }
                $.ajax({
                    type: "post",
                    url: ctx + "/user/delete",
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