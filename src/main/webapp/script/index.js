/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-19
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 */

function loadBizPage(pagePath){
    debugger;
    $("#main_page").load(pagePath);
}

function importBalance(){
    $.post(
        "/replenishController/importPic.do",
        "sdfsdf=sdf",
        function(json){
            if(json){
                var strComplete = "";
                if(json.successful){
                    strComplete = "导入完成。成功【"+json.resultValue.completeCount+"】条，失败【"+json.resultValue.errorCount+"】条";
                    $.messager.alert("处理信息",strComplete,"info",function(){});
                } else {
                    strComplete = json.message;
                    $.messager.alert("处理信息",strComplete,"error",function(){});
                }
            }
        },
        "json"
    );
}

function testLicense(){
    $.ajax({
        type: "POST",
        url: "/replenishController/importPic.do",
        data: "name=John&location=Boston",
        dataType:"json",
        success: function(msg){
            debugger;
            if(typeof msg == "string"){
                var oJson = eval("("+msg+")");
                alert(oJson.name);
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            debugger;
            alert("XMLHttpRequest:" +XMLHttpRequest+" \n\n textStatus:"+textStatus+" \n\n errorThrown:"+errorThrown);
        }
    });
}


function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
    if (!s) return new Date();
    var ss = s.split('-');
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}