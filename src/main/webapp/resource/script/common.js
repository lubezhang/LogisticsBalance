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