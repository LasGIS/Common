<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="GENERATOR" content="Microsoft FrontPage 4.0">
<link rel="stylesheet" type="text/css" href="/css/default.css">
<title>Control объект LasX &quot;Map&quot;</title>
</head>

<body>

<h1>Объект “Map”</h1>

<p>Объект <strong>Map</strong> служит для визуализации карты на
экране. Перед началом работы с объектом <strong>Map</strong>
необходимо предварительно связать его с главным
объектом <strong><a href="LasX_Main.html">Main</a></strong>.
Объект <strong>Map</strong> является оконным объектом и может
быть вставлен как “Control”.</p>

<p class="Remark">Свойства:</p>

<table>
  <tr>
    <th>Название</th>
    <th>[доступ]</th>
    <th>тип</th>
    <th>Описание</th>
  </tr>
  <tr>
    <td class="RefAdres"><a href="#Level">Level</a></td>
    <td>[rw]</td>
    <td>short</td>
    <td>уровень карты экрана.</td>
  </tr>
  <tr>
    <td class="RefAdres">MScale</td>
    <td>[rw]</td>
    <td>long</td>
    <td>масштаб вывода на экран.</td>
  </tr>
  <tr>
    <td class="RefAdres">AbsY1</td>
    <td>[rw]</td>
    <td>double</td>
    <td>координата левой границы карты (запад) в
    метрах местности.</td>
  </tr>
  <tr>
    <td class="RefAdres">AbsX2</td>
    <td>[rw]</td>
    <td>double</td>
    <td>координата верхней границы карты
    (север) в метрах местности.</td>
  </tr>
  <tr>
    <td class="RefAdres">AbsY2</td>
    <td>[rw]</td>
    <td>double</td>
    <td>координата правой границы карты
    (восток) в метрах местности.</td>
  </tr>
  <tr>
    <td class="RefAdres">AbsX1</td>
    <td>[rw]</td>
    <td>double</td>
    <td>координата нижней границы карты (юг) в
    метрах местности.</td>
  </tr>
  <tr>
    <td class="RefAdres">Interval</td>
    <td>[r]</td>
    <td>object <a HREF="LasX_MapObjectList.html">MapObjectList</a></td>
    <td>список графических объектов, выводимых
    на карту.</td>
  </tr>
  <tr>
    <td class="RefAdres">Rastr</td>
    <td>[r]</td>
    <td> object RASTER_DRAWIN</td>
    <td>класс для рисования растра.</td>
  </tr>
  <tr>
    <td class="RefAdres">TwinkleMapOs</td>
    <td>[r]</td>
    <td>object <a HREF="LasX_MapObjectList.html">MapObjectList</a></td>
    <td>список подсвеченных объектов.</td>
  </tr>
</table>

<p class="Remark">Методы:</p>
<strong><font COLOR="#008080">

<p>LinkMain </font></strong>(<font COLOR="#008080"><strong>Main</strong></font>) связать
объект <font COLOR="#008080"><strong>Map </strong></font>с главным объектом <font
COLOR="#008080"><strong>Main</strong></font><font COLOR="#008000">.</font></p>
<font COLOR="#008080"><strong>

<p>Redraw </strong></font>() перерисовать карту.</p>
<font COLOR="#008080"><strong>

<p>GotoPoint </strong></font>(<font COLOR="#008080"><strong>Level</strong></font>,<font COLOR="#008080"><strong>
MScale</strong></font>, <font COLOR="#008080"><strong>AbsY</strong></font>, <font COLOR="#008080"><strong>AbsX</strong></font>)<font
COLOR="#008080"><strong> </strong></font>перейти по координатам.</p>

<p class="Remark">События: </p>
<strong>

<u>
<font COLOR="#008080">

<p>MouseDown </font>(<font COLOR="#008080">AbsY</font>, <font COLOR="#008080">AbsX</font>,<font
COLOR="#008080"> X</font>, <font COLOR="#008080">Y</font>, MouseKey) нажата
кнопка мышки в окне карты.</p>
<font COLOR="#008080">

<p>MouseUp </font>(<font COLOR="#008080">AbsY</font>, <font COLOR="#008080">AbsX</font>,<font
COLOR="#008080"> X</font>, <font COLOR="#008080">Y</font>, MouseKey) отжата
кнопка мышки в окне карты.</p>
<font COLOR="#008080">

<p>MouseMove </font>(<font COLOR="#008080">AbsY</font>, <font COLOR="#008080">AbsX</font>,<font
COLOR="#008080"> X</font>, <font COLOR="#008080">Y</font>, MouseKey)
перемещение мышки в окне карты.</p>
<font COLOR="#008080">

<p>OnEndPaint</font> () сообщение владельцу о завершении
вывода карты. </p>

<h2></u>

</strong>Подробное описание свойств и методов:</h2>

<h4><a name="Level">Level</a></h4>

<p>Текущий уровень карты <strong>“<font
COLOR="#008080">Level</font>”</strong> может принимать значения от 1
до 9 включительно. При изменении значения
текущего уровня карты карта будет автоматически
перерисовываться.</p>

<p>Масштаб карты <strong>“<font COLOR="#008080">MScale</font>”</strong> может
принимать значения от 20 до 100 000 000. или масштаба (<font
COLOR="#008080"><strong>MScale</strong></font>) карта будет
перерисовываться. </p>

<p>Значения координат (<font COLOR="#008080"><strong>AbsX1</strong></font>, <font
COLOR="#008080"><strong>AbsY1</strong></font>, <font COLOR="#008080"><strong>AbsX2</strong></font>, <font
COLOR="#008080"><strong>AbsY2</strong></font>) определяют </p>
</body>
</html>
