<h1>Пользовательская конфигурация проекта (старый
формат)</h1>

<p ALIGN="JUSTIFY">В файле пользовательской конфигурации
хранится временное состояние рабочей среды,
сохранённое в последней сессии работы с картой.
Главный принцип заключается в том, что
пользователь должен попасть в то-же окружение
что и было перед выходом из программы.</p>

<p>Файла пользовательской конфигурации проекта
может находится в любом месте. Имя файла может
иметь любое (желательно до 8 знаков и без русских
букв, для связи с DOS). Расширение файла – “<strong>.CFM</strong>”.</p>

<p><strong>Структура файла</strong> состоит из следующих
частей:</p>

<table>
  <tr>
    <th>Адрес в файле</th>
    <th>Название блока</th>
  </tr>
  <tr>
    <td class="RefAdres">00h – 01Fh</td>
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

<h2><a name="Container_Head">1. Структура</a></h2>

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
</table>

<h3>1.2<a name="Container_Blocks">. Индексы</a></h3>

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
    <td>Блоки тематического меню уровня [1-9]</td>
  </tr>
  <tr>
    <td class="RefAdres">10</td>
    <td class="RefAdres">48h</td>
    <td>4(long)</td>
    <td>Блок плоскостных объектов карты</td>
  </tr>
  <tr>
    <td class="RefAdres">11</td>
    <td class="RefAdres">4Ch</td>
    <td>4(long)</td>
    <td>Блок штриховых примитивов для плоскости</td>
  </tr>
  <tr>
    <td class="RefAdres">12</td>
    <td class="RefAdres">50h</td>
    <td>4(long)</td>
    <td>Блок линейных объектов карты</td>
  </tr>
  <tr>
    <td class="RefAdres">13</td>
    <td class="RefAdres">54h</td>
    <td>4(long)</td>
    <td>Блок штриховых примитивов для линии *</td>
  </tr>
  <tr>
    <td class="RefAdres">14</td>
    <td class="RefAdres">58h</td>
    <td>4(long)</td>
    <td>Блок точечных объектов карты</td>
  </tr>
  <tr>
    <td class="RefAdres">15</td>
    <td class="RefAdres">5Ch</td>
    <td>4(long)</td>
    <td>Блок штриховых примитивов для точки</td>
  </tr>
  <tr>
    <td class="RefAdres">16</td>
    <td class="RefAdres">60h</td>
    <td>4(long)</td>
    <td>Блок настройки паролей</td>
  </tr>
  <tr>
    <td class="RefAdres">17</td>
    <td class="RefAdres">64h</td>
    <td>4(long)</td>
    <td>Блок путей к графической базе</td>
  </tr>
  <tr>
    <td class="RefAdres">18</td>
    <td class="RefAdres">68h</td>
    <td>4(long)</td>
    <td>Блок описания создателя графической базы</td>
  </tr>
  <tr>
    <td class="RefAdres">19</td>
    <td class="RefAdres">6Ch</td>
    <td>4(long)</td>
    <td>Блок номенклатуры местной системы координат</td>
  </tr>
  <tr>
    <td class="RefAdres">1016</td>
    <td class="RefAdres">400h</td>
    <td>1024(long[256])</td>
    <td>Блоки настройки семантической базы</td>
  </tr>
</table>

<h3><a name="Container_Blocks">3.2 Блоки тематического меню
уровня [1-9]</a></h3>

<h3><a name="Container_Blocks">3.3 Блок плоскостных объектов
карты </a></h3>

<h3><a name="Container_Blocks">3.4 Блок штриховых примитивов для
плоскости </a></h3>

<h3><a name="Container_Blocks">3.5 Блок линейных объектов карты </a></h3>

<h3><a name="Container_Blocks">3.6 Блок штриховых примитивов для
линии * </a></h3>

<h3><a name="Container_Blocks">3.7 Блок точечных объектов карты </a></h3>

<h3><a name="Container_Blocks">3.8 Блок штриховых примитивов для
точки </a></h3>

<h3><a name="Container_Blocks">3.9 Блок настройки паролей </a></h3>

<h3><a name="Container_Blocks">3.10 Блок путей к графической базе
</a></h3>

<h3><a name="Container_Blocks">3.11 Блок описания создателя
графической базы </a></h3>

<h3><a name="Container_Blocks">3.12 Блок номенклатуры местной
системы координат </a></h3>

<h3><a name="Container_Blocks">3.13 Блоки настройки
семантической базы </a>[0-255]</h3>

<p>структура файла пользовательской настройки [*.CFM]<br>
</p>

<table>
  <tr>
    <th>поз.</th>
    <th>len</th>
    <th>тип</th>
    <th>имя</th>
    <th>Описание</th>
  </tr>
  <tr>
    <td class="RefAdres">00h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">адрес последнего редактируемого блока</td>
  </tr>
  <tr>
    <td class="RefAdres">04h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">размер файла конфигурации ( для след.
    блока )</td>
  </tr>
  <tr>
    <td class="RefAdres">08h</td>
    <td>4</td>
    <td>point</td>
    <td>ms</td>
    <td>текущее место мышки</td>
  </tr>
  <tr>
    <td class="RefAdres">0Ch</td>
    <td>2</td>
    <td>int</td>
    <td>cursor.urow</td>
    <td>текущий уровень</td>
  </tr>
  <tr>
    <td class="RefAdres">0Eh</td>
    <td>12</td>
    <td>point_f</td>
    <td>cursor.beg</td>
    <td>текущие координаты начала</td>
  </tr>
  <tr>
    <td class="RefAdres">1Ah</td>
    <td>12</td>
    <td>point_f</td>
    <td>cursor.d</td>
    <td>приращение координат</td>
  </tr>
  <tr>
    <td class="RefAdres">26h</td>
    <td>2</td>
    <td>int</td>
    <td>geo_fun</td>
    <td>функция работы с картой</td>
  </tr>
  <tr>
    <td class="RefAdres">28h</td>
    <td>2</td>
    <td>int</td>
    <td>vid_area</td>
    <td>вид объекта</td>
  </tr>
  <tr>
    <td class="RefAdres">2Ah</td>
    <td>2</td>
    <td>int</td>
    <td>chif_area</td>
    <td>шифр объекта</td>
  </tr>
  <tr>
    <td class="RefAdres">2Ch</td>
    <td>2</td>
    <td>int</td>
    <td>bfil_area</td>
    <td>номер файла базы объекта</td>
  </tr>
  <tr>
    <td class="RefAdres">2Eh</td>
    <td>2</td>
    <td>int</td>
    <td>gfil_area</td>
    <td>номер файла карты объекта</td>
  </tr>
  <tr>
    <td class="RefAdres">30h</td>
    <td>2</td>
    <td>int</td>
    <td>Type_len</td>
    <td>размер шрифта</td>
  </tr>
  <tr>
    <td class="RefAdres">32h</td>
    <td>1</td>
    <td>char</td>
    <td>cursor.setk</td>
    <td>активность сетки (да/нет)</td>
  </tr>
  <tr>
    <td class="RefAdres">33h</td>
    <td>1</td>
    <td>char</td>
    <td>glob</td>
    <td>в прошлом глобальной настройки</td>
  </tr>
  <tr>
    <td class="RefAdres">34h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">контрольная сумма ПЗУ машины привязки</td>
  </tr>
  <tr>
    <td class="RefAdres">38h</td>
    <td>2</td>
    <td>int</td>
    <td>klass_area</td>
    <td>номер класса объекта</td>
  </tr>
  <tr>
    <td class="RefAdres">3Ah</td>
    <td>2</td>
    <td>word</td>
    <td>delta_point_catch</td>
    <td>окрестность точки захвата</td>
  </tr>
  <tr>
    <td class="RefAdres">3Ch</td>
    <td>4</td>
    <td>word :32/8</td>
    <td>glob</td>
    <td>глобальные настройки</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">настройки окна печати</td>
  </tr>
  <tr>
    <td class="RefAdres">60h</td>
    <td>2</td>
    <td>int</td>
    <td>PRINT_WIN-&gt;vys_l</td>
    <td>Высота листа [мм*10]</td>
  </tr>
  <tr>
    <td class="RefAdres">62h</td>
    <td>2</td>
    <td>int</td>
    <td>PRINT_WIN-&gt;chr_l</td>
    <td>Ширина листа [мм*10]</td>
  </tr>
  <tr>
    <td class="RefAdres">64h</td>
    <td>4</td>
    <td>long</td>
    <td>PRINT_WIN-&gt;mash</td>
    <td>масштаб вывода</td>
  </tr>
  <tr>
    <td class="RefAdres">68h</td>
    <td>1</td>
    <td>char</td>
    <td>PRINT_WIN-&gt;urw</td>
    <td>уровень вывода</td>
  </tr>
  <tr>
    <td class="RefAdres">69h</td>
    <td>1/8</td>
    <td>byte : 1</td>
    <td>eqi</td>
    <td>0-принтер 1-плоттер</td>
  </tr>
  <tr>
    <td class="RefAdres">69h</td>
    <td>1/8</td>
    <td>byte : 1</td>
    <td>ramka</td>
    <td>0-с рамкой 1-без рамки</td>
  </tr>
  <tr>
    <td class="RefAdres">69h</td>
    <td>1/8</td>
    <td>byte : 1</td>
    <td>rotor</td>
    <td>1-с поворотом</td>
  </tr>
  <tr>
    <td class="RefAdres">69h</td>
    <td>1/8</td>
    <td>byte : 1</td>
    <td>blkstr</td>
    <td>1-c отсечением надписей</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">настройка принтера</td>
  </tr>
  <tr>
    <td class="RefAdres">70h</td>
    <td>1</td>
    <td>byte</td>
    <td>lpt</td>
    <td>Номер порта LPT (1-3)</td>
  </tr>
  <tr>
    <td class="RefAdres">71h</td>
    <td>1</td>
    <td>byte</td>
    <td>typ_prn</td>
    <td>типа принтера для графики</td>
  </tr>
  <tr>
    <td class="RefAdres">72h</td>
    <td>1</td>
    <td>byte</td>
    <td>kch</td>
    <td>Качество низк. сред. выс.</td>
  </tr>
  <tr>
    <td class="RefAdres">73h</td>
    <td>2</td>
    <td>word</td>
    <td>num_str</td>
    <td>число строк в странице</td>
  </tr>
  <tr>
    <td class="RefAdres">75h</td>
    <td>1/8</td>
    <td>byte : 1</td>
    <td>stop_paper</td>
    <td>запрос ввода листа</td>
  </tr>
  <tr>
    <td class="RefAdres">75h</td>
    <td>1/8</td>
    <td>byte : 1</td>
    <td>from_bufer</td>
    <td>распечатывать через буфер</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">настройка плоттера</td>
  </tr>
  <tr>
    <td class="RefAdres">78h</td>
    <td>1</td>
    <td>byte</td>
    <td>PRINT_WIN-&gt;com_p</td>
    <td>Номер порта COM (1-4)</td>
  </tr>
  <tr>
    <td class="RefAdres">79h</td>
    <td>1</td>
    <td>byte</td>
    <td>PRINT_WIN-&gt;typ_plt</td>
    <td>типа плоттера для графики</td>
  </tr>
  <tr>
    <td class="RefAdres">7Ah</td>
    <td>2</td>
    <td>int</td>
    <td>PRINT_WIN-&gt;spd_p</td>
    <td>Скорость обмена [bod]</td>
  </tr>
  <tr>
    <td class="RefAdres">7Ch</td>
    <td>1</td>
    <td>byte</td>
    <td>PRINT_WIN-&gt;rotor</td>
    <td>1-с поворотом</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">настройка дигитайзера</td>
  </tr>
  <tr>
    <td class="RefAdres">80h</td>
    <td>1</td>
    <td>byte</td>
    <td>SET_ECHO_WIN-&gt;com_p</td>
    <td>Номер порта COM (1-4)</td>
  </tr>
  <tr>
    <td class="RefAdres">81h</td>
    <td>1</td>
    <td>byte</td>
    <td>SET_ECHO_WIN-&gt;typ_d</td>
    <td>тип дигитайзера</td>
  </tr>
  <tr>
    <td class="RefAdres">82h</td>
    <td>2</td>
    <td>int</td>
    <td>SET_ECHO_WIN-&gt;spd_p</td>
    <td>Скорость обмена [bod]</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">другие настройки</td>
  </tr>
  <tr>
    <td class="RefAdres">88h</td>
    <td>1</td>
    <td>byte</td>
    <td>COPY_GEO_BASE-&gt;type</td>
    <td>Тип файла передачи</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">параметры трехмерной графики</td>
  </tr>
  <tr>
    <td class="RefAdres">90h</td>
    <td>8</td>
    <td>double</td>
    <td>A1</td>
    <td></td>
  </tr>
  <tr>
    <td class="RefAdres">98h</td>
    <td>8</td>
    <td>double</td>
    <td>A2</td>
    <td></td>
  </tr>
  <tr>
    <td class="RefAdres">A0h</td>
    <td>8</td>
    <td>double</td>
    <td>XB</td>
    <td></td>
  </tr>
  <tr>
    <td class="RefAdres">A8h</td>
    <td>8</td>
    <td>double</td>
    <td>YB</td>
    <td></td>
  </tr>
  <tr>
    <td class="RefAdres">B0h</td>
    <td>8</td>
    <td>double</td>
    <td>ZB</td>
    <td></td>
  </tr>
  <tr>
    <td colspan="5"></td>
  </tr>
  <tr>
    <td class="RefAdres">С0h</td>
    <td>16</td>
    <td>char[16]</td>
    <td colspan="2">Имя последнего пользователя</td>
  </tr>
  <tr>
    <td class="RefAdres">D0h</td>
    <td>16</td>
    <td>char[16]</td>
    <td colspan="2">название файла классификатора</td>
  </tr>
  <tr>
    <td class="RefAdres">E0h</td>
    <td>16</td>
    <td>char[16]</td>
    <td colspan="2">название файла загружаемых шрифтов</td>
  </tr>
  <tr>
    <td class="RefAdres">F0h</td>
    <td>16</td>
    <td>char[16]</td>
    <td colspan="2">имя пользователя в локальной сети</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">начальные точки окон</td>
  </tr>
  <tr>
    <td class="RefAdres">100h</td>
    <td>512</td>
    <td>point[128]</td>
    <td>set_xy_win</td>
    <td>начальные точки окон</td>
  </tr>
  <tr>
    <td colspan="5"><p class="Remark">начальные адреса блоков</td>
  </tr>
  <tr>
    <td class="RefAdres">300h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">адрес блока путей к главной
    конфигурации</td>
  </tr>
  <tr>
    <td class="RefAdres">304h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">адрес пути к последнему файлу уровня</td>
  </tr>
  <tr>
    <td class="RefAdres">308h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">адрес блока блокировки меню объектов</td>
  </tr>
  <tr>
    <td class="RefAdres">30Ch</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">адрес блока настройки дигитайзера</td>
  </tr>
  <tr>
    <td class="RefAdres">310h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">адрес блока настройки векторизатора</td>
  </tr>
  <tr>
    <td class="RefAdres">314h</td>
    <td>4</td>
    <td>long</td>
    <td colspan="2">. . . . . . . . .</td>
  </tr>
  <tr>
    <td class="RefAdres">400h</td>
    <td>1024</td>
    <td>long[256]</td>
    <td colspan="2">начальные адреса блоков настройки базы</td>
  </tr>
  <tr>
    <td class="RefAdres">800h</td>
    <td colspan="4">блоки настройки базы и блоки описания
    объектов карты</td>
  </tr>
  <tr>
    <td class="RefAdres"></td>
    <td colspan="4"><p class="Refer">. . . . . . . . . .</td>
  </tr>
</table>
