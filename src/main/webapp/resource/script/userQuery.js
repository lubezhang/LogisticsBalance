var alert = function (content, callBack){
    $.ligerDialog.alert(content, "处理结果","success",callBack);
};

var error = function (content, callBack){
    $.ligerDialog.alert(content, "处理结果","error",callBack);
};
var gridManager = null;
$(function(){
    $("#searchbtn").ligerButton({ click: function (){
        if (!gridManager) return;
        gridManager.setOptions(
            { parms: [
                { name: 'username', value: $("#s_username").val()},
                { name: 'operatorName', value: $("#s_operatorName").val()}
            ] }
        );
        gridManager.loadData(true);
    }
    });

    //初始化快递单列表
    $("#maingrid").ligerGrid({
        columns: [
//            { display: "主键", name: "balanceId", width: 120,isAllowHide:true },
            { display: "登录名", name: "username", width: 120 },
            { display: "用户", name: "operatorName", width: 320 }
        ],
        dataAction: 'server',
        pageSize: 20,
        toolbar: { items: [
            { id:"add", text: '添加', click: itemclick, icon: 'add' },
            { line: true },
            { id:"edit", text: '修改', click: itemclick, icon: 'edit' },
            { line: true },
            { id:"delete", text: '删除', click: itemclick, icon: 'delete' },
            { line: true }
        ]},
        url: '/user/queryUserList.do',
        sortName: 'operatorDate',
        width: '100%', height: '100%',
        heightDiff:0, checkbox: true,
        alternatingRow:true,rownumbers:true,
        selectRowButtonOnly:true,
        enabledSort:true

    });
    gridManager = $("#maingrid").ligerGetGridManager();
    $("#pageloading").hide();
    initForm();

//    $("#s_addrDateBegin").ligerDateEditor({ label: '收件日期', labelWidth: 60, labelAlign: 'right' });
//    $("#s_addrDateEnd").ligerDateEditor({ label: '至', labelWidth: 20, labelAlign: 'center' });
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
                "/user/addUser.do",
                decodeURIComponent($("#form1").serialize()),
                function(json){
                    if(json.success){
                        alert(json.message, function(){
                            userWin.hidden();
                            gridManager.loadData(true);
                        });
                    } else {
                        error(json.message,"处理信息");
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
        case "add":
            addUser();
            break;
        case "edit":
            editUser();
            break;
        case "delete":
            deleteUser();
            break;
        default:
            break;
    }
}
var userWin = null;
function addUser(){
    userWin = $.ligerDialog.open({title:"添加用户",height: 300,width:300,showMax:true, isResize: false, target:$("#userDetail") });
    var arrays = $("#form1").serializeArray();
    for(var key in arrays){
        $("#"+arrays[key].name).val("");
    }
}

function editUser(){

}
function deleteUser(){
    var rows = gridManager.getSelectedRows();
    var ids = "";
    for(var i = 0; i < rows.length; i++){
        if(0 == i){
            ids += rows[i].operatorId;
        } else {
            ids += ","+rows[i].operatorId;
        }
    }
    SubmitUtils.getJSON(
        "/user/deleteUser.do",
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