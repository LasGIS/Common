<h2>1.5.Тело графического объекта типа плоскость
(OAreal)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">OAreal:</span> public <a href="MapO_Object.html">MapObject</a> {<br>
      &nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">NumELine</span>;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// число элементарных линии</span><br>
      &nbsp;&nbsp;&nbsp; WORD  <span class="ProgTerm">NumELineMax</span>; <span class="ProgComment">// Размер массива ELine</span><br>
      &nbsp;&nbsp;&nbsp; <a href="MapO_eLine.html">eLine</a> * <span class="ProgTerm">ELine</span>;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// массив элементарных линий</span><br>
      }; <span class="ProgComment">// размер структуры 40+8=48 байт</span></p></td>
  </tr>
</table>

<p>Плоскость является масштабируемым объектом
карты. Примером плоскости может являться здание,
квартал, лес и так далее.</p>

<p>Объект плоскость состоит из заголовка и
массива элементарных линий, составляющих
плоскость. Структура плоскости совпадает со
структурой <a href="MapO_OLine.html">объекта типа линия</a> за
исключением способа рисования объекта и
интерпретации элементарных линий.</p>

<p>Здесь линия интерпретируется как полигон
точек, задающий внутреннюю область объекта.
Дополнительные линии представляют собой <u><strong>
включения</strong></u> в основную область или <u><strong> внешние</strong></u>
территории.</p>

<p>Обход по границе основной области и внешних
территорий должны производиться по часовой
стрелке, а обход включений – против часовой
стрелки.</p>

<p class="Remark"><strong>Смотри также:</strong></p>

<p class="Notice"><a href="MapO_Object.html">Общий заголовок графического
объекта <strong>(MapObject)</strong></a></p>

<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>

<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>
