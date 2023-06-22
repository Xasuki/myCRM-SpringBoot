layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 角色展示栏
     */
    var tableIns = table.render({
        elem: '#roleList',
        url: ctx + '/role/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "roleListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: "id", title: '编号', fixed: "true", width: 80},
            {field: 'roleName', title: '角色名', minWidth: 50, align: "center"},
            {field: 'roleReMark', title: '角色备注', minWidth: 100, align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center', minWidth: 150},
            {field: 'updateDate', title: '更新时间', align: 'center', minWidth: 150},
            {title: '操作', minWidth: 150, templet: '#roleListBar', fixed: "right", align: "center"}
        ]]
    });

    /**
     * 多条件搜索
     */
    $(".search_btn").click(function () {
        table.reload("roleListTable", {
            page: {
                curr: 1 //重第一页开始
            },
            where: {
                roleName: $("input[name='roleName']").val(),
            }
        });
    });


    /**
     * 头工具栏事件
     */
    table.on("toolbar(roles)", function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case "add":
                openAddOrUpdateRoleDialog();
                break;
            case "grant":
                openAddGrantDailog(checkStatus.data);
                break;
        }
    });

    /**
     * 行工具栏事件
     */
    table.on("tool(roles)", function (obj) {
        if ("edit" == obj.event) {
            openAddOrUpdateRoleDialog(obj.data.id);
        } else if ("del" == obj.event) {
            layer.confirm("您确定删除当前角色吗？", {icon: 3, title: "角色管理"}, function (index) {
                layer.close(index);
                $.post(ctx + "/role/delete", {id: obj.data.id}, function (res) {
                    if (res.code == 200) {
                        layer.msg("操作成功！", {icon: 6});
                        tableIns.reload();
                    } else {
                        layer.msg(res.msg, {icon: 3});
                    }
                });
                return false;
            });
        }
    });

    /**
     * 打开添加/更新页面
     */
    function openAddOrUpdateRoleDialog(id) {
        var url = ctx + "/role/addOrUpdateRolePage";
        var title = "角色管理-角色添加";
        if (id) {
            url += "?id=" + id;
            title = "角色管理-角色更新";
        }
        layui.layer.open({
            title: title,
            type: 2,
            area: ["600px", "280px"],
            maxmin: true,
            content: url
        });
    }

    /**
     * 打开角色授权页面
     * @param datas
     */
    function openAddGrantDailog(datas) {
        if (datas.length == 0) {
            layer.msg("请选择待授权⻆⾊记录!", {icon: 5});
            return;
        }
        if (datas.length > 1) {
            layer.msg("暂不支持批量角色授权！", {icon: 5});
        }
        var url = ctx + "/role/toAddGrantPage?roleId=" + datas[0].id;
        var title = "角色管理-角色授权"
        layui.layer.open({
            title: title,
            content: url,
            type: 2,
            area: ["600px", "480px"],
            maxmin: true
        });
    }

});
