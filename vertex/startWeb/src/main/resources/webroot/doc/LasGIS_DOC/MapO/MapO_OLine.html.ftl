<h2>1.3.Тело графического объекта типа линия (OLine)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="/doc/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">OLine</span>: public <a href="MapO_Object.html">MapObject</a> {<br>
      &nbsp;&nbsp; WORD <span class="ProgTerm">NumELine</span>;&nbsp;&nbsp;&nbsp; <span class="ProgComment">// число элементарных линии</span><br>
      &nbsp;&nbsp; WORD <span class="ProgTerm">NumELineMax</span>; <span class="ProgComment">// Размер массива ELine</span><br>
      &nbsp;&nbsp; <a href="MapO_eLine.html">eLine</a> * <span class="ProgTerm">ELine</span>;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// массив элементарных линий</span><br>
      }; <span class="ProgComment">// размер структуры 40+8=48 байт</span></p>
    </td>
  </tr>
</table>

<p>Линия является масштабируемым объектом карты.
Примером линии может являться дорога, улица, река
и так далее. Объект линия состоит из заголовка и
списка элементарных линий, составляющих линию:</p>

<p>В общем случае линия может состоять из
нескольких линий, например, улица, которая
проходит через площадь. В этом случае первая
элементарная линия кончается на одном краю
площади, а вторая элементарная линия начинается
на другом краю площади.</p>

<p>Параметр <span class="Refer">&lt;NumЕLine&gt;</span> определяет число
элементарных линий, составляющих линию.</p>

<p>Параметр <span class="Refer">&lt;NumELineMax&gt;</span> определяет размер
массива <span class="Refer">&lt;eLine&gt;</span> - число элементарных линий,
составляющих линию.</p>

<p>Параметр <span class="Refer">&lt;eLine&gt;</span> указывает на массив
элементарных линий, которые составляют объекта
типа линия. Длина массива может меняться в
процессе редактирования объекта. При этом в
случае увеличения размера больше, чем <span class="Refer">&lt;NumELineMax&gt;</span>
необходимо увеличить массив функцией <strong>Realloc</strong>.
При изменении размера меньше, чем <span class="Refer">&lt;NumELineMax&gt;</span>
изменяется лишь параметр <span class="Refer">&lt;NumЕLine&gt;</span>.</p>

<p class="Remark"><strong>Смотри также:</strong></p>

<p class="Notice"><a href="MapO_Object.html">Общий заголовок графического
объекта <strong>(MapObject)</strong></a></p>

<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>

<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>
