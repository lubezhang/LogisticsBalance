var SubmitUtils = {
    getJSON:function(url, data, callBack){
        $.post(
            url+ "?random=" + new Date().getTime(),
            data,
            callBack,
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