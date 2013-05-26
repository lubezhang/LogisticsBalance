var SubmitUtils = {
    getJSON:function(url, data, callBack){
        $.post(
            url+ "?random=" + new Date().getTime(),
            data,
            function(json){
                if($.isEmptyObject(json)){
                    error("系统错误，请联系系统管理员！");
                    return;
                }
                if(json.success){
                    callBack();
                } else {
                    if(json.returnCode === "90"){
                        error(json.message, function(){
                            top.window.location.href = "/";
                        });
                    } else {
                        error(json);
                    }
                }
            },
            "json"
        );
    }
};

var alert = function (content, callBack){
    $.ligerDialog.alert(content, "处理结果","success",callBack);
};

var error = function (content, callBack){
    $.ligerDialog.alert(content, "处理结果","error",callBack);
};