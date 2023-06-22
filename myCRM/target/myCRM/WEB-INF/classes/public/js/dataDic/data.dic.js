layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    /**
     * 用户列表展示
     */
    var tableIns = table.render({
        elem: '#dataDicList',
        url: ctx + '/data_dic/list',
        cellMinWidth: 95,
        page: true,
        height: "full-125",
        limits: [10, 15, 20, 25],
        limit: 10,
        toolbar: "#toolbarDemo",
        id: "dataDicListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: "id", title: '编号', fixed: "true", width: 80},
            {field: 'dataDicName', title: '字典名称', minWidth: 50, align: "center"},
            {field: 'dataDicValue', title: '字典属性值', minWidth: 100, align: 'center'},
            {field: 'createDate', title: '创建时间', align: 'center', minWidth: 150},
            {field: 'updateDate', title: '更新时间', align: 'center', minWidth: 150},
            {title: '操作', minWidth: 150, templet: '#dataDicListBar', fixed: "right", align: "center"}
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
                dataDicName: $("input[name='dataDicName']").val(),
                dataDicValue: $("input[name='dataDicValue']").val(),
            }
        });
    });

});