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
  <script src="js/menu.js" type="text/javascript"></script>
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
    <div class="content">
      <div class="left-menu">
        <#list context.leftMenu as menu>
          <div class="left-menu-item <#if menu.level == 0><#--
          --><#if menu.open>open-book<#else>close-book</#if><#--
        --><#elseif menu.level == 1><#--
          --><#if menu.open>open-book1<#else>close-book1</#if><#--
        --><#elseif menu.level == 2><#--
          --><#if menu.open>open<#else>close</#if><#--
        --><#elseif menu.level == 3><#--
          --><#if menu.open>open1<#else>close1</#if><#--
        --><#elseif menu.level == 4>page<#--
        --><#elseif menu.level == 5>page1</#if>">
            <a href="${menu.target}${menu.link}">${menu.title}</a>
          </div>
        </#list>
      </div>
      <div class="right-content"><#include "/webroot/FrontPage/Main.html"></div>
    </div>
    <div class="footer"></div>
  </div>

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
