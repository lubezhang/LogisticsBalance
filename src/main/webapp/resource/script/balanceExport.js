var alert = function (content, title){
    $.ligerDialog.alert(content, title);
};
var gridManager = null;
$(function(){
    $("#searchbtn").ligerButton({ click: function (){
        if (!gridManager) return;
        gridManager.setOptions(
            { parms: [
                { name: 'balanceCode', value: $("#s_balanceCode").val()},
                { name: 'customerId', value: $("#s_customerId").val()},
                { name: 'addrDateBegin', value: $("#s_addrDateBegin").val()},
                { name: 'addrDateEnd', value: $("#s_addrDateEnd").val()}
            ] }
        );
        gridManager.loadData(true);
    }
    });

    //初始化快递单列表
    $("#maingrid").ligerGrid({
        columns: [
//            { display: "主键", name: "balanceId", width: 120,isAllowHide:true },
            { display: "快递单号", name: "balanceCode", width: 120 },
            { display: "客户", name: "customerId", width: 320 },
            { display: "金额", name: "money", width: 100 },
            { display: "付款方式", name: "gatherState", width: 100 },
            { display: "结算状态", name: "payoffState", width: 100 },
            { display: "修改日期", name: "operatorDate", width: 120 },
            { display: "收件日期", name: "addresseeDate", width: 120 }
        ],
        dataAction: 'server',
        pageSize: 20,
        toolbar: { items: [
            { id:"export", text: '导出对账单', click: itemclick, icon: 'add' },
            { line: true }
//            { id:"update", text: '补录', click: itemclick, icon: 'modify' }
        ]},
        url: '/replenishController/queryBalanceList.do',
        sortName: 'operatorDate',
        width: '100%', height: '100%',
        heightDiff:0, checkbox: true,
        alternatingRow:true,rownumbers:true,
        selectRowButtonOnly:true,
        enabledSort:true

    });
    gridManager = $("#maingrid").ligerGetGridManager();
    $("#pageloading").hide();


    $("#s_addrDateBegin").ligerDateEditor({ label: '收件日期', labelWidth: 60, labelAlign: 'right' });
    $("#s_addrDateEnd").ligerDateEditor({ label: '至', labelWidth: 20, labelAlign: 'center' });
});

//菜单按钮单击事件
function itemclick(item){
    switch (item.id){
        case "export":
            downloadBalance();
            break;
        default:
            break;
    }
}

function downloadBalance(){
//    var params = "customerId=" + $("#customerId").val();
//    params += "&payoffState=" + $('#payoffState').combobox('getValue');
//    params += "&balanceCode=" + $("#balanceCode").val();
//    params += "&addrDateBegin=" + $('#addresseeDate1').datebox('getValue');
//    params += "&addrDateEnd=" + $('#addresseeDate2').datebox('getValue');

    downloadSubmit.submit();
    //$("#downloadContent").attr("src","/replenishController/exportLogistics.do?"+params);
}