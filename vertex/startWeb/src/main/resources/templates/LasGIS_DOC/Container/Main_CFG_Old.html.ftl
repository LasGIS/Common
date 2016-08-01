<h1>Главная конфигурация проекта (старый формат)</h1>

<p ALIGN="JUSTIFY">В файле главной конфигурации
находятся настройки проекта, в которой
присутствует следующая информация</p>

<ul>
  <li>где находятся графические и семантические
    данные,</li>
  <li>каким образом рисуются графические объекты,</li>
  <li>как они связаны друг с другом,</li>
  <li>какие поля доступны для проекта у семантических
    баз,</li>
  <li>и т.д.</li>
</ul>

<p>Файла главной конфигурации проекта находится в
общем каталоге проекта. Имя файла может быть
любым (желательно до 8 знаков и без русских букв,
для связи с DOS). Расширение файла – “<strong>.CFG</strong>”.</p>

<p><strong>Структура файла</strong> состоит из следующих
частей:</p>

<table>
  <tr>
    <th>Адрес в файле</th>
    <th>Название блока</th>
  </tr>
  <tr>
    <td class="RefAdres">00h – 1Fh</td>
    <td class="Refer">Заголовок</td>
  </tr>
  <tr>
    <td class="RefAdres">20h – 7FFh </td>
    <td class="Refer">Индексы</td>
  </tr>
  <tr>
    <td class="RefAdres">800h – до конца</td>
    <td class="Refer">Блоки</td>
  </tr>
</table>

<h2>1. Структура</h2>

<h3><a name="Container_Head">1.1. Структура заголовк</a>а</h3>

<p>Структура заголовка контейнера старого
формата не определяет внутреннюю структуру
файла. Содержание файла определяется по его
расширению (*.cfg).</p>

<table>
  <tr>
    <th>Позиция</th>
    <th>Размер</th>
    <th>Описание</th>
  </tr>
  <tr>
    <td class="RefAdres">00h</td>
    <td>4(dword)</td>
    <td>Адрес последнего блока</td>
  </tr>
  <tr>
    <td class="RefAdres">04h</td>
    <td>4(dword)</td>
    <td>Размер файла конфигурации ( для след. блока )</td>
  </tr>
  <tr>
    <td class="RefAdres">08h</td>
    <td>24(char)</td>
    <td>Текст принадлежн. &quot;ГЛОБАЛЬНАЯ &nbsp;
    НАСТРОЙКА\0&quot;</td>
  </tr>
</table>

<h3>1.2<a name="Container_Indexes">. Индексы</a></h3>

<p>Индексы представляют собой массив адресов
блоков внутри контейнера (<strong>“dword”</strong> или <strong>4 байта</strong>
на один индекс). </p>

<p>Располагаются индексы в области от конца
заголовка (адрес <span class="RefAdres">20h</span>) до начала
блоков (адрес <span class="RefAdres">800h</span>) При этом блоки с
адреса <span class="RefAdres">400h</span> до адреса <span class="RefAdres">800h</span>
зарезервированы для настройки семантических
таблиц.</p>

<p><u>Вложенности подблоков не предусмотрено</u>.</p>

<p>Смотри также: <a href="#Container_Blocks_Index">Распределение
блоков по индексам</a>.</p>

<h3>1.3. Обязательный заголовок блоков</h3>

<table>
  <tr>
    <th>позиция </th>
    <th>Размер</th>
    <th>Имя </th>
    <th>Описание </th>
  </tr>
  <tr>
    <td class="RefAdres">00h</td>
    <td>2(word)</td>
    <td><strong>len_bloc</strong></td>
    <td>размер блока ( свой размер )</td>
  </tr>
  <tr>
    <td class="RefAdres">02h</td>
    <td>2(word)</td>
    <td><strong>len_bloc_max</strong></td>
    <td>максимальный размер блока</td>
  </tr>
  <tr>
    <td class="RefAdres">04h</td>
    <td COLSPAN="3" ALIGN="center">Тело блока</td>
  </tr>
</table>

<h3>1.4. Работа с файлом</h3>

<p>При работе с файлом программа или читает тело
блока или записывает его. При этом знание о
внутренней структуре блока целиком возлагаются
на программиста. Автоматизируется только
процесс размещения блока в файле.</p>

<p class="Remark">Чтение блока:</p>

<p class="Notice">При чтении блока - ничего интересного
не происходит. Программа читает индекс (адрес
блока в файле) и по этому адресу читает тело
блока, затем происходит его разбор.</p>

<p class="Remark">Запись блока:</p>

<p class="Notice">При записи блока - программа сначала
упаковывает данные в единый блок, получается <span
class="Refer">тело блока</span> и вычисляется <strong>размер</strong>
блока. Затем программа получает адрес блока в
индексах и читает <span class="Refer">обязательный
заголовок</span>. </p>

<p class="Notice">&nbsp;</p>

<p class="Notice">Если <span class="Refer">максимальный размер
блока</span> <strong><u>больше</u></strong>, чем размер
записываемого блока или блок является последним
(для этого контролируется <span class="Refer">Адрес
последнего блока</span>), то <span class="Refer">блок </span>записывается
на место старого&nbsp; блока. В случае если блок
последний, то изменяется и <span class="Refer">максимальный
размер блока.</span> При этом изменяются значения:</p>

<p class="Notice"><span class="Refer">размер блока</span>;</p>

<p class="Notice"><span class="Refer">максимальный размер блока </span>(если
блок последний);</p>

<p class="Notice"><span class="Refer">Заголовок.Размер файла
конфигурации </span>(если блок последний);</p>

<p class="Notice">&nbsp;</p>

<p class="Notice">Если <span class="Refer">максимальный размер
блока</span> <strong><u>меньше</u></strong>, но блок не
является последним или блок ещё не был записан,
то программа записывает блок в конец файла. При
этом изменяются значения:</p>

<p class="Notice"><span class="Refer">В индексах - адрес блока</span>;</p>

<p class="Notice"><span class="Refer">Блок.размер блока</span>;</p>

<p class="Notice"><span class="Refer">Блок.максимальный размер
блока </span>(если блок последний);</p>

<p class="Notice"><span class="Refer">Заголовок.Адрес последнего
блока</span>;</p>

<p class="Notice"><span class="Refer">Заголовок.Размер файла
конфигурации</span>;</p>

<p>&nbsp;</p>

<h2><a name="Container_Blocks">3. Блоки</a></h2>

<p>Блоки располагаются в области от адреса <span
class="RefAdres">800h</span> до конца файла. Номер индекса
строго привязан к тематике конкретного блока для
данного <strong>типа файла</strong>. </p>

<p class="Remark"><a name="Container_Blocks_Index">Распределение блоков
по индексам</a></p>

<table>
  <tr>
    <th>Индекс</th>
    <th>Позиция</th>
    <th>Размер</th>
    <th>Описание </th>
  </tr>
  <tr>
    <td class="RefAdres">0</td>
    <td class="RefAdres">20h</td>
    <td>40(long[10])</td>
    <td><a href="#Container_Block_LayerMenu">Блоки тематического меню уровня [1-9]</a></td>
  </tr>
  <tr>
    <td class="RefAdres">10</td>
    <td class="RefAdres">48h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_AreaSet">Блок плоскостных объектов карты</a></td>
  </tr>
  <tr>
    <td class="RefAdres">11</td>
    <td class="RefAdres">4Ch</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_AreaStroke">Блок штриховых примитивов для плоскости</a></td>
  </tr>
  <tr>
    <td class="RefAdres">12</td>
    <td class="RefAdres">50h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_LineSet">Блок линейных объектов карты</a></td>
  </tr>
  <tr>
    <td class="RefAdres">13</td>
    <td class="RefAdres">54h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_LineStroke">Блок штриховых примитивов для линии *</a></td>
  </tr>
  <tr>
    <td class="RefAdres">14</td>
    <td class="RefAdres">58h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_PointSet">Блок точечных объектов карты</a></td>
  </tr>
  <tr>
    <td class="RefAdres">15</td>
    <td class="RefAdres">5Ch</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_PointStroke">Блок штриховых примитивов для точки</a></td>
  </tr>
  <tr>
    <td class="RefAdres">16</td>
    <td class="RefAdres">60h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_Passwords">Блок настройки паролей</a></td>
  </tr>
  <tr>
    <td class="RefAdres">17</td>
    <td class="RefAdres">64h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_Paths">Блок путей к графической базе</a></td>
  </tr>
  <tr>
    <td class="RefAdres">18</td>
    <td class="RefAdres">68h</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_Autor">Блок описания создателя графической базы</a></td>
  </tr>
  <tr>
    <td class="RefAdres">19</td>
    <td class="RefAdres">6Ch</td>
    <td>4(long)</td>
    <td><a href="#Container_Block_CoordinateSystem">Блок номенклатуры местной системы координат</a></td>
  </tr>
  <tr>
    <td class="RefAdres">1016</td>
    <td class="RefAdres">400h</td>
    <td>1024(long[256])</td>
    <td><a href="#Container_Block_DataBase">Блоки настройки семантической базы</a></td>
  </tr>
</table>

<h3><a name="Container_Block_LayerMenu">3.2 Блоки тематического меню уровня [1-9]</a></h3>
<p>Сначала предполагалось, что каждый блок будет содержать настройку для показа конкретного уровня (с 1 по 9).
Однако, в процессе работы было решено оставить один, общий блок для всех уровней.</p>

  <table>
    <caption><p class="Remark">Структура блока тематического меню уровня</p></caption>
    <tr><th colspan="2">позиция</th><th>Размер</th><th>Имя</th><th>Описание</th></tr>
    <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>2(int)</td>
      <td class="Refer">Num_all</td><td>общее число объектов в меню уровня.</td></tr>
    <tr><td class="RefAdres">6</td><td class="RefAdres">6h</td><td>2(int)</td>
      <td class="Refer">Num_main</td><td>число главных тем или слоёв</td></tr>
    <tr><td colspan="5"><p class="Remark">распределение для одной строки-меню</p></td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4(long)</td>
      <td class="Refer">num_func</td><td>код, который содержит описание графического объекта</td></tr>
    <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>1(char)</td>
      <td class="Refer">men</td><td>0 - главное; [1-255] подменю</td></tr>
    <tr><td class="RefAdres">5</td><td class="RefAdres">5h</td><td>1(char)</td>
      <td class="Refer">wyd</td><td>выделенная буква</td></tr>
    <tr><td class="RefAdres">6</td><td class="RefAdres">6h</td><td>2(int)</td>
      <td class="Refer">klass</td><td>код в классификаторе</td></tr>
    <tr><td class="RefAdres">8</td><td class="RefAdres">8h</td><td>24(char[24])</td>
      <td class="Refer">Str</td><td>строка меню</td></tr>
    <tr><td class="RefAdres">32</td><td class="RefAdres">20h</td><td colspan="4">&nbsp;</td></tr>
  </table>
<hr/>
<p class="Remark">Структура меню / подменю:</p>
<p class="Notice">Меню в GEO (DOS приложение) в общем случае состоит из верхнего меню (меню заголовков или слоёв)
  и подменю, в который входят элементы описания конкретных графических объектов.</p>
<p class="Notice">Значение <span class="Refer">Num_main</span> определяет количество заголовков.
  Если <span class="Refer">men</span> для данного элемента меню равно 0, значит это заголовок, иначе это описание
  графического объекта. Все элементы меню с описанием графических объектов, находящиеся между двумя заголовками,
  входят в подменю для первого заголовка.</p>
<p class="Notice"> В GEO при выборе заголовка, подменю обновлялось.</p>
<p class="Remark">Расшифровка кода описания графического объекта:</p>
<p class="Notice">Значение <span class="Refer">num_func</span> содержит описание графического объекта. Расшифровка его
  представлена ниже, в виде программы С++</p>
<pre class="Prog">
<span class="Refer">chif</span> = (UINT)( num_func & 0x00000FFl       ); <span class="ProgComment">// номер типа обьекта (т.е., например, 5 точка)</span>
<span class="Refer">bfil</span> = (UINT)((num_func & 0x000FF00l) >> 8 ); <span class="ProgComment">// номер семантической базы</span>
<span class="Refer">gfil</span> = (UINT)((num_func & 0x0FF0000l) >> 16); <span class="ProgComment">// номер тематического слоя</span>
<span class="Refer">vid</span>  = (UINT)((num_func & 0xF000000l) >> 24); <span class="ProgComment">// тип обьекта (плоскость, линия, точка)</span></pre>

<h3><a name="Container_Block_AreaSet">3.3 Блок плоскостных объектов карты </a></h3>
  <table>
    <caption><p class="Remark">Структура блока плоскостных объектов карты</p></caption>
    <tr><th colspan="2">позиция</th><th>Размер</th><th>Имя</th><th>Описание</th></tr>
    <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>2(int)</td>
      <td class="Refer">Num_all</td><td>общее число плоскостных объектов</td></tr>
    <tr><td colspan="5"><p class="Remark">распределение для одного плоскостного объекта</p></td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col_lin</td><td>цвет границы области</td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col_n</td><td>цвет надписи</td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4</td>
      <td class="Refer">col_fil</td><td>цвет закрашиваемой плоскости</td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col_nf</td><td>цвет фона надписи</td></tr>
    <tr><td class="RefAdres">2</td><td class="RefAdres">2h</td><td>8(char[8])</td>
      <td class="Refer">pattern</td><td>рисунок закраски (pattern 8 X 8 bit )</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td>12/16(int:12)</td>
      <td class="Refer">line</td><td>номер линии для рамки</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td>1/16(int:1)</td>
      <td class="Refer">FL_clear_fill</td><td>признак прозрачности полигона</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td>1/16(int:1)</td>
      <td class="Refer">FL_clear_line</td><td>признак прозрачности линии</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td>1/16(int:1)</td>
      <td class="Refer">FL_nofon_text</td><td>признак пропуска фона надписи</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td>1/16(int:1)</td>
      <td class="Refer">FL_transparnt</td><td>признак полупрозрачности</td></tr>
    <tr><td class="RefAdres">12</td><td class="RefAdres">Ch</td><td>2(int)</td>
      <td class="Refer">drw</td><td>номер штрихового примитива заполнения</td></tr>
    <tr><td class="RefAdres">14</td><td class="RefAdres">Eh</td><td>2(int)</td>
      <td class="Refer">col_drw</td><td>цвет карандаша примитива</td></tr>
    <tr><td class="RefAdres">16</td><td class="RefAdres">10h</td><td colspan="3"></td></tr>
  </table>

<h3><a name="Container_Block_AreaStroke">3.4 Блок штриховых примитивов для плоскости </a></h3>
<table>
  <caption><p class="Remark">Структура блока штриховых примитивов для плоскости</p></caption>
  <tr><th colspan="2">позиция</th><th>Размер</th><th>Имя</th><th>Описание</th></tr>
  <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>2(int)</td>
    <td class="Refer">Num_all</td><td>общее число плоскостных объектов</td></tr>
  <tr><td colspan="5"><p class="Remark">распределение для одного плоскостного объекта</p></td></tr>
  <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>2(int)</td>
    <td class="Refer">pat1</td><td>заполнение 1 линии</td></tr>
  <tr><td class="RefAdres">2</td><td class="RefAdres">2h</td><td>2(int)</td>
    <td class="Refer">pat2</td><td>заполнение 2 линии</td></tr>
  <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>1(char)</td>
    <td class="Refer">dir1</td><td>направление 1 линии</td></tr>
  <tr><td class="RefAdres">5</td><td class="RefAdres">5h</td><td>1(char)</td>
    <td class="Refer">dir2</td><td>направление 2 линии</td></tr>
  <tr><td class="RefAdres">6</td><td class="RefAdres">6h</td><td>1(char)</td>
    <td class="Refer">len1</td><td>шаг линовки 1 линией</td></tr>
  <tr><td class="RefAdres">7</td><td class="RefAdres">7h</td><td>1(char)</td>
    <td class="Refer">len2</td><td>шаг линовки 2 линией</td></tr>
  <tr><td class="RefAdres">8</td><td class="RefAdres">8h</td><td>1(char)</td>
    <td class="Refer">cpl1</td><td>номер карандаша плоттера 1 линии</td></tr>
  <tr><td class="RefAdres">9</td><td class="RefAdres">9h</td><td>1(char)</td>
    <td class="Refer">cpl2</td><td>номер карандаша плоттера 2 линии</td></tr>
  <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td colspan="3"></td></tr>
</table>

<h3><a name="Container_Block_LineSet">3.5 Блок линейных объектов карты </a></h3>
  <table>
    <caption><p class="Remark">Структура блока линейных объектов карты</p></caption>
    <tr><th colspan="2">Позиция</th><th>Размер</th><th>Имя</th><th>Описание</th></tr>
    <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>2(int)</td>
      <td class="Refer">Num_all</td><td>общее число линейных объектов</td></tr>
    <tr><td colspan="5"><p class="Remark">структура одного линейного объекта</p></td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col1</td><td>цвет 1 линии</td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col_n</td><td>цвет надписи</td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col2</td><td>цвет 2 линии</td></tr>
    <tr><td class="RefAdres">0</td><td class="RefAdres">0h</td><td>4/16(unsigned:4)</td>
      <td class="Refer">col_nf</td><td>цвет фона надписи</td></tr>
    <tr><td class="RefAdres">2</td><td class="RefAdres">2h</td><td>2(unsigned)</td>
      <td class="Refer">pat1</td><td>заполнение 1 линии</td></tr>
    <tr><td class="RefAdres">4</td><td class="RefAdres">4h</td><td>2(unsigned)</td>
      <td class="Refer">pat2</td><td>заполнение 2 линии</td></tr>
    <tr><td class="RefAdres">6</td><td class="RefAdres">6h</td><td>2/8(char:2)</td>
      <td class="Refer">thic</td><td>толщина линии</td></tr>
    <tr><td class="RefAdres">...</td><td class="RefAdres">...</td><td>1/8(char:1)</td>
      <td class="Refer">vid_p</td><td>наличие средней буквы</td></tr>
    <tr><td class="RefAdres">...</td><td class="RefAdres">...</td><td>1/8(char:1)</td>
      <td class="Refer">alt_p</td><td>если 1, то все высоты одинаковые</td></tr>
    <tr><td class="RefAdres">...</td><td class="RefAdres">...</td><td>4/8(char:4)</td>
      <td class="Refer">strel</td><td>оформление края (не используется)</td></tr>
    <tr><td class="RefAdres">7</td><td class="RefAdres">7h</td><td>1(char)</td>
      <td class="Refer">vid</td><td>буква в центре линии </td></tr>
    <tr><td class="RefAdres">8</td><td class="RefAdres">8h</td><td>1(char)</td>
      <td class="Refer">drw_k1</td><td>номер краевой точки слева</td></tr>
    <tr><td class="RefAdres">9</td><td class="RefAdres">9h</td><td>1(char)</td>
      <td class="Refer">drw_k2</td><td>номер краевой точки справа</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Ah</td><td>1(char)</td>
      <td class="Refer">cpl</td><td>номер карандаша плоттера</td></tr>
    <tr><td class="RefAdres">10</td><td class="RefAdres">Bh</td><td>1(char)</td>
      <td class="Refer">drw_c</td><td>номер штрихового примитива</td></tr>
    <tr><td class="RefAdres">12</td><td class="RefAdres">Ch</td><td colspan="3">...</td></tr>
  </table>

<h3><a name="Container_Block_LineStroke">3.6 Блок штриховых примитивов для линии *</a></h3>
<p>Штриховые примитивы для линии задаётсся в настройке линии.</p>
<p>(см. <a href="#Container_Block_LineSet">3.5 Блок линейных объектов карты </a>).</p>

<h3><a name="Container_Block_PointSet">3.7 Блок точечных объектов карты </a></h3>
<pre class="Prog">
  структура блока точечных объектов карты            (*.cfg  0x58)
----------+---+-----------+----------------+-----------------------------+
| позиция |len|   тип     |     имя        |      Описание               |
+---------+---+-----------+----------------+-----------------------------+
|   4|   4| 2 |  int      | Num_all        | число плоскостных объектов  |
+---------+--- распределение для одной строки-меню ----------------------+
|   0|   0|4/8|unsigned:4 | col            | цвет маски                  |
|   0|   0|4/8|unsigned:4 | col_n          | цвет надписи                |
|   1|   1|4/8|unsigned:4 | col_f          | цвет фона                   |
|   1|   1|4/8|unsigned:4 | col_nf         | цвет фона надписи           |
|   2|   2|32 |  pat[32]  | pat            | рисунок закраски маски      |
|  34|  22|32 |  pat[32]  | pat            | рисунок закраски фона       |
|  66|  42|4/8|unsigned:4 | ndp            | число отметок высоты        |
|  66|  42|4/8|unsigned:4 | cpl            | номер карандаша плоттера    |
|  66|  42| 1 | unsigned  | drw            | номер штрихового примитива  |
|  68|  44|            .  .  .  .  .  .  .  .  .  .                      |
+---------+--------------------------------------------------------------+
</pre>

<h3><a name="Container_Block_PointStroke">3.8 Блок штриховых примитивов для точки </a></h3>
<pre class="Prog">
  структура блока штриховых примитивов       (*.cfg  0x5C)
----------+---+---------+----------------+-------------------------------+
| позиция |len|   тип   |     имя        |      Описание                 |
+---------+---+---------+----------------+-------------------------------+
|   4|   4| 2 |   int   | Num_all (Nal)  | число примитивов              |
|   6|   6| 2 |   int   | len_buf (lbf)  | число точек в буфере          |
|   6|   6|Nal| int[Nal]| N  [Nal]       | Позиция начала примитива      |
|  +6|  +6|Nbf| int[lbf]| buf[lbf]       | общая куча точек              |
+---------+--- распределение в общем блоке ------------------------------+
|    |    | 1 |  char   | x              | смещение по X                 |
|    |    | 1 |  char   | y              | смещение по Y                 |
|    |    |          .  .  .  .  .  .  .  .  .  .                        |
+---------+--------------------------------------------------------------+
Если 'x==80h' или (-128), то 'y' содержит в себе команду:
0          - поднять перо;
1          - опустить перо ;
2          - установить тонкое перо;
3          - установить толстое перо;
0x10..0x1f - сменить цвет;
0x80       - конец примитива;
</pre>

<h3><a name="Container_Block_Passwords">3.9 Блок настройки паролей </a></h3>
<pre class="Prog">
  структура блока паролей                    (*.cfg  0x60)
----------+---+----------+----------------+------------------------------+
| позиция |len|   тип    |     имя        |      Описание                |
+---------+---+----------+----------------+------------------------------+
|   4|   4| 2 |  int     | Nmen_max       | число пользователей системы  |
|   6|   6| 2 |  int     | Kod_prp        | код преприятия               |
|   8|   8| 28| char[28] | Name_prp       | название предприятия         |
+---------+--- распределение для одного пользователя --------------------+
|   0|   0| 6 | char[6]  | kod            | код пользователя             |
|   6|   6| 2 |  word    | prl_bit        | пароль пользователя          |
|   8|   8| 14| char[14] | name           | имя пользователя             |
|  22|  16| 2 |  word    | Num_User       | номер пользователя в системе |
|  24|  18| 4 | struct   | Access_View    | доступ пользов. к просмотру  |
|  28|  1C| 4 | struct   | Access_Edit    | доступ пользов. к редакции   |
|  32|  20|           .  .  .  .  .  .  .  .  .  .                       |
+---------+--------------------------------------------------------------+
</pre>

<h3><a name="Container_Block_Paths">3.10 Блок путей к графической базе</a></h3>
<pre class="Prog">
  структура блока путей к распределенной базе         (*.cfg  0x64)
 ----------+---+----------+----------------+------------------------------+
 | позиция |len|   тип    |     имя        |      Описание                |
 +---------+---+----------+----------------+------------------------------+
 |   4|   4| 2 |  int     | Num_all        | число путей в базе           |
 |   6|   6| 2 |  int     | num            | длина общего блока           |
 |   8|   8|num|char[num] | fbloc          | общий блок                   |
 +---------+--- распределение в общем блоке ------------------------------+
 |    |    | ? |char[Num] | Type           | тип окончания g*.mp?<---     |
 |    |    | ? |char[?]   | Path[0]        | путь к первому слою          |
 |    |    |           .  .  .  .  .  .  .  .  .  .                       |
 |    |    | ? |char[?]   | Path[Num_all-1]| путь к последнему слою       |
 |    |    | ? |char[?]   | Kat_rastr      | путь к файлам подложки .pcm  |*
 |    |    | ? |char[?]   | Kat_Global     | путь к настройке пред.(*.cfp)|*
 +---------+--------------+----------------+------------------------------+
</pre>

<h3><a name="Container_Block_Autor">3.11 Блок описания создателя графической базы </a></h3>
<pre class="Prog">
  блок описания создателя графической базы         (*.cfg  0x68)
----------+---+----------+-------------+---------------------------------+
| позиция |len|   тип    |     имя     |      Описание                   |
+---------+---+----------+-------------+---------------------------------+
|  4 |  4 | 2 |  int     | Num_str     | число строк текста              |
|  6 |  6 | 2 |  int     | Num_char    | число знаков текста             |
|  8 |  8 | 2 |  int     | num         | длина общего блока              |
| 10 |  A |num| char[num]| base_cr     | общий блок                      |
|    |    |           .  .  .  .  .  .  .  .  .  .                       |
+---------+--------------------------------------------------------------+
</pre>

<h3><a name="Container_Block_CoordinateSystem">3.12 Блок номенклатуры местной системы координат </a></h3>
<pre class="Prog">
  блок номенклатуры местной системы координат      (*.cfg  0x6С)
----------+---+----------+-------------+---------------------------------+
| позиция |len|   тип    |     имя     |      Описание                   |
+---------+---+----------+-------------+---------------------------------+
|  2 |  2 | 4 |  long    |lLenMaxX     | длина всего по X (горизонтали)  |
|  2 |  2 | 4 |  long    |lLenMaxY     | длина всего по Y (вертикали)    |
|  2 |  2 | 4 |  long    |lSmehX       | смещение по X (горизонтали)     |
|  2 |  2 | 4 |  long    |lSmehY       | смещение по Y (вертикали)       |
|  2 |  2 | 2 |  int     |iNumPl       | число планшетов в линейке       |
|  2 |  2 | 2 |  int     |iNumFrst     | номер перв.планшета в перв.лин. |
+---------+---+----------+-------------+---------------------------------+
</pre>

<h3><a name="Container_Block_DataBase">3.13 Блоки настройки семантической базы </a>[0-255]</h3>
<pre class="Prog">
  блок общей настройки семантической базы          (in *.cfg)
 ----------+---+----------+----------------+------------------------------+
 | позиция |len|   тип    |     имя        |      Описание                |
 +---------+---+----------+----------------+------------------------------+
 |  4 |  4 |28 | char[28] | Name_List      | название базы ( в заголовке )|
 | 32 | 20 | 1 |  byte    | bNIndex        | Число первичных индексов     |
 | 33 | 21 |1/8|  byte    | bPoisk         | 1 -> помещение в меню ПОИСК  |
 | 33 | 21 |1/8|  byte    | bIsSystem      |1->системная база с заголовком|
 | 34 | 22 | 2 |  int     | N_Fields (Fld) | число полей в базе           |
 | 36 | 24 | 2 |  int     | num            | длина общего блока           |
 | 38 | 26 |num| char[num]| base_cr        | общий блок                   |
 +---------+--- распределение в общем блоке ------------------------------+
 |    |   2*Fld|setFild[Fld]   Fild        | число цифр до и после точки  |
 |    |    | ? | char[?]  | File           | путь и имя файла             |
 |    |    | ? | char[?]  | Name[i]        | Идентификатор поля           |
 |    |    | ? | char[?]  | Type[i]        | тип поля                     |
 |    |    | ? | char[?]  | MetH[i]        | строка вызова классификатора |
 |    |    | ? | char[?]  | Help[i]        | подсказка по содержимому поля|
 |    |    |           .  .  .  .  .  .  .  .  .  .                       |
 +---------+--------------------------------------------------------------+
</pre>
