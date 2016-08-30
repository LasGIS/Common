<strong><em>

<h3><a name="Объект “MapO”">Объект “MapO”</a></h3>
</em>

<p></strong>Объект <strong>MapO</strong> является обобщенным
графическим объектом карты. Все свойства и
методы объекта делятся на общие и частные. Общие
свойства присущи каждому объекту карты, а
частные свойства зависят от конкретного типа
объекта:</p>

<table border="0" cellspacing="4" cellpadding="4">
  <tr>
    <td colspan="5"><strong>Общие свойства</strong></td>
  </tr>
  <tr>
    <td></td>
    <td><a href="#UID">UID</a></td>
    <td><a href="#Type">Type</a></td>
    <td><a href="#TypeNum">TypeNum</a></td>
    <td><a href="#TextFlag">TextFlag</a></td>
  </tr>
  <tr>
    <td></td>
    <td><a href="#Alias">Alias</a></td>
    <td>Classic</td>
    <td><font color="#808080">CreateTime</font></td>
    <td><font color="#808080">EditTime</font></td>
  </tr>
  <tr>
    <td></td>
    <td>Colour</td>
    <td>Height</td>
    <td>Width</td>
    <td></td>
  </tr>
  <tr>
    <td colspan="5"><strong>Общие методы</strong></td>
  </tr>
  <tr>
    <td></td>
    <td>Draw</td>
    <td>New</td>
    <td></td>
    <td></td>
  </tr>
</table>

<p>Частные свойства и методы зависят от типа
графического объекта (<a href="#Type">общее свойство Type</a>).</p>

<hr width="90%" size="4" color="#008080" noshade>

<h4><em><u>Общее свойство</u></em> &lt;long <a name="UID"><strong>UID</strong></a>&gt;
- Уникальный номер объекта.</h4>

<p>Уникальный номер объекта дается при создании
объекта и не выделятся больше никакому другому
объекту в системе. Свойство <strong>UID </strong>можно <u>только
читать</u>. </p>

<p>При редакции объекта уникальный номер объекта
остается прежним.</p>

<p>При слиянии объектов остается уникальный номер
одного из объектов, а при разделении объекта
уникальный номер выделяется системой<strong>.</p>
</strong>

<h4><u><em>Общее свойство</em></u> &lt;integer <a name="Type"><strong>Type</strong>&gt;</a>
- Тип графического объекта.</h4>

<p>По данному свойству в дальнейшем будет
определяться работа с телом графического
объекта. Свойство <strong>Type</strong> определяется в момент
создания графического объекта и является
свойством <u>только для чтения</u>.</p>

<p>Определим основные типы объектов:&nbsp;&nbsp;&nbsp; </p>

<p align="center" style="background-position: left bottom"><a
name="Таблица типов объекта">Таблица типов объекта</a></p>

<table BORDER="1" CELLSPACING="2" BORDERCOLOR="#000000">
  <tr>
    <td VALIGN="TOP"><strong>Знач.</strong></td>
    <td VALIGN="TOP"><strong>Alias</strong></td>
    <td VALIGN="TOP"><strong>Тип объекта</strong></td>
    <td VALIGN="top"><strong>Описание</strong></td>
  </tr>
  <tr>
    <td VALIGN="TOP">2</td>
    <td VALIGN="TOP">Pxx</td>
    <td VALIGN="TOP">Точка</td>
    <td VALIGN="TOP">Внемасштабный знак, имеющий
    необходимое число высот и диаметр влияния
    (колодец, город)</td>
  </tr>
  <tr>
    <td VALIGN="TOP">2</td>
    <td VALIGN="TOP">tPxx</td>
    <td VALIGN="TOP">Надпись точки</td>
    <td VALIGN="TOP">Надпись точки на карте, имеющая высоту
    букв и относящаяся к объекту типа точка.
    Начертание шрифта зависит от настройки объекта
    типа точка.</td>
  </tr>
  <tr>
    <td VALIGN="TOP">1</td>
    <td VALIGN="TOP">Lxx</td>
    <td VALIGN="TOP">Линия</td>
    <td VALIGN="TOP">Линейный объект, имеющий высоту в
    каждой точке, ширину влияния и протяженность
    (дорога, река)</td>
  </tr>
  <tr>
    <td VALIGN="TOP">1</td>
    <td VALIGN="TOP">tLxx</td>
    <td VALIGN="TOP">Надпись линии</td>
    <td VALIGN="TOP">Надпись линии на карте, имеющая высоту
    букв и относящаяся к объекту типа линия.
    Начертание шрифта зависит от настройки объекта
    типа линия.</td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">3</font></td>
    <td VALIGN="TOP"><font color="#808080">Bxx</font></td>
    <td VALIGN="TOP"><font color="#808080">Полоса</font></td>
    <td VALIGN="TOP"><font color="#808080">Область, заключенная между
    двумя линиями с возможными включениями, имеющая
    высоту в каждой точке, площадь и протяженность
    (река).</font></td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">3</font></td>
    <td VALIGN="TOP"><font color="#808080">tBxx</font></td>
    <td VALIGN="TOP"><font color="#808080">Надпись полосы</font></td>
    <td VALIGN="TOP"><font color="#808080">Надпись полосы на карте,
    имеющая высоту букв и относящаяся к объекту типа
    полоса. Начертание шрифта зависит от настройки
    объекта типа полоса.</font></td>
  </tr>
  <tr>
    <td VALIGN="TOP">0</td>
    <td VALIGN="TOP">Axx</td>
    <td VALIGN="TOP">Плоскость</td>
    <td VALIGN="TOP">Область, заключенная внутри замкнутой
    линии с возможными включениями, имеющая высоту в
    каждой точке по периметру, площадь и периметр
    (квартал, озеро).</td>
  </tr>
  <tr>
    <td VALIGN="TOP">0</td>
    <td VALIGN="TOP">tAxx</td>
    <td VALIGN="TOP">Надпись плоскости</td>
    <td VALIGN="TOP">Надпись плоскости на карте, имеющая
    высоту букв и относящаяся к объекту типа
    плоскость. Начертание шрифта зависит от
    настройки объекта типа плоскость.</td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">4</font></td>
    <td VALIGN="TOP"><font color="#808080">tTxx</font></td>
    <td VALIGN="TOP"><font color="#808080">Надпись</font></td>
    <td VALIGN="TOP"><font color="#808080">Надпись на карте, имеющая
    высоту букв. Начертание шрифта зависит от
    настройки объекта типа надпись.</font></td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">5</font></td>
    <td VALIGN="TOP"><font color="#808080">DN</font></td>
    <td VALIGN="TOP"><font color="#808080">Нерегулярная сеть</font></td>
    <td VALIGN="TOP"><font color="#808080">Состоит из множества точек,
    связанных в треугольники Делонье. Позволяет
    строить сечения, изолинии, находить высоту в
    заданной точке.</font></td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">6</font></td>
    <td VALIGN="TOP"><font color="#808080">RN</font></td>
    <td VALIGN="TOP"><font color="#808080">Регулярная сеть</font></td>
    <td VALIGN="TOP"><font color="#808080">Состоит из двухмерного
    массива высот по краям ячейки. Позволяет строить
    сечения, изолинии, находить высоту в заданной
    точке. </font></td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">7</font></td>
    <td VALIGN="TOP"><font color="#808080">CN</font></td>
    <td VALIGN="TOP"><font color="#808080">Ячеистая сеть</font></td>
    <td VALIGN="TOP"><font color="#808080">Состоит из двухмерного
    массива параметров ячейки. Позволяет
    производить операции с параметрами ячеек (карта
    загрязнения). </font></td>
  </tr>
  <tr>
    <td VALIGN="TOP"><font color="#808080">8</font></td>
    <td VALIGN="TOP"><font color="#808080">RS</font></td>
    <td VALIGN="TOP"><font color="#808080">Растровая подложка</font></td>
    <td VALIGN="TOP"><font color="#808080">Рисуется до операции
    рисования карты. Используется при векторизации
    растровой подложки. </font></td>
  </tr>
</table>

<p>Таблица открыта для дополнения другими типами
объектов.</p>

<h4><u><em>Общее свойство</em></u> &lt;integer<strong> <a name="TypeNum">TypeNum</a></strong>&gt;
- Номер типа графического объекта в данном
проекте. </h4>

<p>Два свойства <strong>Type</strong> и <strong>TypeNum</strong> определяют
внутреннюю структуру программы, которая
определяет:

<ol>
  <li>способ рисования данного объекта,</li>
  <li>видимость в данное время,</li>
  <li>название данного типо-объекта (находится в меню
    слоев),</li>
  <li>в какой базе находится или в какую базу
    необходимо сохранять объект,</li>
  <li>на каком масштабном уровне просматривается
    (масштабный ряд),</li>
  <li>Номер или путь к семантической базе, в которой
    находится справка по этому объекту,</li>
  <li>Интервал классификационных номеров (для
    перебора объектов, относящихся к данному
    типо-объекту),</li>
  <li>Классификационный номер, задаваемый по
    умолчанию при создании объекта. </li>
  <li>и т.д.</li>
</ol>

<h4><u><em>Общее свойство</em></u> &lt;bool <a name="TextFlag">TextFlag</a>&gt;
- Признак надписи графического объекта.</h4>

<p>Свойство <strong>TextFlag</strong> определяет, является ли
графический объект надписью или нет. Свойство
определяется в момент создания графического
объекта и является свойством <u>только для чтения</u>.</p>

<h4><u><em>Общее свойство</em></u> &lt;string <a name="Alias"><strong>Alias</strong></a>&gt;
- Обобщенное описание типа графического объекта.</h4>

<p>Три свойства<strong> Type</strong>, <strong>TypeNum</strong> и <strong>TextFlag</strong>
определяют кличку типо-объекта. Если флаг <strong>TextFlag</strong>
имеет значение <strong>true</strong>, то в начале строки клички
ставитьс я буква <strong>T</strong>. Затем идет символ
типа обекта, как это дано в таблице&nbsp; <a
name="Таблица типов объекта"
href="#Таблица типов объекта">таблице типов объекта</a>.
В конце алиаса указывается число, определяющее номер
типа объекта <strong>TypeNum</strong>.</p>

<h4><u><em>Общее свойство</em></u> &lt;<strong>NumClassic</strong>&gt; -
Классификационный номер графического объекта.</h4>

<p>Определяется при создании этого объекта.
Указывает на структуру описания графического
объекта в файле - классификаторе графических
объектов. Необходим при передаче объекта в
другие системы.</p>

<table border="0" style="color: rgb(30,30,130)">
  <tr>
    <td>&nbsp;&nbsp;&nbsp; DWORD</td>
    <td><strong>Classic;</strong></td>
    <td>// Классификационный номер графического
    объекта</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp;&nbsp; <a href="/doc/LasGIS_DOC/MapO/MapO_Object.html#sUserTime"><u>SUserTime</u></a></td>
    <td><strong>CreateTime;</strong></td>
    <td>// момент создания объекта (кто и когда)</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp;&nbsp; SUserTime</td>
    <td><strong>EditTime;</strong></td>
    <td>// момент последней редакции объекта</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp;&nbsp; long</td>
    <td><strong>Colour;</strong></td>
    <td>// цвет объекта</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp;&nbsp; long</td>
    <td><strong>Height;</strong></td>
    <td>// Высота объекта</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp;&nbsp; long</td>
    <td><strong>Width;</strong></td>
    <td>// Ширина объекта</td>
  </tr>
</table>

<h4><u><em>Общее свойство</em></u> &lt;<strong>CreateTime</strong>&gt; - <a
href="/doc/LasGIS_DOC/MapO/MapO_Object.html#sUserTime"><u>Структура, определяющая
момент создания объекта</u></a>.</h4>

<p>Определяется в момент занесения объекта в базу.
В этой структуре определяется пользователь,
создавший объект и время создания объекта.</p>

<h4><u><em>Общее свойство</em></u> &lt;<strong>EditTime</strong>&gt; -
Структура, определяющая момент последней
редакции объекта.</h4>

<p>Изменяется в момент занесения измененного
объекта в базу. В этой структуре (см. ниже)
определяется пользователь, изменивший объект и
время изменения объекта.</p>

<p><u><em>Общее свойство</em></u> &lt;<strong>Color</strong>&gt; цвет объекта<strong></p>
</strong>

<p><u><em>Общее свойство</em></u> &lt;<strong>Height</strong>&gt; <u>в</u>ысота
объекта</p>

<p><u><em>Общее свойство</em></u> &lt;<strong>Width</strong>&gt; ширина
объекта</p>

<p>обрабатываются особым образом. Способ
обработки этих параметров определяется в момент
настройки вывода объекта и находится в файле
настройки слоя. В общем случае параметры задают
вид изображения знака на карте и используются в
языке <strong>LGL.</strong></p>

<p>При нормальной настройке, &lt;<strong>Color</strong>&gt; должен
содержать цвет объекта. Цвет определяется как
процент перехода из одного цвета в другой.
Значение этих двух красок определяются в файле
настройки слоя.</p>

<p>Параметр &lt;<strong>Height</strong>&gt; определяет высоту объекта
[в сантиметрах] местности и используется только
при получении трехмерного изображения
местности.</p>

<p>Параметр &lt;<strong>Width</strong>&gt; определяет ширину объекта
[в сантиметрах] местности или определяет толщину
линии (или окантовки плоскости) [в 0.01 миллиметрах]
при выводе на карту. Тип вывода определяется при
настройке конкретного объекта в файле настройки
слоя.</p>

<p>Смотри также:</p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_OPoint.html">Тело графического объекта
типа точка (<strong>OPoint</strong>)</a></p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_OLine.html">Тело графического объекта
типа линия (<strong>OLine</strong>)</a></p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_OStrip.html">Тело графического объекта
типа полоса (<strong>OStrip</strong>)</a></p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_OAreal.html">Тело графического объекта
типа плоскость (<strong>OAreal</strong>)</a></p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_OText.html">Тело графического объекта
типа надпись (<strong>OText</strong>)</a></p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>
<strong>

<p><a name="sUserTime">Момент создания или редактирования
объекта (sUserTime)</a></p>
</strong>

<table border="0" style="color: rgb(30,30,130)">
  <tr>
    <td colspan="3">class <strong>sUserTime</strong></td>
  </tr>
  <tr>
    <td>{</td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp; WORD</td>
    <td><strong>Enterprise</strong>;</td>
    <td>// Номер предприятия пользователя</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp; WORD</td>
    <td><strong>User</strong>;</td>
    <td>// Номер пользователя на предприятии</td>
  </tr>
  <tr>
    <td>&nbsp;&nbsp; <a href="/doc/LasGIS_DOC/MapO/MapO_Object.html#sDate"><strong>sDate</strong></a></td>
    <td><strong>Date</strong>;</td>
    <td>// Дата создания илиредактирования объекта</td>
  </tr>
  <tr>
    <td>};</td>
    <td></td>
    <td>// размер структуры 8 байт</td>
  </tr>
</table>

<p>Здесь:</p>

<p>&lt;<strong>Enterprise</strong>&gt; - Номер предприятия, на котором
был создан объект. Вместе с уникальным номером
объекта образует глобальный уникальный номер
объекта. Вместе с номером пользователя образует
уникальный номер пользователя. Определяется в
момент создания дистрибутива или момент
передачи права на пользование системой <strong>LasGIS</strong>, и
находится в <strong>файле настройки системы</strong>.</p>

<p>&lt;<strong>User</strong>&gt; - Номер пользователя на предприятии
&lt;<strong>Enterprise</strong>&gt;, который создал или изменил объект.
Номер дается пользователю один раз при
регистрации пользователя в <strong>файле настройки
системы.</strong> В дальнейшем для данного пользователя
устанавливается доступ к тому или иному слою,
пароль, режим работы и т.д.</p>

<p>&lt;<strong>Date</strong>&gt; - Дата и время создания или изменения
объекта. Определяется в момент занесения объекта
в графическую базу вместе с номером пользователя
&lt;<strong>User</strong>&gt; и номером предприятия &lt;<strong>Enterprise</strong>&gt;.
Формат даты определяется как структура:</p>
<font color="#008080">

<table border="0" style="color: rgb(30,30,130)">
  <tr>
    <td colspan="3"><a name="sDate">struct <strong>sDate</strong></a></td>
  </tr>
  <tr>
    <td>{</td>
    <td></td>
    <td></td>
  </tr>
  <tr>
    <td></td>
    <td>UINT</td>
    <td><strong>time</strong>: 11;</td>
    <td>// время, прошедшее с 0 часов 00 минут в минутах
    (0-1439)</td>
  </tr>
  <tr>
    <td></td>
    <td>UINT</td>
    <td><strong>day</strong>: 5;</td>
    <td>// день месяца (1-32)</td>
  </tr>
  <tr>
    <td></td>
    <td>UINT</td>
    <td><strong>moon</strong>: 4;</td>
    <td>// месяц года (1-12)</td>
  </tr>
  <tr>
    <td></td>
    <td>UINT</td>
    <td><strong>year</strong>: 12;</td>
    <td>// год (1-4096)</td>
  </tr>
  <tr>
    <td>};</td>
  </tr>
</table>
</font>

<p>Формат даты будет уточняться при создании
системы.</p>

<p>Смотри также:</p>

<p><a href="/doc/LasGIS_DOC/MapO/MapO_Object.html#Object"><u>Общий заголовок графического объекта</u></a></p>
