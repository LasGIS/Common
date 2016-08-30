<h2>1.8.Формат элементарной точки (ePoint)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="/doc/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">ePoint</span> {<br>
    &nbsp;&nbsp; long&nbsp;&nbsp; <span class="ProgTerm">X</span>;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp; <span
    class="ProgComment">// координата <span class="Refer">X</span> точки в
    см. местности.</span><br>
    &nbsp;&nbsp; long&nbsp;&nbsp; <span class="ProgTerm">Y</span>;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp; <span
    class="ProgComment">// координата <span class="Refer">Y</span> точки в
    см. местности.</span><br>
    &nbsp;&nbsp; long&nbsp;&nbsp; <span class="ProgTerm">H</span>;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp; <span
    class="ProgComment">// высота точки над уровнем моря (в см.
    местности).</span><br>
    &nbsp;&nbsp; long&nbsp;&nbsp; <span class="ProgTerm">Prec</span>;&nbsp;&nbsp;
      &nbsp; <span
    class="ProgComment">// точность координат (в см. местности).</span><br>
    &nbsp;&nbsp; short&nbsp; <span class="ProgTerm">Num</span>;&nbsp;&nbsp;&nbsp;
      &nbsp; <span
    class="ProgComment">// номер точки (общий для всего
    объекта).</span><br>
    &nbsp;&nbsp; USHORT <span class="ProgTerm">Branch:1</span>; <span class="ProgComment">//
    признак примыкания к точке других линий, полос
    или плоскостей</span><br>
    &nbsp;&nbsp; USHORT <span class="ProgTerm">Blank:1</span>;&nbsp; <span class="ProgComment">//
    признак пропуска линии или границы от этой точки
    до следующей</span><br>
    &nbsp;&nbsp; USHORT Free:14;&nbsp; <span class="ProgComment">// пока пусто</span><br>
    }; <span class="ProgComment">// размер структуры 20 байт</span></td>
  </tr>
</table>

<p>Элементарная точка входит в состав
элементарной линии графических объектов линия,
полоса и плоскость. </p>

<p>Параметры <span class="Refer">&lt;X&gt;</span> и <span class="Refer">&lt;Y&gt;</span>
составляют координаты точки объекта. Координата <span
class="Refer">&lt;X&gt;</span> определяет широту точки (снизу
вверх на карте или с юга на север), а координата <span
class="Refer">&lt;Y&gt;</span> - долготу точки (слева направо
или с запада на восток) в сантиметрах местности,
если объект находится на уровнях от 0-го до 5-го
(масштаб 1:200 000) включительно и в 1/10000000 долях
градуса, если объект находится выше 5-го уровня.</p>

<p><span class="Refer">&lt;Prec&gt; - </span>точность координат <span
class="Refer">&lt;X&gt;</span> и <span class="Refer">&lt;Y&gt;</span>
определяется как погрешность определения
координаты в см. местности. Точность
определяется:</p>

<p>при скалывании мышкой - автоматически по
масштабу изображения на экране,</p>

<p>при вводе с клавиатуры – погрешность считается
нулевой.</p>

<p><span class="Refer">&lt;Num&gt;</span> - определяет номер данной
точки, применительно для всего объекта. Номер
точки может быть полезен при сборе объекта из
частей с разных планшетов, а также участвует в
документах при определении границ
землепользования.</p>

<p>Флаг <span class="Refer">&lt;Branch&gt;</span> указывает на то, что
в данной точке к данному объекту примыкает
другой объект с теми - же свойствами например
смежный земельный участок или ответвление к
трубопроводу). Этот признак поможет при решении
сетевых задач.</p>

<p>Флаг <span class="Refer">&lt;Blank&gt;</span> указывает на то, что
следующая линия объекта не показывается на
карте. Это необходимо, например, при рисовании
отдельных участков реки или дороги. </p>

<p class="Remark">Смотри также:</p>

<p class="Notice"><a href="MapO_Object.html">Общий заголовок графического
объекта <span class="Refer">(MapObject)</span></a></p>

<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<span
class="Refer">eLine</span>)</a></p>
