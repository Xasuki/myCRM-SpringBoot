layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //客户流失列表展示
    var  tableIns = table.render({
        elem: '#customerLossList',
        url : ctx+'/customer_loss/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerLossListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'cusNo', title: '客户编号',align:"center"},
            {field: 'cusName', title: '客户名称',align:"center"},
            {field: 'cusManager', title: '客户经理',align:"center"},
            {field: 'lastOrderTime', title: '最后下单时间',align:"center"},
            {field: 'lossReason', title: '流失原因',align:"center"},
            {field: 'confirmLossTime', title: '确认流失时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#op"}
        ]]
    });

    //行工具栏
    table.on("tool(customerLosses)", function (data) {
        if (data.event == 'add') {
            openCustomerReprieveDialog("客户流失管理-流失管理",data.data.id);
        } else if (data.event == 'info') {
            openCustomerReprieveDialog("客户流失管理-流失详情",data.data.id)
        }
    })

    //打开客户流失查看
    function openCustomerReprieveDialog(title,id){
        var url = ctx + "/customer_loss/toCustomerReprievePage?id="+id;
        layui.layer.open({
            title: title,
            type: 2,
            content: url,
            area: ["500px", "620px"],
            maxmin: true
        });
    }

    //绑定搜索按钮
    $(".search_btn").click(function () {
        table.reload("customerLossListTable", {
            page: {
                curr: 1
            },
            where: {
                cusNo: $("input[name='cusNo']").val(),
                cusName: $("input[name='cusName']").val(),
                state: $("#state").val()
            }
        });

    });
});
