<html>

<head>
  <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="/css/default.css">
  <title>Тело графического объекта типа надпись (OText)</title>
</head>

<body>

<h2>1.6.Тело графического объекта типа надпись (OText)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="../../../webroot/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">OText</span> : public <a href="MapO_Object.html">MapObject</a> {<br>
      &nbsp;&nbsp; char * <span class="ProgTerm">Text</span>;&nbsp; <span class="ProgComment">// строка текста</span><br>
      &nbsp;&nbsp; long <span class="ProgTerm">Height</span>;&nbsp; <span class="ProgComment">// высота шрифта в см. местности</span><br>
      &nbsp;&nbsp; <a href="#MapO_eFont">eFont</a> * <span class="ProgTerm">Font</span>;&nbsp;
      <span class="ProgComment">// описание шрифта</span><br>
      &nbsp;&nbsp; <a href="MapO_eLine.html">eLine</a> * <span class="ProgTerm">ELine</span>;
      <span class="ProgComment">// элементарная линия, определяющая начало и
    направление вывода текста</span><br>
      }; <span class="ProgComment">// размер структуры 40+8=48 байт</span></p>
    </td>
  </tr>
</table>

<p>Графический объект типа надпись может быть
двух типов:</p>

<p>1 – надпись как самостоятельный графический
объект,</p>

<p>2 – надпись, принадлежащая к некоторому типу
графического объекта (например, принадлежит к 15-ой линии).</p>

<p>В первом случае в заголовке объекта (см. <a
href="MapO_Object.html">MapObject</a>) параметр <span class="Refer">&lt;Type&gt;</span>
содержит значение “4”, а параметр <span class="Refer">&lt;TextFlag&gt;</span>
при работе не учитывается. В этом случае
настройки шрифта хранятся в настройках
типо-объекта “Надпись” (как надпись 16-я).</p>

<p>Во втором случае параметр <span class="Refer">&lt;Type&gt;</span> и параметр
<span class="Refer">&lt;TypeNum&gt;</span> содержит значение скрепленного
типо-объекта (например, плоскость 17-я), а параметр
<span class="Refer">&lt;Text Flag&gt;</span> содержит значение “<strong><u>TRUE</u></strong>”. В этом
случае настройки шрифта хранятся в настройках
скрепленного типо-объекта.</p>

<p>В любом случае настройки могут быть
прикреплены к данному объекту. Тогда они
записываются вместе с объектом и хранятся в
параметре <span class="Refer">&lt;Font&gt;</span>. Если параметр <span class="Refer">&lt;Font&gt;</span>
содержит значение “<strong><u>NULL</u></strong>” это значит, что
настройки необходимо брать в настройках
типо-объекта, иначе необходимо пользоваться
настройками из параметра <span class="Refer">&lt;Font&gt;</span>.</p>

<p>Параметр <span class="Refer">&lt;Text&gt;</span> содержит текст в <strong>RichText</strong> формате.
Символ “0x00” означает окончание текста, два
символа “0x0D 0x0A” - перевод строки. В <strong>RichText</strong> формате
можно также задавать размер, формат и цвет
шрифта.</p>

<p>Параметр <span class="Refer">&lt;Height&gt;</span> содержит высоту шрифта в
сантиметрах местности. При выводе высота
пересчитывается в соответствии с масштабом
вывода.</p>

<p>Элементарная линия <span class="Refer">&lt;ELine&gt;</span> определяет
позицию надписи на карте. Если линия состоит из
одной точки, то начало надписи будет находиться в
этой точке. Если у линии – две точки, то вторая
задает направление вывода. Если линия состоит из
трех точек, то надпись будет располагаться по
окружности, образованной этими тремя точками. В
случае же большего числа точек, надпись будет
располагаться на полученном полигоне. </p>

<p class="Remark">Смотри также:</p>

<p class="Notice"><a href="MapO_Object.html">Общий заголовок графического
объекта <strong>(MapObject)</strong></a></p>

<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>

<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>

<h3><strong>1.6.1 <a name="MapO_eFont">Формат описания ШРИФТА</a></strong></h3>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="../../../webroot/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">struct <span class="ProgTerm">eFont</span> {<br>
      &nbsp;&nbsp; char * <span class="ProgTerm">Name</span>;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// название шрифта (Areal; Courier)</span><br>
      &nbsp;&nbsp; COLORREF <span class="ProgTerm">TextColor; </span><span class="ProgComment">// цвет текста</span><br>
      &nbsp;&nbsp; COLORREF <span class="ProgTerm">BkColor;&nbsp;&nbsp; </span><span class="ProgComment">// цвет фона</span><br>
      &nbsp;&nbsp; UINT <span class="ProgTerm">Bold</span>:1;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// если “TRUE” - толстый шрифт</span><br>
      &nbsp;&nbsp; UINT <span class="ProgTerm">Italic</span>:1;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// если “TRUE” - наклонный шрифт</span><br>
      &nbsp;&nbsp; UINT <span class="ProgTerm">Underline</span>:1;&nbsp;&nbsp;
      <span class="ProgComment">// если “TRUE” - шрифт с подчеркиванием</span><br>
      &nbsp;&nbsp; UINT <span class="ProgTerm">NoBK</span>:1;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// если “TRUE” - текст выводится без фона</span><br>
      }</p></td>
  </tr>
</table>
</body>
</html>
