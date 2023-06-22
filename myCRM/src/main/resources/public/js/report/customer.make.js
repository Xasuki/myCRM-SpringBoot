layui.use(['layer','echarts'], function () {
    var $ = layui.jquery,
        echarts = layui.echarts;

    $.ajax({
        type:"get",
        url:ctx+"/customer/countCustomerMark",
        dataType:"json",
        success:function (data){
            var myChart = echarts.init(document.getElementById('line'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '客户构成分析'
                },
                tooltip: {},
                xAxis: {
                    type:'category',
                    data: data.dataX
                },
                yAxis: {type:'value'},
                series: [
                    {
                        //type: 'bar',柱状图
                        type: 'line',//折线图
                        data: data.dataY
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });

    $.ajax({
        type:"get",
        url:ctx+"/customer/countCustomerMark02",
        dataType:"json",
        success:function (data){
            var myChart = echarts.init(document.getElementById('Nightingale'));
            var option = {
                title: {
                    text: '客户构成分析',
                    subtext: '来自CRM',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    left: 'center',
                    top: 'bottom',
                    data: data.dataX
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                series: [
                    {
                        name: '半径模式',
                        type: 'pie',
                        radius: [20, 140],
                        center: ['25%', '50%'],
                        roseType: 'radius',
                        itemStyle: {
                            borderRadius: 5
                        },
                        label: {
                            show: false
                        },
                        emphasis: {
                            label: {
                                show: true
                            }
                        },
                        data:data.dataY
                    },
                    {
                        name: '面积模式',
                        type: 'pie',
                        radius: [20, 140],
                        center: ['75%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 5
                        },
                        data: data.dataY
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });
});