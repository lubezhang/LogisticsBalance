/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-27
 * Time: 上午1:20
 * To change this template use File | Settings | File Templates.
 */

var tab = null;
var accordion = null;
var tree = null;
$(function (){
    //布局
    $("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });
    var height = $(".l-layout-center").height();
    //Tab
    $("#framecenter").ligerTab({ height: height });
    //面板
    $("#accordion1").ligerAccordion({ height: height - 24, speed: null });
    $(".l-link").hover(function (){
        $(this).addClass("l-link-over");
    }, function (){
        $(this).removeClass("l-link-over");
    });

    tab = $("#framecenter").ligerGetTabManager();
    accordion = $("#accordion1").ligerGetAccordionManager();
    tree = $("#tree1").ligerGetTreeManager();
    $("#pageloading").hide();
    initMenu();
});
function f_heightChanged(options){
    if (tab)
        tab.addHeight(options.diff);
    if (accordion && options.middleHeight - 24 > 0)
        accordion.setHeight(options.middleHeight - 24);
}
function f_addTab(tabid,text, url){
    tab.addTabItem({ tabid : tabid,text: text, url: url });
}

function initMenu(){
    SubmitUtils.getJSON(
        "/user/queryUserMenu.do",
        {},
        function(data){
            debugger;
        }
    );
}
