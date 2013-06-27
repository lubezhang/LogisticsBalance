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
    initMenu();
});

function initLayout(){
    //布局
    $("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });
    var height = $(".l-layout-center").height();
    //Tab
    $("#framecenter").ligerTab({ height: height });
    //面板
    $("#menuContainer").ligerAccordion({ height: height - 24, speed: null });
    $(".l-link").hover(function (){
        $(this).addClass("l-link-over");
    }, function (){
        $(this).removeClass("l-link-over");
    });

    tab = $("#framecenter").ligerGetTabManager();
    accordion = $("#menuContainer").ligerGetAccordionManager();
    tree = $("#tree1").ligerGetTreeManager();
    $("#pageloading").hide();

}
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
            if(!data.resultValue){
                error("系统异常");
                return;
            }
            var menuContainer = $("#menuContainer");
            var i, j, mainMenuLength, childMenuLength;
            for(i = 0, mainMenuLength = data.resultValue.length; i < mainMenuLength; i++){
                var mainMenu = data.resultValue[i];
                var jqMainMenu = $("<div></div>").attr("title",mainMenu.title);

                var childMenuList = mainMenu.childMenu;
                for(j = 0, childMenuLength = childMenuList.length; j < childMenuLength; j++){
                    var code = childMenuList[j].code;
                    var title = childMenuList[j].title;
                    var url = childMenuList[j].url;
                    var jqChildMenu = $("<a></a>").attr("class","l-link")
                        .attr("href","javascript:void(0)").text(title)
                        .attr("onClick", "f_addTab('"+code+"','"+title+"','/page"+url+"')");
                    jqMainMenu.append(jqChildMenu);
                }
                menuContainer.append(jqMainMenu);
            }
            initLayout();
        }
    );
}
