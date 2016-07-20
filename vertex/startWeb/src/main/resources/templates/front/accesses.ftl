<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="../css/main.css">
  <script type="text/javascript">ImagePrefix = "/images/";</script>
  <script src="../js/menu.js" type="text/javascript"></script>
  <title>Способы доступа программного комплекса 'LasGIS'</title>
  <STYLE type="text/css">
    OL.roman { list-style-type: lower-roman }
    OL.arabik { list-style-type: arabic-numbers }
  </STYLE>
</head>

<body>
<h3>Способы доступа программы LasGIS</h3>
<p class="ind">Программный комплекс "LasGIS" может работать:</p>
<ul>
  <li>В локальной сети;</li>
  <li>Как <strong>Active</strong> в постороннем приложении (например на Visual Basic(е));</li>
  <li>В режиме удаленного доступа (связь через <strong>WinSocket</strong>);</li>
  <li>Как <strong>Active</strong> в постороннем приложении, в режиме удаленного доступа;</li>
  <li>Как <strong>Active</strong> в <strong>Internet</strong> страницах через удаленный доступ.</li>
</ul>

<h4><a name='DOS'>Программа, работающая под DOS</a></h4>
<p class="ind">Программа "LasGIS" работающая в локальной сети была создана в
  1993 году и работала в системе DOS. В качестве компилятора был выбран
  "<strong>Borland C++</strong>" и "<strong>Paradox Engine</strong>". При этом осуществлялся запрос
  к графической и семантической базе данных.
</p>
<p class="ind">В графической базе хранится информация по графическим объектам
  карты (векторная графика в виде плоскостных, линейных и точечных объектов) и
  растровая подложка. Для векторной  графики был выбран оригинальный формат
  хранения. Система доступа писалась на C++.
</p>
<p class="ind">В семантической базе данных хранится атрибутивная информация о
  графических объектах карты. Семантическая база данных представляет собой
  систему реляционных таблиц (формат <strong>Paradox .db</strong>) с доступом через
  <strong>Paradox Engine</strong>. При этом отдельная таблица соответствует отдельному
  типу графических объектов, соотношение которых определяется в настройке
  системы.
</p>
<h4><a name='local_Win'>Работа в локальной сети (Windows)</a></h4>
<p class="ind">Работу системы "LasGIS" в локальной сети в среде Windows
  95/98/2000/NT/XP определяют два модуля:
</p>
<p><img
  alt="Access_metods_1" src="../images/access_methods_1.gif"
  title='Работа системы "LasGIS" в локальной сети'
/></p>
<p style="TEXT-ALIGN: center">Рисунок 1.</p>
<ol style="list-style-type: arabic-numbers" >
  <li>Программа для работы <strong>LasGIS.exe</strong>. Основная программа в которой
    происходит работа с картой (навигация, печать и т.д.), выполнение
    семантических запросов (просмотр и редакция справки, поиск конкретных
    объектов и т.д.), работа с графическими объектами (создание, визуальная
    редакция, редакция по точкам и т.д.).
  </li>
  <li>Модуль связи с базой данных <strong>LGL_Map.dll</strong>. Модуль связи транслирует
    запрос основной программы в SQL запросы к семантическим базам данных и
    графическим файлам. Семантический запрос осуществляется через стандартные
    <strong>ODBC</strong> драйвера (DNS = "LasGIS").
  </li>
</ol>
<p class="ind">Интерфейс локального доступа представляет собой определенный
  набор функций, связывающих библиотеку динамической компоновки <strong>LGL_Map.dll</strong>
  с основной программой <strong>LasGIS.exe</strong>. Интерфейс локального доступа это
  основной интерфейс системы, который работает и в других частях системы
  (см далее).
</p>
<h4><a name='ActiveX'>Работа в приложении (COM объект LasX)</a></h4>
<p class="ind">Для доступа к базам данных LasGIS из других приложений был создан
  <strong>ActiveX</strong> объект "<strong>LasX</strong>" (см. рисунок 2).</p>
<p class="ind">Объект <strong>LasX</strong> имеет в себе два объекта типа <strong>Control</strong>
  это окно <strong>Main</strong> и окно <strong>Map</strong>. В окне <strong>Map</strong> расположена карта,
  а в окне <strong>Main</strong> находится список слоев и объектов слоя, которыми
  пользователь может управлять (закрыть или открыть для отображения в окне Map).
  Если нет необходимости отображать список слоёв, окно <strong>Main</strong> можно не
  создавать.
</p>
<p><img
  alt="Access_metods_2" src="../images/access_methods_2.gif"
  title='Доступ к базам LasGIS из других приложений.'
/></p>
<p style="TEXT-ALIGN: center">Рисунок 2.</p>
<p class="ind">Объект <strong>LasX</strong> через <em>интерфейс локального доступа</em>
  связан с модулем связи <strong>LGL_Map.dll</strong>, а через него с базой данных
  <strong>LasGIS</strong>.
</p>
<h4><a name='remote_access'>Работа с удаленным доступом</a></h4>
<p class="ind">Для работы с удаленным доступом был создан сервер приложения
  <strong>LG_Server.exe</strong> и модуль удаленного доступа <strong>LGL_MapSocket.dll</strong>.
  Эти два модуля как бы разрывают <em>интерфейс локального доступа</em> и
  транслируют запрос через <strong>Internet</strong>:
</p>
<p><img
  alt="Access_metods_3" src="../images/access_methods_3.gif"
  title='Доступ к базам LasGIS через удаленный доступ.'
/></p>
<p style="TEXT-ALIGN: center">Рисунок 3.</p>
<p class="ind">Кроме того, сервер приложения поддерживает несколько связей и
  выполняет функции администратора для обеспечения легальности доступа и
  ограничения полномочий (простой пользователь может только смотреть карту, но
  не редактировать).
</p>
<p class="ind">Связь между сервер приложением и модулем удаленного доступа
  осуществляется через <strong>WinSocket</strong> по <strong>URL</strong>, который передается в
  web-страницу в параметрах объекта.
</p>
<p class="ind">В качестве приложения, работающего через удаленный доступ, может
  быть любое приложение, работающее через интерфейс локального доступа:
</p>
<ul>
  <li>Основное приложение <strong>LasGIS</strong></li>
  <li><strong>Active</strong> объект <strong>LasX</strong></li>
  <li>Второй сервер приложения <strong>LG_Server.exe</strong>.</li>
</ul>
<p class="ind">В последнем случае второй <strong>"LG_Server.exe"</strong> является
  дополнительным буфером, который замыкает запрос. Если информация,
  необходимая клиенту уже есть на данном сервере, то запрос выполняется на этом
  сервере, а не транслируется к первому серверу.
</p>
<h4><a name='remote_access_interface'>&nbsp;</a></h4>
<p class="ind">Информация, передаваемая по удаленному доступу:</p>
<table border="1">
  <tr><th>Тип запроса</th><th>Запрос</th><th>Ответ</th></tr>
  <tr>
    <td>
      Векторная графика
    </td><td>
      Границы прямоугольника запрашиваемой зоны в абсолютных координатах плюс
      список необходимых для передачи слоев и типообъектов слоя.
    </td><td>
      Список графических объектов в виде заголовка и списка точек объекта.
      Точка объекта это координаты X,Y,Z.
    </td>
  </tr><tr>
    <td>
      Растровая карта
    </td><td>
      Границы прямоугольника запрашиваемой зоны с указанием разрешения
      устройства экрана (принтера) в абсолютных координатах.
    </td><td>
      Файл растровой информации в сжатом виде (сжатие в формате .pcx)
    </td>
  </tr><tr>
    <td>
      Атрибутивная информация
    </td><td>
      Строка SQL запроса
    </td><td>
      Описание заголовка + последовательный вывод всех полей результирующей
      таблицы или признак успеха, если был запрос типа INSERT
    </td>
  </tr>
</table>

<p class="ind"><br/>Вот пример web-страницы с получением карты:<br/></p>

<p class="Prog">
&lt;html&gt;&lt;head&gt;<br/>
&lt;meta http-equiv="Content-Type" content="text/html; charset=UTF-8"&gt;<br/>
<br/>
&lt;script LANGUAGE="VBScript"&gt;<br/>
&lt;!--<br/>
&nbsp;&nbsp;Sub Window_OnLoad()<br/>
&nbsp;&nbsp;&nbsp;&nbsp;Main.Load "//laskin.omsk.ru/SetMap1/", "User", ""<br/>
&nbsp;&nbsp;&nbsp;&nbsp;Map.Main = Main<br/>
&nbsp;&nbsp;&nbsp;&nbsp;Map.GotoPoint 2, 10000, 15450.0, 9330.0<br/>
&nbsp;&nbsp;End Sub<br/>
<br/>
&nbsp;&nbsp;Sub Main_ChangeSelect(TypeChange)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;Map.Redraw<br/>
&nbsp;&nbsp;End Sub<br/>
//--&gt;<br/>
&lt;/script&gt;<br/>
<br/>
&lt;title&gt;Проверка LasX на VBScript&lt;/title&gt;<br/>
&lt;/head&gt;&lt;body&gt;<br/>
&lt;h3&gt;Проверка LasX на на VBScript&lt;/h3&gt;<br/>
&lt;p&gt;<br/>
<br/>
&lt;!-- объект Главной конфигурации --&gt;<br/>
&lt;object ID="Main" WIDTH="300" HEIGHT="600"<br/>
CLASSID="CLSID:CFF2C7CF-A317-11D4-B406-008048FB8A7C"&gt;<br/>
&lt;/object&gt;<br/>
<br/>
&lt;!-- объект карта --&gt;<br/>
&lt;object ID="Map" WIDTH="500" HEIGHT="600"<br/>
CLASSID="CLSID:CFF2C7D2-A317-11D4-B406-008048FB8A7C"&gt;<br/>
&lt;/object&gt;<br/>
<br/>
&lt;/p&gt;<br/>
&lt;/body&gt;&lt;/html&gt;<br/>
<p>

</body>
</html>