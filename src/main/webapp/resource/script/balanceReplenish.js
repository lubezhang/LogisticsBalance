//列表结构
var alert = function (content, title){
    $.ligerDialog.alert(content, title);
};
var gridManager = null;
$(function(){
    $("#searchbtn").ligerButton({ click: function (){
        if (!gridManager) return;
        gridManager.setOptions(
            { parms: [{ name: 'balanceCode', value: $("#sbalanceCode").val()}] }
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
            { display: "修改日期", name: "operatorDate", width: 120 },
            { display: "收件日期", name: "addresseeDate", width: 120 }
        ],
        dataAction: 'server',
        pageSize: 20,
        toolbar: { items: [
            { id:"import", text: '导入快递单', click: itemclick, icon: 'add' },
            { line: true },
            { id:"update", text: '补录', click: itemclick, icon: 'modify' }
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
//            alert("Submitted!")
            $.getJSON(
                "/replenishController/updateBalanceDetail.do?random="+new Date().getTime(),
                $("#form1").serialize(),
                function(json){
                    debugger;
                    if(json.successful){
                        $.ligerDialog.alert("修改成功", "处理信息",'',function(){
                            editNext();
                        });
                    } else {
                        alert("修改失败"+json.successful,"处理信息");
                    }
                }
            );
        }
    });
    $("form").ligerForm();
    $(".l-button-test").click(function (){
        alert(v.element($("#txtName")));
    });
}

//菜单按钮单击事件
function itemclick(item){
    switch (item.id){
        case "import":
            importBalance();
            break;
        case "update":
            editNext();
            break;
        default:
            break;
    }
}
//导入快递单图片
function importBalance(){
    $.getJSON(
        "/replenishController/importPic.do",
        "",
        function(json){
            if(json){
                var strComplete = "";
                if(json.successful){
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

var win = null;
function editNext(){
    $.getJSON(
        "/replenishController/queryNextDetail.do?random="+new Date().getTime(),
        "",
        function(json){
            debugger;
            if(json){
                for(var key in json){
                    $("#"+key).val(json[key]);
                }
                $("#balancePic").attr("src",json.picPath);
                if(null == win){
                    win = $.ligerDialog.open({title:"快递单详情",height: 200,showMax:true, isResize: true, target:$("#balanceDetail") });
                    win.max();
                } else {
                    win.show();
                }
            } else {
                alert("没有需要补录的快递单！");
            }
        }
    );
}