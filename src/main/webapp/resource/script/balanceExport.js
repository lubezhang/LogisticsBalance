var alert = function (content, title){
    $.ligerDialog.alert(content, title);
};
var gridManager = null;
$(function(){
    $("#searchbtn").ligerButton({ click: function (){
        if (!gridManager) return;
        gridManager.setOptions(
            { parms: [
//                { name: 'balanceCode', value: $("#s_balanceCode").val()},
                { name: 'customerId', value: $("#s_customerId").val()},
                { name: 'addrDateBegin', value: $("#s_addrDateBegin").val()},
                { name: 'addrDateEnd', value: $("#s_addrDateEnd").val()},
                { name: 'queryType', value: "export"}
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
            { line: true },
            { id:"status", text: '快递单结算', click: itemclick, icon: 'status' },
            { line: true }
        ]},
        url: '/replenishController/queryBalanceList.do?queryType=export',
        sortName: 'operatorDate',
        width: '100%', height: '100%',
        heightDiff:0, checkbox: true,
        alternatingRow:true,
        rownumbers:true,
        selectRowButtonOnly:true,
        enabledSort:true,
        onBeforeShowData:function(data){
            SubmitUtils.getJSON(
                "/replenishController/getTotalMoney.do",
                gridManager.options.parms,
                function(json){
                    if(json.success){
                        $("#gatherMoney").text("未结：【"+json.resultValue.notPayMoney+"】，  已结：【"+json.resultValue.payMoney+"】");
                    } else {
                        alert(json.message,"处理信息");
                    }
                }
            );
        }

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
        case "status":
            updateStatus();
            break;
        default:
            break;
    }
}

/**
 * 更改快递单结算状态
 */
function updateStatus(){
    var rows = gridManager.getSelectedRows();
    if(0 == rows.length){
        alert("请选择需要结算的快递单。");
    }
    var ids = "";
    for(var i = 0; i < rows.length; i++){
        if(0 == i){
            ids += rows[i].balanceId;
        } else {
            ids += ","+rows[i].balanceId;
        }
    }
    SubmitUtils.getJSON(
        "/replenishController/updatePayOff.do",
        {"ids":ids},
        function(json){
            if(json.success){
                alert(json.message, function(){
                    gridManager.loadData(true);
                });
            } else {
                error(json.message, function(){
                    gridManager.loadData(true);
                });
            }
        }
    );
}

/**
 * 下载快递对账单
 */
function downloadBalance(){
    $("form input").each(function(){
        var id = $(this).attr("id")
        $(this).val($("#s_"+id).val());
    });
    $("#payoffState").val("2");
    downloadSubmit.submit();
}