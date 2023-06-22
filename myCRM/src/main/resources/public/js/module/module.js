layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treeTable = layui.treetable;

    // 渲染表格
    treeTable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'id',
        treePidName: 'parentId',
        elem: '#munu-table',
        url: ctx + '/module/list',
        toolbar: "#toolbarDemo",
        treeDefaultClose: true,
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if (d.grade == 1) {
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', width: 280, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    /**
     * 头部工具栏事件
     */
    table.on('toolbar(munu-table)', function (obj) {
        switch (obj.event) {
            case "expand":
                // 全部展开
                treeTable.expandAll('#munu-table');
                break;
            case "fold":
                // 全部折叠
                treeTable.foldAll('#munu-table');
                break;
            case "add":
                // 添加目录 层级=0 父菜单=-1
                openAddModuleDialog(0, -1);
                break;
        }
        ;
    });

    table.on("tool(munu-table)", function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent == 'add') {
            if (data.grade == 2) {
                layer.msg("暂不支持四级菜单添加！", {icon: 5});
                return;
            }
            // 一级|二级菜单   grade=当前层级+1，parentId=当前资源的ID
            openAddModuleDialog(data.grade + 1, data.id);
        } else if (layEvent == 'edit') {
            // 修改资源
            openUpdateModuleDialog(data.id);
        } else if (layEvent == 'del') {
            deleteModuleDialog(data.id);
        }
    });

    function openAddModuleDialog(grade, parentId) {
        var title = "资源管理 - 资源添加";
        var url = ctx + "/module/addModulePage?grade=" + grade + "&parentId=" + parentId;
        layer.open({
            type: 2,
            title: title,
            content: url,
            area: ["700px", "450px"],
            maxmin: true
        });
    }

    function openUpdateModuleDialog(id) {
        var title = "资源管理 - 资源修改";
        var url = ctx + "/module/updateModulePage?id=" + id;
        layer.open({
            type: 2,
            title: title,
            content: url,
            area: ["700px", "450px"],
            maxmin: true
        });
    }

    function deleteModuleDialog(id) {
        layer.confirm("您确定要删除这条记录吗？", {icon: 3, title: "资源管理"}, function (data) {
            //如果确认发起ajax请求
            $.ajax({
                type: "post",
                url: ctx + "/module/delete",
                data: {
                    id: id
                },
                success:function (res){
                    if(res.code == 200){
                        layer.msg("操作成功！",{icon:"6"});
                        window.location.reload();
                    }
                    else {
                        layer.msg(res.msg,{icon:5});
                    }
                }
            });
            return false;
        });
    }

});