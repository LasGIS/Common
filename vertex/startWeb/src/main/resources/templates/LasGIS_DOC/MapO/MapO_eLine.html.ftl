<html>

<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<meta NAME="GENERATOR" CONTENT="Microsoft FrontPage 4.0">
<link rel="stylesheet" type="text/css" href="/css/default.css">
<title>Формат элементарной линии (eLine)</title>
</head>

<body>
<sup>

<h2></sup>1.7. Формат элементарной линии (eLine)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="../../../webroot/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">eLine</span> {<br>
    &nbsp;&nbsp; WORD <span class="ProgTerm">Num</span>;&nbsp;&nbsp;&nbsp; <span
    class="ProgComment">// число точек в линии</span><br>
    &nbsp;&nbsp; WORD <span class="ProgTerm">NumMax</span>; <span class="ProgComment">//
    максимальное число точек в линии</span><br>
    &nbsp;&nbsp; <a href="MapO_ePoint.html">ePoint</a> * <span class="ProgTerm">Pnt</span>;
      <span class="ProgComment">// массив элементарных
    точек</span><br>
    }; <span class="ProgComment">// размер структуры 8 байт</span></td>
  </tr>
</table>

<p>Элементарная линия входит в состав графических
объектов линия, полоса и плоскость. </p>

<p>Здесь:</p>

<p><span class="Refer">&lt;Num&gt;</span><strong> - </strong>число точек реально
присутствующих в линии,</p>

<p><span class="Refer">&lt;NumMax&gt;</span><strong> - </strong>число точек,
выделенных при создании массива <span class="Refer">&lt;Pnt&gt;</span>,</p>

<p><span class="Refer">&lt;Pnt&gt;</span><strong> - </strong>массив точек
элементарной линии.</p>

<p>При создании элементарной линии оператором <strong>new
</strong>создается массив точек с размером <span class="Refer">&lt;NumMax&gt;</span>
на несколько значений больше чем <span class="Refer">&lt;Num&gt;</span>.
При увеличении массива <span class="Refer">&lt;Pnt&gt;</span>
меньше, чем <span class="Refer">&lt;NumMax&gt;</span> массив не <em>переаллокируется</em>,
а лишь используются резервные точки массива и
изменяется размер <span class="Refer">&lt;Num&gt;</span>. Таким
образом при редактировании массива экономиться
время на добавлении новых точек. </p>

<p class="Remark">Смотри также:</p>

<p class="Notice"><a href="MapO_Object.html">Общий заголовок графического
объекта <strong>(MapObject)</strong></a></p>

<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (ePoint)</a></p>
</body>
</html>
