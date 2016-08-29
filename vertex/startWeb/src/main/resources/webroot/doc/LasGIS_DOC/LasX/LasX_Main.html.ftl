<h2>Главный объект “Main”</h2>

<p>Главный объект <spin COLOR="#008080"><strong>Main</strong></font> служит
для доступа к географической базе данных “LasGIS”
через файл главной конфигурации проекта(.cfg). Объект <font
COLOR="#008080"><strong>Main</strong></font> является оконным объектом, на
котором находится:

<ol>
  <li>Список слоев и типо-объектов слоя системы в виде
    дерева (tree view control).</li>
  <li>Блок с кнопками (Toolbar), который отображает общие
    свойства доступа к базе. Нажатая кнопка отражает
    включенное свойство и наоборот. Блок содержит
    следующие свойства:</li>
</ol>

<ul>
  <strong>
  <li>[Р] </strong><u>р</u>астровая карта- показывать растровую
    карту</li>
  <strong>
  <li>[Н] </strong><u>н</u>адписи на карте- показывать надписи на
    карте</li>
  <strong>
  <li>[О] </strong><u>о</u>тметки высот- показывать отметки
    высот</li>
  <strong>
  <li>[К] </strong>только <u>к</u>онтур - показывать плоскостные
    объекты в виде контура</li>
  <strong>
  <li>[C] </strong>координатная сетка- показывать
    координатную сетку с указанием координат</li>
</ul>

<p class="Remark">Свойства</p>

<table>
  <tr>
    <th>Название</th>
    <th>доступ</th>
    <th>тип</th>
    <th>Описание</th>
  </tr>
  <tr>
    <td class="RefAdres"><a HREF="LasX_Layers.html">Layers</a></td>
    <td>ReadOnly</td>
    <td><a href="LasX_Layer.html">Layer[]</a></td>
    <td>содержит коллекцию объектов типа Layer,</td>
  </tr>
  <tr>
    <td class="RefAdres">viewGridChart</td>
    <td>ReadWrite</td>
    <td>bool</td>
    <td>Определяет показ координатной сетки.</td>
  </tr>
  <tr>
    <td class="RefAdres">viewRastr</td>
    <td>ReadWrite</td>
    <td>bool</td>
    <td>Определяет показ растровой карты.</td>
  </tr>
  <tr>
    <td class="RefAdres">viewText</td>
    <td>ReadWrite</td>
    <td>bool</td>
    <td>Определяет показ надписей на карте.</td>
  </tr>
  <tr>
    <td class="RefAdres">viewHeigtMark</td>
    <td>ReadWrite</td>
    <td>bool</td>
    <td>Определяет показ высотных отметок.</td>
  </tr>
  <tr>
    <td class="RefAdres">viewLineOnly</td>
    <td>ReadWrite</td>
    <td>bool</td>
    <td>Определяет показ плоскостных объектов в виде
контура.</td>
  </tr>
  <tr>
    <td class="RefAdres">viewSimPoligon</td>
    <td>ReadWrite</td>
    <td>bool</td>
    <td>Определяет упрощённое рисование полигонов.</td>
  </tr>
  <tr>
    <td class="RefAdres">BaseAgent</td>
    <td>ReadOnly</td>
    <td>BaseAgent</td>
    <td>При вызове создаёт объект (BaseAgent)</td>
  </tr>
</table>

<p class="Remark">Методы</p>

<table>
  <tr>
    <th>Метод</th><th>Описание</th>
  </tr><tr>
    <td>
<pre class="Prog">Sub <a href="#Load">Load</a>(
      <span class="ProgTerm">NameBASE</span> As String,
      <span class="ProgTerm">UserName</span> As String,
      <span class="ProgTerm">Password</span> As String
)</pre>
    </td><td>
      Метод открывает доступ к географической базе данных
      "LasGIS" через файл главной конфигурации проекта (.cfg).
    </td>
  </tr><tr>
    <td>
<pre class="Prog">Sub <a href="#Load">Load</a>(
      <span class="ProgTerm">NameBASE</span> As String,
      <span class="ProgTerm">UserName</span> As String,
      <span class="ProgTerm">Password</span> As String
)</pre>
    </td><td>
      Метод открывает доступ к географической базе данных
      "LasGIS" через файл главной конфигурации проекта (.cfg).
    </td>
  </tr><tr>
    <td>
<pre class="Prog">Sub <a href="#Load">Load</a>(
      <span class="ProgTerm">NameBASE</span> As String,
      <span class="ProgTerm">UserName</span> As String,
      <span class="ProgTerm">Password</span> As String
)</pre>
    </td><td>
      Метод открывает доступ к географической базе данных
      "LasGIS" через файл главной конфигурации проекта (.cfg).
    </td>
  </tr>
</table>

<p class="Remark">События</p>

<h2>Подробное описание</h2>

<h3>Свойства:</h3>

<h4>Свойство &lt;object <a href="LasX_Layers.html">Layers</a>&gt; [r]</h4>

<p>содержит коллекцию объектов типа <a href="LasX_Layer.html">Layer</a>,
определяющих все существующие в данной
конфигурации слои и типо-объекты слоя. В данный
момент слои можно только читать и устанавливать
видимость слоя или типо-объекта слоя. Число
объектов </p>

<h4><u><em>Свойство</em></u> &lt;bool viewGridChart&gt; [rw]</h4>

<p>Определяет показ координатной сетки. Свойство
можно как читать, так и изменять. При изменении
значения <strong>viewGridChart</strong> карта, связанная в
данный момент с главным объектом <strong>Main, </strong>будет
перерисовываться </p>

<h4><em><u>Свойство</u> </em>&lt;bool viewRastr&gt; [rw]</h4>

<p>Определяет показ растровой карты.</p>

<h4><em><u>Свойство</u> </em>&lt;bool viewText&gt; [rw]</h4>

<p>Определяет показ надписей на карте.</p>

<h4>Свойство &lt;bool viewHeigtMark&gt; [rw]</h4>

<p>Определяет показ высотных отметок.</p>

<h4>Свойство &lt;bool viewLineOnly&gt; [rw]</h4>

<p>Определяет показ плоскостных объектов в виде
контура.</p>

<h4>Свойство &lt;bool viewSimPoligon&gt; [rw]</h4>

<p>Определяет упрощённое рисование полигонов.</p>

<h3>Методы:</h3>

<h4>Метод &lt;Load&gt;</h4>

<pre class="Prog">Sub Load(
  <span class="ProgTerm">NameBASE</span> As String,
  <span class="ProgTerm">UserName</span> As String,
  <span class="ProgTerm">Password</span> As String
)</pre>

<!--<p class="Prog">Load(<span class="ProgComment">[in]</span> BSTR <span class="ProgTerm">NameBASE</span>,
<span class="ProgComment">[in]</span> BSTR <span class="ProgTerm">UserName</span>,
<span class="ProgComment">[in]</span> BSTR <span class="ProgTerm">Password</span>);</p>-->

<p>Метод открывает доступ к географической базе данных
“LasGIS” через файл главной конфигурации проекта (.cfg).</p>

<p class="Remark">Параметры:</p>

<p><span class="Refer">NameBASE</span> - Является адресом файла главной конфигурации проекта (.cfg).</p>
<p><span class="Refer">UserName</span> - Имя пользователя, зарегистрированного в данном проекте.</p>
<p><span class="Refer">Password</span> - Пароль этого пользователя.</p>

<h4>Функция &lt;GetLayer&gt;</h4>

<pre class="Prog">Function <span class="ProgTerm">GetLayer</span>(
  <span class="ProgTerm">ByType</span> As Integer,
  <span class="ProgTerm">Var</span>
) As <span class="ProgTerm">Layer</span></pre>

<p>Функция позволяет находить ссылку на описание типового объекта различными способами,
в зависимости от параметра <span class="ProgTerm">ByType</span>.</p>


<!--<table border="0" width="800">
  <tr>
    <td></u>название</td>
    <td>параметры</td>
    <td></strong>описание</td>
  </tr>
  <tr>
    <td></u></td>
    <td>BSTR </td>
    <td></strong></td>
  </tr>
  <tr>
    <td><strong><font COLOR="#008080">LinkMap</font></strong></td>
    <td></td>
    <td>связать главный объект <strong>Main</strong> с существующим
    объектом <a href="LasX_Map.html">Map</a></td>
  </tr>
  <tr>
    <td><font COLOR="#008080"><strong>SetLayer</strong></font></td>
    <td></td>
    <td>установить видимость слоя.</td>
  </tr>
</table>-->

<h3>События:</h3>

<p class="Prog">Event <span class="ProgTerm">ChangeSelect</span>(ByVal
<span class="ProgTerm">TypeChange</span> As Long)</p>

<p>Событие возникает при изменении состояния объекта Main.
Параметр "<span class="Refer">Type</span>" уточняет, в
какой настройке были произведены изменения:</p>

<table>
  <tr>
    <th>Значение</th>
    <th>Описание параметра <strong>TypeChange</strong></th>
  </tr><tr>
    <td class='Refer' align='right'>0</td><td>был открыт или закрыт тдельный слой или типо-объект слоя</td>
  </tr><tr>
    <td class='Refer' align='right'>1</td><td>был включен или выключен показ координатной сетки</td>
  </tr><tr>
    <td class='Refer' align='right'>2</td><td>был включен или выключен показ растровой карты</td>
  </tr><tr>
    <td class='Refer' align='right'>3</td><td>был включен или выключен показ надписей на карте</td>
  </tr><tr>
    <td class='Refer' align='right'>4</td><td>был включен или выключен показ отметок высот</td>
  </tr><tr>
    <td class='Refer' align='right'>5</td><td>был включен или выключен показ плоскостных объектов в виде контура</td>
  </tr><tr>
    <td class='Refer' align='right'>6</td><td>был включен или выключен режим упрощённого рисования полигонов</td>
  </tr>
</table>
