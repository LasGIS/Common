<h2></sup></strong>2.1.Главная таблица графических объектов
&lt;Objects&gt;</h2>
</font>

<p>Главная таблица графических объектов содержит
полный список графических объектов для данного
предприятия</p>

<p class="Refer">Структура полей &lt;Objects&gt;</p>

<table WIDTH="685" border="1" cellspacing="0" cellpadding>
  <tr>
    <th WIDTH="8%" VALIGN="top"><strong>ключ</strong></th>
    <th WIDTH="7%" VALIGN="top"><strong>Уник</strong></th>
    <th WIDTH="14%" VALIGN="top"><strong>название</strong></th>
    <th WIDTH="11%" VALIGN="top"><strong>тип</strong></th>
    <th WIDTH="7%" VALIGN="top"><strong>Размер</strong></th>
    <th WIDTH="7%" VALIGN="top"><strong>Умолч.</strong></th>
    <th WIDTH="47%" VALIGN="top"><strong>Описание поля</strong></th>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Перв.</td>
    <td WIDTH="7%" VALIGN="TOP">Да</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>UnicNum</strong></td>
    <td WIDTH="11%" VALIGN="TOP">numeric</td>
    <td WIDTH="7%" VALIGN="TOP">10</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">Уникальный номер объекта (дается
    при создании)</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>NextNum</strong></td>
    <td WIDTH="11%" VALIGN="TOP">numeric</td>
    <td WIDTH="7%" VALIGN="TOP">10</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">Номер для связки объектов в
    цепочки</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>BaseNum</strong></td>
    <td WIDTH="11%" VALIGN="TOP">numeric</td>
    <td WIDTH="7%" VALIGN="TOP">10</td>
    <td WIDTH="7%" VALIGN="TOP">10</td>
    <td WIDTH="47%" VALIGN="TOP">номер связи объекта с
    атрибутивной базой</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Втор.</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Xmin</strong></td>
    <td WIDTH="11%" VALIGN="TOP">decimal</td>
    <td WIDTH="7%" VALIGN="TOP">12,3</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">минимальная координата &lt;X &gt;</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Втор.</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Ymin</strong></td>
    <td WIDTH="11%" VALIGN="TOP">decimal</td>
    <td WIDTH="7%" VALIGN="TOP">12,3</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">минимальная координата &lt;Y &gt;</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Втор.</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Xmax</strong></td>
    <td WIDTH="11%" VALIGN="TOP">decimal</td>
    <td WIDTH="7%" VALIGN="TOP">12,3</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">максимальная координата &lt;X &gt;</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Втор.</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Ymax</strong></td>
    <td WIDTH="11%" VALIGN="TOP">decimal</td>
    <td WIDTH="7%" VALIGN="TOP">12,3</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">максимальная координата &lt;Y &gt;</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Втор.</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>LevelMin</strong></td>
    <td WIDTH="11%" VALIGN="TOP">smallint</td>
    <td WIDTH="7%" VALIGN="TOP">2</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">минимальный уровень карты для
    видимости</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">Втор.</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>LevelMax</strong></td>
    <td WIDTH="11%" VALIGN="TOP">smallint</td>
    <td WIDTH="7%" VALIGN="TOP">2</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">максимальный уровень карты для
    видимости</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Enterprise</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">номер предприятия, в котором
    работает пользователь </td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>UserSet</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">номер пользователя, последний
    раз редактировавшего объект</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>DataSet</strong></td>
    <td WIDTH="11%" VALIGN="TOP">datetime</td>
    <td WIDTH="7%" VALIGN="TOP">8</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">дата создания/редактирования
    объекта</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas0</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">0 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas1</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">1 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas2</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">2 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas3</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">3 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas4</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">4 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas5</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">5 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas6</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">6 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas7</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">7 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Clas8</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="47%" VALIGN="TOP">8 уровень классификатора</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Color</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">параметр A определяет цвет
    объекта </td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Height</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">определяет высоту объекта в [мм]</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>Width</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">определяет ширину объекта в [мм]</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>TypeMapO</strong></td>
    <td WIDTH="11%" VALIGN="TOP">tinyint</td>
    <td WIDTH="7%" VALIGN="TOP">1</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">тип графического объекта (линия ,
    точка и т.д.)</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>TypeNum</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">номер типа графического объекта
    (2-линия и т.д.)</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>SizeMapO</strong></td>
    <td WIDTH="11%" VALIGN="TOP">int</td>
    <td WIDTH="7%" VALIGN="TOP">4</td>
    <td WIDTH="7%" VALIGN="TOP">0</td>
    <td WIDTH="47%" VALIGN="TOP">размер объекта в байтах</td>
  </tr>
  <tr>
    <td WIDTH="8%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="7%" VALIGN="TOP">&nbsp;</td>
    <td WIDTH="14%" VALIGN="TOP"><strong>MapO</strong></td>
    <td WIDTH="11%" VALIGN="TOP">image</td>
    <td WIDTH="7%" VALIGN="TOP">16</td>
    <td WIDTH="7%" VALIGN="TOP">null</td>
    <td WIDTH="47%" VALIGN="TOP">содержание объекта (определяется
    по типу объекта)</td>
  </tr>
</table>
