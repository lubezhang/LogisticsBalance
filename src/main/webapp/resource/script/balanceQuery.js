var alert = function (content, title){
    $.ligerDialog.alert(content, title);
};

$(function(){
    $("#searchbtn").ligerButton({ click: showBalance});
});

function showBalance(){
    $.getJSON(
        "/replenishController/queryBalanceDetail.do",
        "balanceCode="+$("#sbalanceCode").val(),
        function(json){
            if(json){
                $("#balancePic").show().attr("src",json.picPath);
            } else {
                alert("查询快递单异常！");
            }
        }
    );
}