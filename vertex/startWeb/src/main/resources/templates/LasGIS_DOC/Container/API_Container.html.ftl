<h1>API доступа к данным Контейнера</h1>

<p>Для работы с контейнером (см. <a href="Container.html">Внутренний
формат файла - контейнера</a>) применяется
стандартный API, который заключён в библиотеке <span
class="Refer">LG_container.lib</span>. При этом библиотека должна
поддерживать работу со всеми предыдущими
файлами - контейнерами вне зависимости от их
версии.</p>

<p>Главная задача API это управление блоками
контейнера: <span class="Refer">запись блока</span> и <span
class="Refer">чтение блока</span>, но кроме этого
необходимы и сопутствующие функции.</p>

<p>В библиотеке реализуются 2 класса <span class="Refer">&quot;LG_Container&quot;
</span>и <span class="Refer">&quot;LG_Container_Block&quot;</span>. Первый
класс отвечает за работу с самим файлом
-контейнером, а второй поддерживает работу с
открытым блоком:

<ul>
  <li><a href="#LG_Container">LG_Container</a> - класс реализует работу с
    файлом<ul>
      <li><a href="#LG_Container_Construct">LG_Container(char * FileName)</a> - открыть
        существующий файл - контейнер</li>
      <li><a href="#LG_Container_Construct_Open">LG_Container(...)</a> - Создать
        пустой контейнер и открыть его</li>
      <li><a href="#LG_Container_Destruct">~LG_Container()</a> - Закрываем
        открытый ранее класс контейнера</li>
      <li><a href="#LG_Container_Lock_Block">Lock_Block(...)</a> - заблокировать
        блок</li>
      <li><a href="#LG_Container_UnLock_Block">UnLock_Block(...)</a> -
        разблокировать блок</li>
      <li><a href="#LG_Container_Load_Block">Load_Block()</a> - Загрузить блок
        (подблок) как массив байт</li>
      <li><a href="#LG_Container_Save_Block">Save_Block()</a> - Сохранить блок
        (подблок) как массив байт</li>
      <li><a href="#LG_Container_Save_Block">Open_Block()</a> - Открыть блок
        (подблок) как класс</li>
    </ul>
  </li>
  <li><a href="#LG_Container_Block">LG_Container_Block</a> - класс реализует
    работу с открытым блоком<ul>
      <li><a href="#LG_Container_Block_Lock">Lock()</a> - заблокировать блок</li>
      <li><a href="#LG_Container_Block_UnLock">UnLock()</a> - разблокировать
        блок</li>
      <li><a href="#LG_Container_Block_Save">Save()</a> - Сохранить блок</li>
      <li><a href="#LG_Container_Block_Load">Load()</a> - Загрузить блок</li>
      <li><a href="#LG_Container_Block_Add_Short">Add(short Num)</a> - Добавить к
        телу блока короткое целое число</li>
      <li><a href="#LG_Container_Block_Add_Integer">Add(int Num)</a> - Добавить к
        телу блока целое число</li>
      <li><a href="#LG_Container_Block_Add_Double">Add(double Num)</a> - Добавить к
        телу блока вещественное число</li>
      <li><a href="#LG_Container_Block_Add_String">Add(char * zStr)</a> - Добавить к
        телу блока строку</li>
      <li><a href="#LG_Container_Block_Add_Void">Add(void * mem, int len)</a> - Добавить
        к телу блока некоторый блок памяти</li>
      <li><a href="#LG_Container_Block_Get_Short">short Get()</a> - Получить из тела
        блока короткое целое число</li>
      <li><a href="#LG_Container_Block_Get_Integer">int Get()</a> - Получить из тела
        блока целое число</li>
      <li><a href="#LG_Container_Block_Get_Double">double Get()</a> - Получить из
        тела блока вещественное число</li>
      <li><a href="#LG_Container_Block_Get_String">char * Get()</a> - Получить из
        тела блока строку</li>
      <li><a href="#LG_Container_Block_Get_Void">void * Get(int len)</a> - Получить из
        тела блока некоторый блок памяти</li>
    </ul>
  </li>
</ul>

<h2>1. <a name="LG_Container">LG_Container - класс реализует работу с
файлом</a></h2>

<h3>1.1. <a name="LG_Container_Construct">LG_Container(char * FileName) - открыть
существующий файл - контейнер</a></h3>

<h3>1.2. <a name="LG_Container_Construct_Open">LG_Container(...)</a> - Создать
пустой контейнер и открыть его</h3>

<p>Функция создаёт пустой файл - контейнер на
основании вводимых параметров функции и
открывает его для работы..</p>

<pre>BOOL <span class="ProgTerm">LG_Container::LG_Container</span>(
   char * <span class="ProgTerm">NameFile</span>,   <span class="ProgComment">//(Inp) полный путь к файлу - контейнеру</span>
   char * <span class="ProgTerm">Aleas</span>,      <span class="ProgComment">//(Inp) Тип контейнера</span>
   char * <span class="ProgTerm">Descr</span>,      <span class="ProgComment">//(Inp) Название контейнера</span>
   WORD   <span class="ProgTerm">VerBLOCK</span>,   <span class="ProgComment">//(Inp) Версия типа контейнера</span>
   WORD   <span class="ProgTerm">VerMain</span>,    <span class="ProgComment">//(Inp) Версия контейнера</span>
   WORD   <span class="ProgTerm">NumIndex</span>    <span  class="ProgComment">//(Inp) Число индексов в главном блоке</span>
);</pre>

<p>где:</p>

<p><span class="ProgTerm">NameFile</span> - полный путь к
создаваемому файлу - контейнеру.</p>

<p><span class="ProgTerm">Aleas</span> - значение для заполнения
поля <a href="Container.html#Container_Head_Type">&quot;Тип
контейнера&quot; </a>в заголовке создаваемого файла -
контейнера. </p>

<p><span class="ProgTerm">Descr</span> - значение для заполнения
поля <a href="Container.html#Container_Head_Describe">&quot;Название
файла - контейнера&quot;</a> в заголовке создаваемого
файла - контейнера. </p>

<p><span class="ProgTerm">VerBLOCK</span> - значение для заполнения
поля <a href="Container.html#Container_Head_Type">&quot;Версия типа
контейнера&quot;</a> в заголовке создаваемого файла -
контейнера. </p>

<p><span class="ProgTerm">VerMain</span> - значение для заполнения
поля <a href="Container.html#Container_Head_Version">&quot;Версия
контейнера&quot;</a> в заголовке создаваемого файла -
контейнера. По умолчанию присваивается
максимальное значение версии, поддерживаемое
данной библиотекой<span class="Refer"> LG_container.lib</span>.</p>

<h3>1.3. <a name="LG_Container_Destruct">~LG_Container()</a> - Закрываем
открытый ранее контейнер</h3>

<h3>1.4. <a name="LG_Container_Lock_Block">Заблокировать блок</a></h3>

<pre>BOOL <span class="ProgTerm">LG_Container::Lock_Block</span>(
   DWORD * <span class="ProgTerm">Index</span>,  <span class="ProgComment"> //(Inp) индекс</span>
   int     <span class="ProgTerm">SizeInd</span> <span class="ProgComment"> //(Inp) размерность вложения индекса</span>
);</pre>

<h3>1.5. <a name="LG_Container_UnLock_Block">Разблокировать блок</a></h3>

<pre>BOOL <span class="ProgTerm">LG_Container::UnLock_Block</span>(
   DWORD * <span class="ProgTerm">Index</span>,  <span class="ProgComment"> //(Inp) индекс</span>
   int     <span class="ProgTerm">SizeInd</span> <span class="ProgComment"> //(Inp) размерность вложения индекса</span>
);</pre>

<p> </p>

<h3>1.6. <a name="LG_Container_Load_Block">Загрузить блок</a></h3>

<pre>BOOL <span class="ProgTerm">LG_Container::Load_Block</span>(
   void ** <span class="ProgTerm">Block</span>,  <span class="ProgComment"> //(Out) сюда надо положить тело блока</span>
   DWORD * <span class="ProgTerm">Length</span>, <span class="ProgComment"> //(Out) сюда надо положить размер блока</span>
   DWORD * <span class="ProgTerm">Index</span>,  <span class="ProgComment"> //(Inp) индекс</span>
   int     <span class="ProgTerm">SizeInd</span> <span class="ProgComment"> //(Inp) размерность вложения индекса</span>
);</pre>

<h3>1.7. <a name="LG_Container_Save_Block">Сохранить блок</a></h3>

<pre>BOOL <span class="ProgTerm">LG_Container::Save_Block</span>(
   void *  <span class="ProgTerm">Block</span>,  <span class="ProgComment"> //(Inp) тело блока</span>
   DWORD   <span class="ProgTerm">Length</span>, <span class="ProgComment"> //(Inp) размер блока</span>
   DWORD * <span class="ProgTerm">Index</span>,  <span class="ProgComment"> //(Inp) индекс</span>
   int     <span class="ProgTerm">SizeInd</span> <span class="ProgComment"> //(Inp) размерность вложения индекса</span>
);</pre>

<h3>1.8. <a name="LG_Container_Open_Block">Open_Block()</a> - Открыть блок
(подблок) как класс</h3>

<pre>LG_Container_Block * <span class="ProgTerm">LG_Container::Open_Block</span>(
   DWORD * <span class="ProgTerm">Index</span>,  <span class="ProgComment"> //(Inp) индекс</span>
   int     <span class="ProgTerm">SizeInd</span> <span class="ProgComment"> //(Inp) размерность вложения индекса</span>
);</pre>

<h2>2. <a name="LG_Container_Block">LG_Container_Block</a> - класс реализует
работу с открытым блоком</h2>

<h3><a name="LG_Container_Block_Lock">Lock()</a> - заблокировать блок</h3>

<h3><a name="LG_Container_Block_UnLock">UnLock()</a> - разблокировать
блок</h3>

<h3><a name="LG_Container_Block_Save">Save()</a> - Сохранить блок</h3>

<h3><a name="LG_Container_Block_Load">Load()</a> - Загрузить блок</h3>

<h3><a name="LG_Container_Block_Add_Short">Add(short Num)</a> - Добавить к телу блока короткое целое число</h3>

<h3><a name="LG_Container_Block_Add_Integer">Add(int Num)</a> - Добавить к телу блока целое число</h3>

<h3><a name="LG_Container_Block_Add_Double">Add(double Num)</a> - Добавить к телу блока вещественное число</h3>

<h3><a name="LG_Container_Block_Add_String">Add(char * zStr) - Добавить к телу блока строку</a></h3>

<h3><a name="LG_Container_Block_Add_Void">Add(void * mem, int len)</a> - Добавить к телу блока некоторый блок памяти</h3>

<h3><a name="LG_Container_Block_Get_Short">short Get()</a> - Получить из тела блока короткое целое число</h3>

<h3><a name="LG_Container_Block_Get_Integer">int Get()</a> - Получить из тела блока целое число</h3>

<h3><a name="LG_Container_Block_Get_Double">double Get()</a> - Получить из тела блока вещественное число</h3>

<h3><a name="LG_Container_Block_Get_String">char * Get()</a> - Получить из тела блока строку</h3>

<h3><a name="LG_Container_Block_Get_Void">void * Get(int len)</a> - Получить из тела блока некоторый блок памяти</h3>
