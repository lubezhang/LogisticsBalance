var gridManager = null;
$(function(){
    $("#searchbtn").ligerButton({ click: function (){
        if (!gridManager) return;
        gridManager.setOptions(
            { parms: [
                { name: 'balanceCode', value: $("#sbalanceCode").val()},
                { name: 'customerId', value: $("#sCustomerName").val()}
//                { name: 'queryType', value: "replenish"}
            ] }
        );
        gridManager.loadData(true);
    }
    });

    //初始化快递单列表
    $("#maingrid").ligerGrid({
        columns: [
//            { display: "主键", name: "balanceId", width: 120 },
            { display: "快递单号", name: "balanceCode", width: 120 },
            { display: "客户", name: "customerId", width: 320 },
            { display: "快递员", name: "balanceUser", width: 120 },
            { display: "补录员", name: "lockUser", width: 120 },
            { display: "收件日期", name: "addresseeDate", width: 120 },
            { display: "修改日期", name: "operatorDate", width: 120 }
        ],
        dataAction: 'server',
        pageSize: 20,
        toolbar: { items: [
            { id:"import", text: '导入补录快递单', click: itemclick, icon: 'add' },
            { line: true },
            { id:"unlock", text: '解锁', click: itemclick, icon: 'edit' },
            { id:"edit", text: '编辑', click: itemclick, icon: 'edit' },
            { id:"delete", text: '删除', click: itemclick, icon: 'delete' },
            { line: true },
            { id:"importQuery", text: '导入查询快递单', click: itemclick, icon: 'add' }
        ]},
        url: '/replenishController/queryBalanceList.do',
        sortName: 'operatorDate',
        width: '100%', height: '100%',
        heightDiff:0, checkbox: true,
        alternatingRow:true,rownumbers:true,
        selectRowButtonOnly:true
    });
    gridManager = $("#maingrid").ligerGetGridManager();
    $("#pageloading").hide();

//    $("#money").ligerSpinner({ height: 28, type: 'int',isNegative:false });
    initForm();
});

/**
 * 初始化表单
 */
function initForm(){
    $.metadata.setType("attr", "validate");
    var v = $("form").validate({
        debug: true,
        errorPlacement: function (lable, element){
            if (element.hasClass("l-textarea")){
                element.ligerTip({ content: lable.html(), target: element[0] });
            }else if (element.hasClass("l-text-field")){
                element.parent().ligerTip({ content: lable.html(), target: element[0] });
            }else{
                lable.appendTo(element.parents("td:first").next("td"));
            }
        },
        success: function (lable){
            lable.ligerHideTip();
            lable.remove();
        },
        submitHandler: function (){
            $("form .l-text,.l-textarea").ligerHideTip();
            SubmitUtils.getJSON(
                "/replenishController/updateBalanceDetail.do",
                decodeURIComponent($("#form1").serialize()),
                function(json){
                    if(json.success){
                        $.ligerDialog.alert("修改成功", "处理信息",'',function(){
                            balanceEditWin.close();
//                            balanceWin.destroy();
                        });
                    } else {
                        alert("修改失败"+json.success,"处理信息");
                    }
                }
            );
        }
    });
    $("form").ligerForm();
    $(".l-button-test").click(function (){
        $("#customerId").val("");
        $("#money").val(0);
        $("#balanceUser").val("");
        $("#addresseeDate").val("");
    });
}

//菜单按钮单击事件
function itemclick(item){
    switch (item.id){
        case "import":
            importBalance({"payoffState":"2"});
            break;
        case "importQuery":
            importBalance({"payoffState":"1"});
            break;
        case "unlock":
            unlock();
            break;
        case "edit":
            editBalance();
            break;
        case "delete":
            deleteBalance();
            break;
        default:
            break;
    }
}
//导入快递单图片
function importBalance(params){
    SubmitUtils.getJSON(
        "/replenishController/importPic.do",
        params,
        function(json){
            if(json){
                var strComplete = "";
                if(json.success){
                    strComplete = "导入完成。成功【"+json.resultValue.completeCount+"】条，失败【"+json.resultValue.errorCount+"】条";
                    alert(strComplete,"处理信息");
                } else {
                    strComplete = json.message;
                    alert(strComplete,"处理信息");
                }
            }
        }
    );
}

var deleteBalance = function(){
    var rows = gridManager.getSelectedRows();
    if(0 == rows.length){
        alert("请选择需要删除的快递单信息。");
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
        "/replenishController/deleteBalance.do",
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

var unlock = function(){
    var rows = gridManager.getSelectedRows();
    if(0 == rows.length){
        alert("请选择需要删除的快递单信息。");
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
        "/replenishController/unlockBalance.do",
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

var balanceEditWin = null;
var editBalance = function(){
    var rows = gridManager.getSelectedRows();
    if(0 == rows.length){
        alert("请选择需要删除的快递单信息。");
        return;
    } else if(1 != rows.length){
        alert("只能选择一个快递单。");
        return;
    }

    SubmitUtils.getJSON(
        "/replenishController/queryBalanceDetail.do",
        {"balanceId":rows[0].balanceId},
        function(json){
            if($.isEmptyObject(json)){
                alert("系统错误！");
                return;
            }
            if(!$.isEmptyObject(json.resultValue)){
                for(var key in json.resultValue){
                    $("#"+key).val(json.resultValue[key]);
                }
                $("#balancePic").attr("src",json.resultValue.picPath);
                if(null == balanceEditWin){
                    balanceEditWin = $.ligerDialog.open({title:"快递单详情",isHidden:true,height: 200,showMax:true, isResize: false, target:$("#balanceDetail") });
                    balanceEditWin.max();
                } else {
                    balanceEditWin.show();
                }
            } else {
                alert("没有需要修改的快递单！", function(){
                    balanceEditWin.close();
                });
            }
        }
    );
}
