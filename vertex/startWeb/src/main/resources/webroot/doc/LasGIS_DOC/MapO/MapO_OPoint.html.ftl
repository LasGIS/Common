<h2>1.2.Тело графического объекта типа точка (OPoint)</h2>

<table cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><img src="/doc/images/forefinger.jpg" width="16" height="16"></td>
    <td><p class="Prog">class <span class="ProgTerm">OPoint</span>: public <a
    href="MapO_Object.html">MapObject</a> {<br>
    &nbsp;&nbsp;&nbsp; long <span class="ProgTerm">X</span>; <span class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    // Координата X объекта на местности</span><br>
    &nbsp;&nbsp;&nbsp; long <span class="ProgTerm">Y</span>; <span class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    // Координата <strong>Y </strong>объекта на местности</span><br>
    &nbsp;&nbsp;&nbsp; long <span class="ProgTerm">Prec</span>; <span class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;
    // Точность координат <strong>X</strong> и <strong>Y</strong></span><br>
    &nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">VisibleH</span>;&nbsp;<span class="ProgComment">
    // Число видимых высот точки</span><br>
    &nbsp; &nbsp; WORD <span class="ProgTerm">NumH</span>; <span class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;
    // Число высот точки</span><br>
    &nbsp;&nbsp;&nbsp; long * <span class="ProgTerm">H</span>; <span class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    // Ссылка на массив высот точки</span><br>
    }; <span class="ProgComment">// размер структуры 40+20=60 байт</span></td>
  </tr>
</table>

<p>Точка является внемасштабным знаком на карте.
Примером точки может являться колодец,
одиноко-стоящее дерево, знак населенного пункта
и так далее. Объект типа точка состоит из
заголовка, координат точки и определенного в
заголовке числа высот:</p>

<p>Здесь:</p>

<p>Параметры <span class="Refer">&lt;X&gt;</span> и <span class="Refer">&lt;Y&gt;</span> составляют
координаты точки объекта. Координата <span class="Refer">&lt;<strong>X</strong>&gt;</span>
определяет широту точки, а координата <span class="Refer">&lt;<strong>Y</strong>&gt;</span> -
долготу точки обьекта в сантиметрах местности,
если объект находится на уровнях от 0-го до 5-го
(масштаб 1:200 000) включительно и в 1/10000000 долях
градуса, если объект находится на уровнях выше от
5-го.</p>

<p><span class="Refer">&lt;<strong>Prec</strong>&gt;</span> точность координат
<span class="Refer">&lt;<strong>X</strong>&gt;</span> и <span class="Refer">&lt;<strong>Y</strong>&gt;</span>
определяется как погрешность определения
координаты в [см] местности. Точность
определяется:</p>

<p>при скалывании мышкой - автоматически по
масштабу изображения на экране,</p>

<p>при вводе с клавиатуры – погрешность считается
нулевой.</p>

<p>Параметр<strong> </strong><span class="Refer">&lt;<strong>VisibleH</strong>&gt;</span> содержит число
первых точек, которые будут выведены на карту.</p>

<p>Параметр <span class="Refer">&lt;<strong>NumH</strong>&gt;</span> содержит полное число
высот данной точки. Этот параметр определяет
размерность массива высот точки <span class="Refer">&lt;<strong>H</strong>&gt;</span>.</p>

<p><span class="Refer">&lt;<strong>H</strong>&gt;</span> - массив высот точки.</p>

<p class="Remark">Смотри также:</p>
<p class="Notice"><a href="MapO_Object.html">Общий заголовок
графического объекта (<strong>MapObject</strong>)</a></p>

<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>

<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>
