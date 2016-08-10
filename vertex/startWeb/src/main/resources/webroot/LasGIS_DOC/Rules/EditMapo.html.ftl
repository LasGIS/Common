<h1>Правила редактирования графических объектов
карты.</h1>

<p>Данные правила распространяются на следующие
объекты карты:

<ul>
  <li><span class="Refer">точечные,</span></li>
  <li><span class="Refer">линейные,</span></li>
  <li><span class="Refer">плоскостные и </span></li>
  <li><span class="Refer">полосные</span> </li>
</ul>

<p>Предусматривается два режима редактирования
объектов:

<ol>
  <li><span class="Refer">Создание нового объекта</span>; </li>
  <li><span class="Refer">Редакция уже существующего объекта</span>.</li>
</ol>

<p>Для редакции предусматривается использовать
графифеский манипулятор &quot;мышь&quot; (<span class="Refer">правую</span>
и <span class="Refer">левую</span> кнопки), а также
дополнительные кнопки клавиатуры: <span class="Refer">&lt;Ctrl&gt;</span>,
<span class="Refer">&lt;Alt&gt;</span> и <span class="Refer">&lt;Shift&gt;</span> для
временной смены режима.&nbsp;</p>

<p>Кроме этого различаются такие моменты, как
попадание мышкой в точку, отрезок, тело
плоскостного или полосного объекта и попадание
мимо объекта.</p>

<h2><a name="Container">1. Общие правила.</a></h2>

<p>Предполагаются следующие общие правила при
редактировании объектов:

<ol>
  <li>Кнопка <span class="Refer">&lt;Alt&gt; </span>клавиатуры служит
    для временной смены режима (между&nbsp; <span class="Refer">&quot;Созданием
    нового объекта&quot;</span> и <span class="Refer">&quot;Редакцией
    существующего объекта&quot;</span>).</li>
  <li>Кнопка <span class="Refer">&lt;Ctrl&gt; </span>клавиатуры служит
    для перемещения точек объекта и самого
    существующего объекта совместно с привязанными
    точками соседних объектов..</li>
  <li>Кнопка <span class="Refer">&lt;Shift&gt; </span>клавиатуры служит
    для вращения существующего объекта.</li>
  <li><span class="Refer">Правая кнопка мыши</span> используется
    в основном для вызова POP-UP меню, в котором должны
    находиться добавочные функции работы с объектом.</li>
</ol>

<p>В общем случае существуют следующие
комбинации:

<ul>
  <li>Режим <u><strong>создания</strong></u> (или режим
    редактирования с доп.клавишей <span class="Refer">&lt;Alt&gt;</span>):<ul>
      <li><span class="Refer">Без доп.клавиш</span> - создаются новые
        точки объекта, которые приклеиваются к
        существующим.</li>
      <li><span class="Refer">&lt;Ctrl&gt; </span>- создаются новые точки
        объекта, которые перетаскивают соседние точки
        чужих объектов в новое место.</li>
    </ul>
  </li>
  <li>Режим <u><strong>редактирования</strong></u> (или режим
    создания с доп.клавишей <span class="Refer">&lt;Alt&gt;</span>):<ul>
      <li><span class="Refer">Без доп.клавиш</span> - перетаскиваются
        существующие точки объекта или создаются и
        перетаскиваются новые, которые образуются в
        промежутке между старыми. При&nbsp; этом
        перетаскиваемые точки приклеиваются к
        существующим.</li>
      <li><span class="Refer">&lt;Ctrl&gt; </span>- перетаскиваются
        существующие точки объекта или создаются и
        перетаскиваются новые, которые образуются в
        промежутке между старыми. При&nbsp; этом
        перемещаемые точки перетаскивают соседние точки
        чужих объектов в новое место.</li>
    </ul>
  </li>
  <li>Оба режима<u><strong> создания</strong></u> и <u><strong>редактирования</strong></u><ul>
      <li><span class="Refer">&lt;Shift&gt; </span>- Происходит перемеещение
        объекта (левой - перемещение; правой - вращение).
        При этом привязка точек, если она была, теряется.</li>
      <li><span class="Refer">&lt;Shift&gt; + &lt;Ctrl&gt; </span>- Происходит
        перемеещение объекта (левой - перемещение; правой
        - вращение). При этом соседние точки чужих
        объектов следуют за привязанными точками.</li>
    </ul>
  </li>
</ul>

<h3><a name="ModeMaking">1.1. Режим создания объекта</a></h3>

<p>При удержании дополнительной кнопки <span class="Refer">&lt;Alt&gt;</span>
система переходит в <a href="#ModeEdit">режим редакции
объекта</a>, который описан далее.</p>

<p>При удержании дополнительной кнопки <span class="Refer">&lt;Shift&gt;</span>
система переходит в <a href="#ModeDislocation">режим
перемещения и вращения объекта</a>.</p>

<table border="1" cellspacing="0">
  <tr>
    <th colspan="2">мышка</th>
    <th rowspan="2">доп. key</th>
    <th rowspan="2">событие</th>
  </tr>
  <tr>
    <th>клавиша</th>
    <th>статус</th>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4">любой объект</th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeMaking_POP_UP">Вызов
    выпадающего меню</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td rowspan="2">Если под мышкой находится некоторый
    объект или несколько объектов, то появляется
    всплывающее меню (pop-up) и предлагается выполнить
    дополнительные функции.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">двойной щелчок</span></td>
    <td align="center">-</td>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4"><strong>Точечный объект</strong></th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeMaking_Create_Point">Создание
    точечного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td>Новый точечный объект создаётся в памяти.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center">-</td>
    <td>Показывается перемещение нового точечного
    объекта.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center">-</td>
    <td>Производится поиск видимых точек в зоне
    захвата.<p>Если таковые точки находятся, то
    координаты нового точечного объекта получают
    координаты первой захваченной точки, иначе
    координаты нового точечного объекта остаются
    мышкиными.</p>
    <p>После чего новый точечный объект записывается
    в карту.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a
    name="ModeMaking_Create_Point_Adjacent">Создание точечного объекта
    со смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Новый точечный объект создаётся в памяти.
    Произходит поиск точек соседних объектов,
    попадающих в зону захвата.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Показывается перемещение нового точечного
    объекта и захваченных точек (резиновая нить).</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Создаётся точечный объект и все захваченные
    точки объектов перемещаются в новое место</td>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4">Линейный, полостный&nbsp; и
    плоскостной объекты</th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeMaking_Set_Line">Задание
    точек линейного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td>Для нового объекта создаётся новая точка.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center">-</td>
    <td>Показывается перемещение (резиновая нить)
    новой точки. Если это линейный объект, то одна
    линия от предпоследней точки до&nbsp; новой точки.
    Если это плоскостной объект, то добавляется
    линия отновой точки до первой. </td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center">-</td>
    <td>Производится поиск видимых точек в зоне
    захвата.<p>Если таковые точки находятся, то
    координаты новой точки объекта получают
    координаты первой захваченной точки, иначе
    координаты новой точки объекта остаются
    мышкиными.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeMaking_Set_Line_Adjacent">Задание
    точек линейного объекта со смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Для нового объекта создаётся новая точка.
    Произходит поиск точек соседних объектов,
    попадающих в зону захвата.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Показывается перемещение (резиновая нить)
    новой точки (см. выше) и захваченных точек
    соседних объектов.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Координаты новой точки объекта остаются
    мышкиными. Все захваченные точки объектов
    перемещаются в новое место.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeMaking_Create_Line">Завершение
    ввода точек линейного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td rowspan="2">Если пользователь уже начал вводить
    новый объект, редакция завершается с сохранением
    объекта. При этом последняя точка (по правой
    кнопке мышки) не фиксируется.<p>Если пользователь
    не начинал вводить объект, то программа
    переходит к <a href="#ModeEdit_POP_UP">вызову выпадающего
    меню</a>.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
  </tr>
</table>

<h2><a name="ModeEdit">1.2. Режим редактирования объекта</a></h2>

<p>При удержании дополнительной кнопки <span class="Refer">&lt;Alt&gt;</span>
система переходит в <a href="#ModeMaking">режим создания
объекта</a>, который описан выше.</p>

<p>При удержании дополнительной кнопки <span class="Refer">&lt;Shift&gt;</span>
система переходит в <a href="#ModeDislocation">режим
перемещения и вращения объекта</a>.</p>

<table border="1" cellspacing="0">
  <tr>
    <th colspan="2">мышка</th>
    <th rowspan="2">доп. key</th>
    <th rowspan="2">событие</th>
  </tr>
  <tr>
    <th>клавиша</th>
    <th>статус</th>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4">любой объект</th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_POP_UP">Вызов
    выпадающего меню</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">&nbsp;</td>
    <td rowspan="2">Если под мышкой находится некоторый
    объект или несколько объектов, то появляется
    всплывающее меню (pop-up) и предлагается выполнить
    дополнительные функции.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">двойной щелчок</span></td>
    <td align="center">-</td>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4"><strong>точечный объект</strong></th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_Move_Point">Перемещение
    точечного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td>Производится поиск точечного объекта данного
    типа.<p>Если такой точечный объект <strong><u>найден</u></strong>,
    то найденный объект копируется в память.</p>
    <p>Если объект <strong><u>не найден,</u></strong> то
    следующие действия не выполняются.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center">-</td>
    <td>Показывается перемещение точечного объекта.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center">-</td>
    <td>Производится поиск видимых точек в зоне
    захвата.<p>Если таковые точки находятся, то
    координаты точечного объекта получают
    координаты первой захваченной точки, иначе
    координаты нового точечного объекта остаются
    мышкиными.</p>
    <p>После чего объект перезаписывается в карту.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_Move_Point_Adjacent">Перемещение
    точечного объекта со смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Производится поиск точечного объекта данного
    типа и найденный объект копируется в память.<p>Если
    объект <strong><u>не найден,</u></strong> то следующие
    действия не выполняются.</p>
    <p>Производится захват точек объектов, попадающих
    в зону захвата.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Показывается перемещение&nbsp; точечного
    объекта и перемещение (резиновая нить)
    захваченных точек</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Перезаписывается точечный объект и все
    захваченные точки объектов перемещаются в новое
    место</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_Del_Point">Удаление
    точечного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td>Производится поиск точечного объекта данного
    типа. Если такой объект найден, то он удаляется с
    подтверждением, иначе <a href="#ModeEdit_POP_UP">открывается
    подменю</a> (см. выше).</td>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4">линейный, полостный&nbsp; и
    плоскостной объекты</th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_Move_Line">Перемещение
    точки линейного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td>Производится поиск точки данного типа объекта.
    Если точка объекта не находится, то ищется
    отрезок объекта данного типа, на который
    приходится накол мышки.<p>Если точка объекта или
    подходящий отрезок&nbsp; <strong><u>найден</u></strong>, то
    найденный объект копируется в память. Если был
    найден отрезок, то в промежутке между точками
    отрезка создаётся новая точка.</p>
    <p>Если объект <strong><u>не найден,</u></strong> то
    следующие действия не выполняются.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center">-</td>
    <td>Показывается перемещение (резиновая нить)
    найденной точки. Если точка добавляется в начало
    или конец линейного объекта, то нить одна, иначе
    нить идёт от двух точек.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center">-</td>
    <td>Производится поиск точек видимых объектов в
    зоне захвата.<p>Если таковые точки находятся, то
    координаты перемещаемой точки получают
    координаты первой захваченной точки, иначе
    координаты перемещаемой точки становятся
    мышкиными.</p>
    <p>После чего объект перезаписывается в карту.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_Move_Line_Adjacent">Перемещение
    точки линейного объекта со смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Производится поиск точки данного типа объекта.
    Если точка объекта не находится, то ищется
    отрезок объекта данного типа, на который
    приходится накол мышки.<p>Если точка объекта или
    подходящий отрезок&nbsp; <strong><u>найден</u></strong>, то
    найденный объект копируется в память. Если был
    найден отрезок, то в промежутке между точками
    отрезка создаётся новая точка.</p>
    <p>Если объект <strong><u>не найден,</u></strong> то
    следующие действия не выполняются.</p>
    <p>Произходит захват точек видимых объектов,
    попадающих в зону захвата.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Показывается перемещение (резиновая нить)
    найденной точки объекта и всех захваченных
    точек.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Ctrl&gt;</span></td>
    <td>Координаты переставляемой точки и все
    захваченные точки объектов становятся
    мышкиными. Перезаписывается объект и все
    захваченные точки объектов перемещаются в новое
    место</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeEdit_Del_Line">Удаление
    точки линейного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
    <td rowspan="2">Производится поиск точки объекта
    данного типа. Если такой объект найден, то
    удаляется соответствующая точка из объекта,
    иначе <a href="#ModeEdit_POP_UP">открывается подменю</a> (см.
    выше).<p>Если это линейный объект и в нём было 2
    (две) точки, или это плоскостной объект и в нём
    было 3 (три) точки, то объект удаляется с
    подтверждением.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center">-</td>
  </tr>
</table>

<h2><a name="ModeDislocation">1.3. Перемещение и вращение
объекта</a></h2>

<p>В режим перемещения и вращения система
переходит при удержании дополнительной кнопки <span
class="Refer">&lt;Shift&gt;</span> из <a href="#ModeMaking">режима
создания объекта</a> и <a href="#ModeEdit">режима редакции
объекта</a>.</p>

<table border="1" cellspacing="0">
  <tr>
    <th colspan="2">мышка</th>
    <th rowspan="2">доп. key</th>
    <th rowspan="2">событие</th>
  </tr>
  <tr>
    <th>клавиша</th>
    <th>статус</th>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4"><strong>точечный объект</strong></th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeDislocation_Move_Point">Перемещение
    точечного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td rowspan="3">см. <a href="#ModeEdit_Move_Point">Перемещение
    точечного объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a
    name="ModeDislocation_Move_Point_Adjacent">Перемещение точечного
    объекта со смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td rowspan="3">см. <a href="#ModeEdit_Move_Point_Adjacent">Перемещение
    точечного объекта со смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
  </tr>
  <tr>
    <td colspan="4" height="5"></td>
  </tr>
  <tr>
    <th align="center" colspan="4">линейный, полостный&nbsp; и
    плоскостной объекты</th>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeDislocation_Move_Line">Перемещение
    объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td>Производится поиск данного типа объекта. Если
    объект <strong><u>найден</u></strong>, то он копируется в
    память.<p>Если объект <strong><u>не найден,</u></strong> то
    следующие действия не выполняются.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td>Показывается перемещение весего объекта
    целиком.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td>Перезаписывается объект с новыми
    координатами.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a name="ModeDislocation_Rotor_Line">Вращение
    объекта</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td>Производится поиск данного типа объекта. Если
    объект <strong><u>найден</u></strong>, то он копируется в
    память и запоминается точка поворота объекта.
    Если объект был найден по точке, то эта точка и
    будет поворотной, иначе поворотной будет точка
    накола мыши.<p>Если объект <strong><u>не найден,</u></strong>
    то следующие действия не выполняются.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td>Показывается вращение объекта вокруг точки
    поворота. При этом угол поворота вычисляется как
    угол между направлением на север (вверх чертежа)
    и вектором, образованным точкой поворота и
    текущей точкой мышки.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt;</span></td>
    <td>Перезаписывается объект в новой позиции.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a
    name="ModeDislocation_Move_Line_Adjacent">Перемещение объекта со
    смежными точкам</a>и</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td>Производится поиск данного типа объекта. Если
    объект <strong><u>найден</u></strong>, то он копируется в
    память.<p>Если объект <strong><u>не найден,</u></strong> то
    следующие действия не выполняются.</p>
    <p>Произходит захват всех точек смежных объектов,
    связанных с данным объектом.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td>Показывается перемещение весего объекта
    целиком. Перемещение смежных точек не
    показывается в виду сложности алгоритма.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">левая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td>Перезаписывается объект и все захваченные
    точки объектов перемещаются в новое место<p>Общие
    точки всех смежных объектов перемещаются в
    соответствии с новыми координатами
    перемещаемого объекта таким образом, что все
    ранее привязанные точки остаются привязанными.</td>
  </tr>
  <tr>
    <td align="center" colspan="4"><p class="RefAdres"><a
    name="ModeDislocation_Rotor_Line_Adjacent">Вращение объекта со
    смежными точками</a></td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">нажата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td>Производится поиск данного типа объекта. Если
    объект <strong><u>найден</u></strong>, то он копируется в
    память и запоминается точка поворота объекта.
    Если объект был найден по точке, то эта точка и
    будет поворотной, иначе поворотной будет точка
    накола мыши.<p>Если объект <strong><u>не найден,</u></strong>
    то следующие действия не выполняются.</p>
    <p>Произходит захват всех точек смежных объектов,
    связанных с данным объектом.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">перемещение</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td>Показывается вращение объекта вокруг точки
    поворота. При этом угол поворота вычисляется как
    угол между направлением на север (вверх чертежа)
    и вектором, образованным точкой поворота и
    текущей точкой мышки.<p>Показывается перемещение
    весего объекта целиком. Перемещение смежных
    точек не показывается в виду сложности
    алгоритма.</td>
  </tr>
  <tr>
    <td align="center"><span class="Refer">правая</span></td>
    <td align="center"><span class="Refer">отжата</span></td>
    <td align="center"><span class="Refer">&lt;Shift&gt; +&lt;Ctrl&gt;</span></td>
    <td>Перезаписывается объект и все захваченные
    точки объектов перемещаются в новое место<p>Общие
    точки всех смежных объектов перемещаются в
    соответствии с новыми координатами
    перемещаемого объекта таким образом, что все
    ранее привязанные точки остаются привязанными.</td>
  </tr>
</table>
