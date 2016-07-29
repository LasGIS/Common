<html>

<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<meta NAME="GENERATOR" CONTENT="Microsoft FrontPage 4.0">
<link rel="stylesheet" type="text/css" href="/css/default.css">
<title>Тело графического объекта типа полоса (OStrip)</title>
</head>

<body>

<h2>1.4.Тело графического объекта типа полоса (OStrip)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="../../../webroot/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">OStrip:</span> public <a href="MapO_Object.html">MapObject</a> {<br>
      &nbsp;&nbsp; WORD <span class="ProgTerm">NumELine</span>;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// число элементарных линии</span><br>
      &nbsp;&nbsp; WORD <span class="ProgTerm">NumELineMax</span>; <span class="ProgComment">// Размер массива ELine</span><br>
      &nbsp;&nbsp; <a href="MapO_eLine.html">eLine</a> * <span class="ProgTerm">ELine</span>;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// массив элементарных линий</span><br>
      }; <span class="ProgComment">// размер структуры 40+8=48 байт</span></p>
    </td>
  </tr>
</table>

<p>Полоса является масштабируемым объектом карты.
Примером полосы может являться дорога, улица,
река и так далее, имеющие отдельное описание
правой стороны, левой стороны и центральной
линии. </p>

<p>Объект полоса состоит из заголовка и массива
элементарных линий, составляющих полосу:
Структура полосы совпадает со структурой <a
href="MapO_OLine.html">объекта типа линия</a> за исключением
способа рисования объекта и интерпретации
элементарных линий.</p>

<p><span class="Refer">Первая</span> элементарная линия полосы определяет
левую границу, <span class="Refer">вторая</span> - правую границу, а <span class="Refer">третья</span> -
центральную линию полосы. Третья линия полосы
может отсутствовать, в этом случае центральная
линия будет не определена.</p>

<p>При рисовании полосы происходит закраска
полигона, образованного левой границей (первая
элементарная линия) и правой границей (вторая
элементарная линия) полосы. При этом правая
граница будет перевернута. Затем рисуются
границы полосы. Промежутки между началами и
концами границ не заполняются.</p>

<p class="Remark"><strong>Смотри также:</strong></p>

<p class="Notice"><a href="MapO_Object.html">Общий заголовок графического
объекта <strong>(MapObject)</strong></a></p>

<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>

<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>
</body>
</html>
