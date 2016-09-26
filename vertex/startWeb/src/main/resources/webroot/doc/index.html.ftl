<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta http-equiv="Pragma" content="no-cache"/>
  <link rel="icon" type="image/x-icon" href="/doc/images/LasGIS.ico" />
  <link rel="stylesheet" type="text/css" href="/doc/css/main.css"/>
  <link rel="stylesheet" type="text/css" href="/doc/css/menu.css"/>
  <script type="text/javascript">ImagePrefix = "images/";</script>
  <script type="text/javascript" src="/js/jquery-1.12.0.js"></script>
  <script type="text/javascript" src="/doc/js/menu.js"></script>
  <script type="text/javascript" src="https://api-maps.yandex.ru/2.1/?lang=ru_RU"></script>
  <title>Главная страничка LasGIS</title>
</head>
<!--body class="marginzero" -->
<body>
  <div class="main">
    <div class="header">
      <div class="logotype">
        <img alt="Trade Mark LasGIS Association." src="/doc/images/TradeMark.jpg" title="Эта картинка неизменная для всех страничек сайта."/>
      </div>
      <div class="header-title">
        <div class="head-mini" title="Эта строка неизменная для всех страничек сайта.">
          Ассоциация развития программного комплекса <strong>LasGIS</strong> г.Омск
        </div>
        <div class="head-main" title="Заголовок должен меняться в зависимости от выбранной страницы. В данном случае это заголовок для 'Главной страницы'" >
          ${context.main.headMain}
        </div>
        <div id="menu" class="menu" title="Главное меню (в этой панели все пункты главного меню)">
          <#list context.main.mainMenu as menu>
            <div
            <#if menu.submenu??>
                onmouseover="selectMenu(this, '${menu.submenu.id}');"
                onmouseout="unselectMenu();"
            </#if>
                title="${menu.title!}">
              <a href="${menu.link}">${menu.text}</a>
            </div>
          </#list>
        </div>
      </div>
      <div class="header-addons">
        <a href="http://www.gismeteo.ru/towns/28698.htm"><img
          alt="GISMETEO.RU: график температуры на 10 дней для г. Омск"
          src="http://informer.gismeteo.ru/graph/G28698-1.GIF"
          border=0 width=200 height=100
        ></a>
      </div>
    </div>
    <#function iconClass level isOpen>
        <#if     level == 0><#if isOpen><#return "open-book"><#else><#return "close-book"></#if>
        <#elseif level == 1><#if isOpen><#return "open-book1"><#else><#return "close-book1"></#if>
        <#elseif level == 2><#if isOpen><#return "open"><#else><#return "close"></#if>
        <#elseif level == 3><#if isOpen><#return "open1"><#else><#return "close1"></#if>
        <#elseif level == 4><#return "page">
        <#elseif level == 5><#return "page1">
        </#if>
    </#function>
    <#macro mLeftMenu leftMenu>
      <#list leftMenu as menu>
        <div class="left-menu-item ${iconClass(menu.level, menu.open)}">
          <div class="left-menu-img level${menu.level}" level="level${menu.level}" opened="<#if menu.open>open<#else>close</#if>"></div>
          <a href="${menu.link}${menu.anchor!}" class="menu-anchor" data-link="${menu.link}" data-anchor="${menu.anchor!}">${menu.text}</a>
          <#if menu.submenu??>
            <div<#if !menu.open> style="display: none"</#if>>
              <@mLeftMenu menu.submenu/>
            </div>
          </#if>
        </div>
      </#list>
    </#macro>
    <div class="content">
      <div class="left-menu">
        <@mLeftMenu context.main.leftMenu/>
      </div>
      <div class="right-content"><#include "${context.main.documentName}"></div>
    </div>
    <div class="footer"></div>
  </div>
  <div

  <#list context.main.mainMenu as menu><#if menu.submenu??>
  <!-- start of layer-->
   <div
     class="PopMenu"
     style="display:none; overflow:visible"
     id="${menu.submenu.id}"
     onmouseover="overMenu();"
     onmouseout="unselectMenu();"
   >
     <table border="0" cellspacing="0" cellpadding="0" onmouseover="overMenu();">
       <#list menu.submenu.menu as submenu>
       <tr><td><a href="${submenu.link}" title="${submenu.title!}">${submenu.text}</a></td></tr>
       </#list>
     </table>
   </div>
   <!-- end of layer-->
  </#if></#list>
</body>
</html>
