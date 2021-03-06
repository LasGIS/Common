<h1>Конфигурация предприятия</h1>

<p>В файле конфигурации предприятия находятся
общие настройки проедприятия.</p>

<p>Файла конфигурации предприятия находится в
общем каталоге предприятия. Имя файла строго
определено:j <span class="Refer">L_GLOBAL.CFP</span>. Расширение
файла – “<strong>.CFP</strong>”.</p>

<p><strong>Файл имеет структуру контейнера. смотри: </strong><a
href="Container.html">Внутренний
формат файла - контейнера</a><strong>.</strong></p>

<h2>1. Описание блоков</h2>

<p>Структура файла настройки проекта [*.CFG]</p>

<table>
  <tr>
    <th><strong>Индекс</strong> </th>
    <th><strong>Адрес</strong> </th>
    <th><strong>Описание</strong> </th>
  </tr>
  <tr>
    <td>0</td>
    <td>32</td>
    <td>Пока не используется</td>
  </tr>
  <tr>
    <td>1</td>
    <td>36</td>
    <td>Блок настройки графической базы </td>
  </tr>
  <tr>
    <td>2</td>
    <td>40</td>
    <td>Блок локализации тематических слоев</td>
  </tr>
  <tr>
    <td>3</td>
    <td>44</td>
    <td>Блок семантических запросов</td>
  </tr>
  <tr>
    <td>4</td>
    <td>48</td>
    <td>Блок семантических таблиц</td>
  </tr>
  <tr>
    <td>5</td>
    <td>52</td>
    <td>Блок номенклатуры местной системы координат</td>
  </tr>
  <tr>
    <td>6</td>
    <td>56</td>
    <td>Блок описания создателя графической базы</td>
  </tr>
</table>

<h2>1.3.1. Блок настройки графической базы</h2>

<p>Блок настройки графической базы состоит из 9
блоков уровня. Блок уровня состоит из N-ого
количества блоков описания подтипа. Блок
описания подтипа может быть следующих типов:

<ol>
  <li>Заголовок слоя</li>
  <li>Графический типо-объект </li>
  <ol>
    <li>Плоскость</li>
    <ol>
      <li>Тест плоскости</li>
    </ol>
    <li>Линия</li>
    <ol>
      <li>Тест линии</li>
    </ol>
    <li>Полоса</li>
    <ol>
      <li>Тест полосы</li>
    </ol>
    <li>Точка</li>
    <ol>
      <li>Тест точки</li>
    </ol>
    <li>Независимый текст</li>
  </ol>
  <li>Растровая карта</li>
  <ol>
    <li>Растровая карта 1 уровня </li>
    <li>Растровая карта 2 уровня </li>
    <li>Растровая карта 3 уровня </li>
    <li>…</li>
    <li>Растровая карта 9 уровня</li>
  </ol>
</ol>

<p>Каждый блок описания подтипа состоит из
следующих полей:

<ol>
  <li>Номер подменю в тематическом меню уровня.</li>
  <li>Название типо-объекта в меню слоев.</li>
  <li>Тип локализации (<strong>П6</strong>, <strong>Л5</strong>, <strong>Т4</strong>, и т.д.).</li>
</ol>

<p>&nbsp;</p>

<p>-----------------</p>

<h2>1.3.2. Блок локализации тематических слоев</h2>

<p>Блок локализации тематических слоев связан с
блоком настройки графической базы через поле
типо-объекта. Блок локализации тематических
слоев состоит из 6 блоков на каждый тип
типо-объекта:

<ol>
  <li>Плоскость</li>
  <li>Линия</li>
  <li>Полоса</li>
  <li>Точка</li>
  <li>Надпись</li>
  <li>Растр</li>
</ol>

<p>Блок локализации не зависит от уровня карты.
Например, мы хотим, чтобы здания <u>первого</u>
уровня (в смысле графической базы данных)
отображались и на <u>первом</u> уровне и на <u>втором</u>
уровне (в смысле масштаба отображения). В этом
случае создается тип локализации <strong>“П12”</strong>.
Затем в меню <u>первого</u> уровня создается
подменю со ссылкой на тип <strong>“П12”</strong> и в меню <u>второго</u>
уровня создается подменю со ссылкой на тип <strong>“П12”</strong>.
При этом как в первом уровне, так и во втором
работа будет происходить с одним и тем – же
объектом.</p>

<p>&nbsp;

<ol>
  <li>Классификационная строка данного подтипа.</li>
  <li>Очередность отрисовки данного типо-объекта.</li>
  <li>Номера семантических описаний, присущих
    данному типо-объекту.</li>
  <li>Тип графической базы</li>
</ol>

<ul>
  <ul>
    <li><strong>“LG1”</strong>-Старый формат LasGIS</li>
    <li><strong>“MAR”-</strong>Обменный архивный формат LasGIS</li>
    <li><strong>“MAP”-</strong>Обменный классический формат LasGIS</li>
    <li><strong>“FOR”-</strong>Обменный формат “FORA”</li>
    <li><strong>“LG2”-</strong>Новый формат LasGIS</li>
    <li><strong>“SQL”-</strong>Данные на SQL-сервере в формате LasGIS</li>
    <li><strong>“SFX”-</strong>Данные в формате SFX</li>
    <li><strong>“SHP”-</strong>Данные в формате Shape файла ARCINFO</li>
    <li>… для растровых файлов …</li>
    <li><strong>“BMP”-</strong>Данные в формате <strong>.BMP</strong></li>
    <li><strong>“PCX”-</strong>Данные в формате <strong>.PCX</strong></li>
    <li><strong>“PCM”-</strong>Данные в формате <strong>.PCM</strong></li>
    <li>&nbsp;</li>
  </ul>
</ul>

<ol>
  <li>Полный путь к графической базе</li>
</ol>

<ul>
  <ul>
    <li>Для всех файлов – путь и имя файла</li>
    <li>Для старого и нового формата LasGIS – путь к
      каталогу</li>
  </ul>
</ul>

<ul>
  <ul>
    <li>Для данных на SQL-сервере в формате LasGIS – <strong>“имя_сервера”.“имя_базы”</strong></li>
  </ul>
</ul>

<ul>
  <ul>
    <li>...</li>
  </ul>
</ul>

<ol>
  <li>Уточнение подтипа в общем списке </li>
</ol>

<ul>
  <ul>
    <li>Для старого формата LasGIS – тип локализации <strong>“2.П.5”</strong>
      (пятая плоскость на втором уровене)</li>
    <li>Для обменного архивного формата LasGIS – тип
      локализации <strong>“П.5”</strong> (пятая плоскость)</li>
    <li>Для обменного классического формата LasGIS –
      классификационная строка</li>
    <li>Для растровых файлов – уровень <strong>“2”</strong></li>
    <li>...</li>
  </ul>
</ul>

<h3>1.3.3. Блок семантических запросов</h3>

<h3>1.3.4. Блок семантических таблиц&gt;</h3>
