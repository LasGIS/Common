<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta http-equiv="Pragma" content="no-cache"/>
  <link rel="icon" type="image/x-icon" href="images/LasGIS.ico" />
  <link rel="stylesheet" type="text/css" href="css/main.css"/>
  <link rel="stylesheet" type="text/css" href="css/menu.css"/>
  <script type="text/javascript">ImagePrefix = "images/";</script>
  <script type="text/javascript" src="/js/jquery-1.12.0.js"></script>
  <script type="text/javascript" src="/js/menu.js"></script>
  <title>Главная страничка LasGIS</title>
</head>
<!--body class="marginzero" -->
<body>
  <div class="main">
    <div class="header">
      <div class="logotype">
        <img alt="Trade Mark LasGIS Association." src="images/TradeMark.jpg" title="Эта картинка неизменная для всех страничек сайта."/>
      </div>
      <div class="header-title">
        <div class="head-mini" title="Эта строка неизменная для всех страничек сайта.">
          Ассоциация развития программного комплекса <strong>LasGIS</strong> г.Омск
        </div>
        <div class="head-main" title="Заголовок должен меняться в зависимости от выбранной страницы. В данном случае это заголовок для 'Главной страницы'" >
          ${context.headMain}
        </div>
        <div id="menu" class="menu" title="Главное меню (в этой панели все пункты главного меню)">
            <div
              onmouseover="selectMenu(this, 'mainLayer');"
              onmouseout="unselectMenu();"
              title="Главная страница"
            >
              <a href="index.html">Главная</a>
            </div><div
              onmouseover="selectMenu(this, 'historyLayer');"
              onmouseout="unselectMenu();"
              title="История создания всего проекта LasGIS, начиная с 1990 года"
            >
              <a href="History.html">История создания</a>
            </div><div
              onmouseover="selectMenu(this, 'documentationLayer');"
              onmouseout="unselectMenu();"
              title="Оригинальный сборник документации"
            >
              <a href="LasGIS_DOC/Document_LasGIS.html">Документация</a>
            </div><div
              title="Здесь всё, что касается текущих проектов (LGView, Geo, MakeRastr ...)."
            >
              <a href="LasGIS_DOC/Document_LasGIS.html">Проекты</a>
            </div>
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
    <#macro mLeftMenu mainMenu>
      <#list mainMenu as menu>
        <div class="left-menu-item ${iconClass(menu.level, menu.open)}">
          <div class="left-menu-img level${menu.level}" level="level${menu.level}" opened="<#if menu.open>open<#else>close</#if>"></div>
          <a href="${menu.target}${menu.link}">${menu.title}</a>
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
        <@mLeftMenu context.leftMenu/>
      </div>
      <div class="right-content"><#include "/templates/front/main.ftl"></div>
    </div>
    <div class="footer"></div>
  </div>

  <!-- start of layer-->
   <div
     class="PopMenu"
     style="display:none; overflow:visible"
     id="mainLayer"
     onmouseover="overMenu();"
     onmouseout="unselectMenu();"
   >
     <table border="0" cellspacing="0" cellpadding="0" onmouseover="overMenu();">
       <tr><td><a
         href='javascript:LoadUrl("FrontPage/Main.html", "FrontPage/Menu.html", "Программный комплекс LasGIS")'
       >Программный комплекс 'LasGIS'</a></td></tr>
       <tr><td><a
         href='javascript:LoadUrl("FrontPage/Map.html", "FrontPage/Menu.html", "База Геоданных ПК LasGIS")'
       >База Геоданных ПК 'LasGIS'</a></td></tr>
       <tr><td><a
         href='javascript:LoadUrl("FrontPage/Accesses.html", "FrontPage/Menu.html", "Способы доступа ПК LasGIS")'
       >Способы доступа ПК 'LasGIS'</a></td></tr>
     </table>
   </div>
   <!-- end of layer-->
   <!-- start of layer-->
   <div
     class="PopMenu"
     style="display:none; overflow:visible"
     id="documentationLayer"
     onmouseover="overMenu();"
     onmouseout="unselectMenu();"
   >
     <table border="0" cellspacing="0" cellpadding="0" onmouseover="overMenu();">
       <tr><td><a
         href='javascript:LoadUrl("LasGIS_DOC/default.html", "LasGIS_DOC/Menu.html", "Документация по программному комплексу LasGIS")'
         title="Общая документация по LasGIS для разных подпроектов."
       >Документация по LasGIS</a></td></tr>
       <tr><td><a
         href='javascript:LoadUrl("LasGIS_DOC/LasX/LasX.html", "LasGIS_DOC/LasX/Menu.html", "Структура объектной модели LasX")'
       >Структура объектной модели LasX (ActiveX object)</a></td></tr>
       <tr><td><a
         href='javascript:LoadUrl("LasGIS_DOC/MapO/MapO.html", "LasGIS_DOC/MapO/Menu.html", "Формат графического объекта (C++)")'
       >Формат графического объекта (C++)</a></td></tr>
       <tr><td><a
         href='javascript:LoadUrl("LasGIS_DOC/Container/Container.html", "LasGIS_DOC/Container/Menu.html", "Внутренний формат файла-контейнера")'
       >Внутренний формат файла-контейнера (C++)</a></td></tr>
     </table>
   </div>
   <!-- end of layer-->
   <!-- start of layer-->
   <div
     class="PopMenu"
     style="display:none; overflow:visible"
     id="historyLayer"
     onmouseover="overMenu();"
     onmouseout="unselectMenu();"
   >
     <table border="0" cellspacing="0" cellpadding="0" onmouseover="overMenu();">
       <tr><td><a
         href='javascript:LoadUrl("Explanation.html", "ExplanationMenu.html")'
       >Пояснения к HTML</a></td></tr>
     </table>
   </div>
   <!-- end of layer-->
</body>
</html>
