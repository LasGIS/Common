<h2>1.1.Общий заголовок графического объекта (<a name="MapObject">MapObject</a>)</h2>
<table cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td valign="top"><img src="/doc/images/forefinger.jpg" height="16"
 width="16"></td>
      <td>
      <p class="Prog">class <span class="ProgTerm">MapObject</span> {<br>
&nbsp;&nbsp;&nbsp; long <span class="ProgTerm">UID</span>; <span
 class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; // Уникальный
номер объекта</span><br>
&nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">Type</span>:15;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// Тип графического объекта (линия,
точка, и т.д.)</span><br>
&nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">TextFlag</span>:1; <span
 class="ProgComment">// Признак надписи графического объекта</span><br>
&nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">TypeNum</span>; <span
 class="ProgComment">&nbsp;&nbsp; // Номер типа графического объекта
(для проекта)</span><br>
&nbsp;&nbsp;&nbsp; DWORD <span class="ProgTerm">Classic</span>; <span
 class="ProgComment">&nbsp; // Классификационный номер графического
объекта</span><br>
&nbsp;&nbsp;&nbsp; <a href="MapO_Object.html#sUserTime">SUserTime</a> <span
 class="ProgTerm">CreateTime</span>; <span class="ProgComment">//
момент создания объекта (кто и когда)</span><br>
&nbsp;&nbsp;&nbsp; SUserTime <span class="ProgTerm">EditTime</span>;&nbsp;&nbsp;
      <span class="ProgComment">// момент последней редакции объекта</span><br>
&nbsp;&nbsp;&nbsp; long <span class="ProgTerm">Colour</span>; <span
 class="ProgComment">&nbsp;&nbsp;&nbsp; // цвет объекта</span><br>
&nbsp;&nbsp;&nbsp; long <span class="ProgTerm">Height</span>;&nbsp;&nbsp;&nbsp;&nbsp;
      <span class="ProgComment">// Высота объекта</span><br>
&nbsp;&nbsp;&nbsp; long <span class="ProgTerm">Width</span>; <span
 class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp; // Ширина объекта</span><br>
}; <span class="ProgComment">// размер структуры 40 байт</span></p>
      </td>
    </tr>
  </tbody>
</table>
<p>В общем заголовке графического объекта
содержится информация, необходимая для
последующего сохранения объекта в хранилище,
ссылка на способ рисования в данном проекте и
реквизиты пользователя,
создававшего/изменявшего объект.</p>
<p>Полный объект состоит из общего заголовка и
тела, определяемого типом графического объекта.
Реально общий заголовок является родителем для
конкретного типа объекта, а конкретный тип
объекта – потомком общего заголовка.</p>
<hr align="left" width="100%">
<p><span class="Refer">&lt;UID&gt;</span> - Уникальный номер
объекта. Дается при создании объекта и не
выделятся больше никакому другому объекту
внутри данного предприятия. При любой операции
создания объекта внутри данного предприятия
система запрашивает уникальный номер из <strong>файла
настройки системы</strong>, увеличивает его на единицу
и записывает увеличенное значение обратно в <strong>файл</strong>
<strong>настройки системы</strong>. Увеличенное значение
используется как уникальный номер объекта. Таким
образом поддерживается уникальность объекта
внутри предприятия.</p>
<p>Поле уникального номера в <strong>файле настройки
системы</strong> никогда нельзя уменьшать.</p>
<p>Поле уникального номера в заголовке объекта
никогда нельзя изменять без создания объекта.</p>
<p>При редакции объекта уникальный номер объекта
остается прежним.</p>
<p>При слиянии объектов остается уникальный номер
одного из объектов, а при разделении объекта
запрашивается уникальный номер из <strong>файла
настройки системы.</strong></p>
<p><span class="Refer">&lt;Type&gt;</span> - Тип графического
объекта. По данному полю в дальнейшем будет
определяться работа с телом графического
объекта. Определим основные типы объектов:</p>
<table border="1" cellpadding="" cellspacing="0">
  <tbody>
    <tr>
      <th><strong>Знач.</strong> </th>
      <th><strong>Тип объекта</strong> </th>
      <th><strong>Описание</strong> </th>
    </tr>
    <tr>
      <td class="RefAdres">2</td>
      <td>Точка</td>
      <td>Внемасштабный знак, имеющий необходимое число высот и диаметр
влияния (колодец)</td>
    </tr>
    <tr>
      <td class="RefAdres">1</td>
      <td>Линия</td>
      <td>Линейный объект, имеющий высоту в каждой точке, ширину
влияния и протяженность (дорога)</td>
    </tr>
    <tr>
      <td class="RefAdres">3</td>
      <td>Полоса</td>
      <td>Область, заключенная между двумя линиями с возможными
включениями, имеющая высоту в каждой точке, площадь и протяженность
(река).</td>
    </tr>
    <tr>
      <td class="RefAdres">0</td>
      <td>Плоскость</td>
      <td>Область, заключенная внутри замкнутой линии с возможными
включениями, имеющая высоту в каждой точке, площадь и периметр
(квартал).</td>
    </tr>
    <tr>
      <td class="RefAdres">4</td>
      <td>Надпись</td>
      <td>Надпись на карте, имеющая высоту букв, наклон, тип шрифта и
относящаяся к некоторому типу объекта (1-точке, 2-линии, 5-плоскости)</td>
    </tr>
    <tr>
      <td class="RefAdres">5</td>
      <td>Нерегулярная сеть</td>
      <td>Состоит из множества точек, связанных в треугольники Делонье.
Позволяет строить сечения, изолинии, находить высоту в заданной точке.</td>
    </tr>
    <tr>
      <td class="RefAdres">6</td>
      <td>Регулярная сеть</td>
      <td>Состоит из двухмерного массива высот по краям ячейки.
Позволяет строить сечения, изолинии, находить высоту в заданной точке. </td>
    </tr>
    <tr>
      <td class="RefAdres">7</td>
      <td>Ячеистая сеть</td>
      <td>Состоит из двухмерного массива параметров ячейки. Позволяет
производить операции с параметрами ячеек (карта загрязнения). </td>
    </tr>
    <tr>
      <td class="RefAdres">8</td>
      <td>Растровая подложка</td>
      <td>Рисуется до операции рисования карты. Используется при
векторизации растровой подложки. </td>
    </tr>
  </tbody>
</table>
<p>Здесь в первом столбце показано значение поля <span class="Refer">&lt;Type&gt;</span>
в общем заголовке.</p>
<p>Таблица открыта для дополнения другими типами
объектов.</p>
<p><span class="Refer">&lt;TypeNum&gt;</span> - Номер типа
графического объекта в данном проекте. Два поля <span class="Refer">&lt;Type&gt;</span>
и <span class="Refer">&lt;TypeNum&gt;</span>
определяют внутреннюю структуру программы,
через которую можно найти: </p>
<ol>
  <li>способ рисования данного объекта,</li>
  <li>видимость в данное время,</li>
  <li>название данного типо-объекта (находится в меню слоев),</li>
  <li>в какой базе находится или в какую базу необходимо сохранять
объект,</li>
  <li>на каком масштабном уровне просматривается (масштабный ряд),</li>
  <li>Номер или путь к семантической базе, в которой находится справка
по этому объекту,</li>
  <li>Интервал классификационных номеров (для перебора объектов,
относящихся к данному типо-объекту),</li>
  <li>Классификационный номер, задаваемый по умолчанию при создании
объекта. </li>
  <li>и т.д.</li>
</ol>
<p><span class="Refer">&lt;NumClassic&gt;</span> - Классификационный
номер графического объекта. Определяется при
создании этого объекта. Указывает на структуру
описания графического объекта в файле -
классификаторе графических объектов. Необходим
при передаче объекта в другие системы.</p>
<p><span class="Refer">&lt;CreateTime&gt;</span> - <a
 href="MapO_Object.html#sUserTime"><u>Структура,
определяющая момент создания объекта</u></a>.
Определяется в момент занесения объекта в базу. В
этой структуре определяется пользователь,
создавший объект и время создания объекта.</p>
<p><span class="Refer">&lt;EditTime&gt;</span> - Структура,
определяющая момент последней редакции объекта.
Изменяется в момент занесения измененного
объекта в базу. В этой структуре (см. ниже)
определяется пользователь, изменивший объект и
время изменения объекта.</p>
<p>Параметры <span class="Refer">&lt;Color&gt;</span>, <span
 class="Refer">&lt;Height&gt;</span>
и <span class="Refer">&lt;Width&gt;</span> обрабатываются особым
образом. Способ обработки этих параметров
определяется в момент настройки вывода объекта и
находится в файле настройки слоя. В общем случае
параметры задают вид изображения знака на карте
и используются в языке <strong>LGL.</strong></p>
<p>При нормальной настройке, <span class="Refer">&lt;Color&gt;</span>
должен содержать цвет объекта. Цвет определяется
как процент перехода из одного цвета в другой.
Значение этих двух красок определяются в файле
настройки слоя.</p>
<p>Параметр <span class="Refer">&lt;Height&gt;</span> определяет
высоту объекта [в сантиметрах] местности и
используется только при получении трехмерного
изображения местности.</p>
<p>Параметр <span class="Refer">&lt;Width&gt;</span> определяет
ширину объекта [в сантиметрах] местности или
определяет толщину линии (или окантовки
плоскости) [в 0.01 миллиметрах] при выводе на карту.
Тип вывода определяется при настройке
конкретного объекта в файле настройки слоя.</p>
<p class="Remark">Смотри также:</p>
<p class="Notice"><a href="MapO_OPoint.html">Тело графического объекта
типа
точка (<strong>OPoint</strong>)</a></p>
<p class="Notice"><a href="MapO_OLine.html">Тело графического объекта
типа
линия (<strong>OLine</strong>)</a></p>
<p class="Notice"><a href="MapO_OStrip.html">Тело графического объекта
типа
полоса (<strong>OStrip</strong>)</a></p>
<p class="Notice"><a href="MapO_OAreal.html">Тело графического объекта
типа
плоскость (<strong>OAreal</strong>)</a></p>
<p class="Notice"><a href="MapO_OText.html">Тело графического объекта
типа
надпись (<strong>OText</strong>)</a></p>
<p class="Notice"><a href="MapO_eLine.html">Формат элементарной линии (<strong>eLine</strong>)</a></p>
<p class="Notice"><a href="MapO_ePoint.html">Формат элементарной точки (<strong>ePoint</strong>)</a></p>
<strong>
<h3><a name="sUserTime">Момент создания или редактирования
объекта (sUserTime)</a></h3>
</strong>
<table cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td valign="top"><a name="sUserTime"><img
 src="/doc/images/forefinger.jpg" height="16" width="16"></a></td>
      <td>
      <p class="Prog">class <span class="ProgTerm">sUserTime</span> {<br>
&nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">Enterprise</span>; <span
 class="ProgComment">// Номер предприятия пользователя</span><br>
&nbsp;&nbsp;&nbsp; WORD <span class="ProgTerm">User</span>; <span
 class="ProgComment">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; // Номер
пользователя на предприятии</span><br>
&nbsp;&nbsp;&nbsp; <a href="MapO_Object.html#sDate">sDate</a> <span
 class="ProgTerm">Date</span>; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
 class="ProgComment">// Дата создания или редактирования объекта</span><br>
}; <span class="ProgComment">// размер структуры 8 байт</span> </p>
      </td>
    </tr>
  </tbody>
</table>
<p>Здесь:</p>
<p><span class="Refer">&lt;Enterprise&gt;</span> - Номер предприятия,
на
котором был создан объект. Вместе с уникальным
номером объекта образует глобальный уникальный
номер объекта. Вместе с номером пользователя
образует уникальный номер пользователя.
Определяется в момент создания дистрибутива или
момент передачи права на пользование системой <strong>LasGIS</strong>,
и находится в <strong>файле настройки системы</strong>.</p>
<p><span class="Refer">&lt;User&gt;</span> - Номер пользователя на
предприятии <span class="Refer">&lt;Enterprise&gt;</span>, который
создал или изменил объект. Номер дается
пользователю один раз при регистрации
пользователя в <strong>файле настройки системы.</strong> В
дальнейшем для данного пользователя
устанавливается доступ к тому или иному слою,
пароль, режим работы и т.д.</p>
<p><span class="Refer">&lt;Date&gt;</span> - Дата и время создания
или изменения объекта. Определяется в момент
занесения объекта в графическую базу вместе с
номером пользователя <span class="Refer">&lt;User&gt;</span> и
номером предприятия <span class="Refer">&lt;Enterprise&gt;</span>.
Формат даты определяется как структура:</p>
<table cellpadding="0" cellspacing="0">
  <tbody>
    <tr>
      <td valign="top"><img src="/doc/images/forefinger.jpg" height="16"
 width="16"></td>
      <td>
      <p class="Prog">struct <span class="ProgTerm">sDate</span> {<br>
&nbsp;&nbsp;&nbsp; UINT <span class="ProgTerm">time</span>: 11; <span
 class="ProgComment">// время, прошедшее с 0 часов 00 минут в минутах
(0-1439) </span><br>
&nbsp;&nbsp;&nbsp; UINT <span class="ProgTerm">day</span>:&nbsp;&nbsp;
5; <span class="ProgComment">// день месяца (1-32) </span><br>
&nbsp;&nbsp;&nbsp; UINT <span class="ProgTerm">moon</span>:&nbsp; 4; <span
 class="ProgComment">// месяц года (1-12) </span><br>
&nbsp;&nbsp;&nbsp; UINT <span class="ProgTerm">year</span>: 12; <span
 class="ProgComment">// год (1-4096)</span><br>
}; </p>
      </td>
    </tr>
  </tbody>
</table>
<p>Формат даты будет уточняться при создании
системы.</p>
<p class="Remark">Смотри также:</p>
<p class="Notice"><a href="MapO_Object.html#MapObject"><u>Общий заголовок
графического объекта</u></a></p>
