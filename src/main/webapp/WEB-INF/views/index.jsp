<%@ page contentType="text/html;charset=utf-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="/WEB-INF/taglib/c.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>森道尔快递单查询和对帐系统V1.0</title>
    <link href="/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="/lib/jquery/jquery-1.8.2.min.js" type="text/javascript"></script>
    <script src="/lib/ligerUI/js/ligerui.min.js" type="text/javascript"></script>
    <script src="/resource/script/index.js" type="text/javascript"></script>
    <script type="text/javascript" src="/resource/script/common.js"></script>
    <style type="text/css">
        body,html{height:100%;}
        body{ padding:0px; margin:0;   overflow:hidden;}
        .l-link{ display:block; height:26px; line-height:26px; padding-left:10px; text-decoration:underline; color:#333;}
        #pageloading{position:absolute; left:0px; top:0px; background:white url('loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
        .l-link{ display:block; line-height:22px; height:22px; padding-left:16px;border:1px solid white; margin:4px;}
        .l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;}
        .l-topmenu{ margin:0; padding:0; height:31px; line-height:31px; background:url('/lib/images/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #1D438B;  }
        .l-topmenu-logo{ color:#E7E7E7; padding-left:35px; line-height:26px;background:url('/lib/images/logo.png') no-repeat 10px 5px;}
        .l-topmenu-welcome{  position:absolute; height:24px; right:30px; top:0px;color:#E7E7E7;}
        .l-topmenu-welcome a{ color:#E7E7E7; text-decoration:underline}
        .l-topmenu-username{ color:#070A0C; font-weight:bold;}

    </style>
</head>
<body style="padding:0px;background:#EAEEF5;">
<input type="hidden" id="currLoginId" value="${loginId}">
<div id="pageloading"></div>
<div id="topmenu" class="l-topmenu">
    <div class="l-topmenu-logo"><h3>森道尔快递单查询和对帐系统V1.0</h3></div>
    <!--<div class="l-topmenu-welcome">-->
        <!--<a href="index.aspx" class="l-link2">服务器版本</a>-->
        <!--<span class="space">|</span>-->
        <!--<a href="https://me.alipay.com/daomi" class="l-link2" target="_blank">捐赠</a>-->
        <!--<span class="space">|</span>-->
        <!--<a href="http://bbs.ligerui.com" class="l-link2" target="_blank">论坛</a>-->
    <!--</div>-->

    <div class="l-topmenu-welcome">
        <span class="l-topmenu-username"></span>欢迎您  &nbsp;
        [<a href="javascript:void(0)">修改密码</a>] &nbsp;
        [<a href="javascript:void(0)">切换用户</a>]
        [<a href="/user/logout.do">退出</a>]
    </div>
</div>
<div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; ">
    <div position="left" title="主要菜单" id="accordion1">
        <!--<div title="功能列表" class="l-scroll">-->
        <!--<ul id="tree1" style="margin-top:3px;"></ul>-->
        <!--</div>-->
        <%--<div title="快递单维护">--%>
            <%--<div style=" height:7px;"></div>--%>
            <%--<a class="l-link" href="javascript:f_addTab('11','快递单补录','/page/balanceReplenish.html')">快递单补录</a>--%>
            <%--<a class="l-link" href="javascript:f_addTab('12','快递对账单','/page/balanceExport.html')">快递对账单</a>--%>
        <%--</div>--%>
        <%--<div title="快递单查询">--%>
            <%--<div style=" height:7px;"></div>--%>
            <%--<a class="l-link" href="javascript:f_addTab('21','快递单查询','/page/balanceQuery.html')">快递单查询</a>--%>
        <%--</div>--%>
        <%--<div title="系统管理">--%>
            <%--<div style=" height:7px;"></div>--%>
            <%--<a class="l-link" href="javascript:f_addTab('91','用户管理','/page/userQuery.html')">用户管理</a>--%>
            <%--<a class="l-link" href="javascript:f_addTab('92','快递单管理','/page/balanceManager.html')">快递单管理</a>--%>
        <%--</div>--%>
    </div>
    <div position="center" id="framecenter">
        <div tabid="home" title="我的主页" style="height:300px">
            <iframe frameborder="0" name="home" id="home" src="/welcome.html"></iframe>
        </div>
    </div>

</div>
<div style="height:32px; line-height:32px; text-align:center;">
    Copyright © 2008-2013 北京森道尔在线科技发展有限公司 北京市海淀区海淀路19-2号科城大厦3026号  技术支持热线：4006-917-916 电话：010-62617813  62563810
</div>
<div style="display:none"></div>
</body>
</html>
