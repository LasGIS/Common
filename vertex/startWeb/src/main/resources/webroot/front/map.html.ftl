<h4><a name='Vector_map'>Векторная карта</a></h4>

<p>Векторная карта основана на понятии объекта как неделимого для пользователя
элемента, сведения о котором включают его геодезические координаты и атрибутивные
данные из семантической базы.</p>
<p>Объекты в свою очередь группируются в тематические слои. Под слоем понимается
часть графической базы, состоящая из однотипных объектов. Так, например, слоем
могут считаться здания, а объектами этого слоя будут: жилые здания, промышленные
здания, гаражи, ограждения и т.д. Система LasGIS может работать с 32-мя слоями
по 20-30 типов объектов в слое.</p>

<p>Слои графической информации определенного масштаба составляют карту определенного
уровня. Всего в системе 9 уровней карт (от масштаба 1:500 - 1 уровень до масштаба
1:200 миллионов - 9 уровень).</p>

<p>Принципиальным отличием системы LasGIS от однотипных систем является возможность
для пользователя настройки графического изображения и структуры графических слоев.</p>

<span style="horiz-align: center"><img alt="struct_vector.gif" src='../images/struct_vector.gif'/></span>

<p style="TEXT-ALIGN: center">Рис 1</p>

<h4><a name="Semantic_link">Семантическая База Данных</a></h4>

<p>Каждый графический объект в системе LasGIS связан с его описанием в
семантической базе. Вызов информации об объекте возможен по его изображению
на экране. И наоборот возможен поиск графического объекта (или группы объектов)
по семантической базе данных.</p>

<p>Структура семантической базы также может быть настроена или отредактирована
пользователем. Так, например, по зданиям может содержаться следующая
информация: адрес, площадь, число жителей, стоимость и т.д. Всего в системе
может быть настроено до 256 атрибутивных баз данных по объектам.</p>

<p>Кроме атрибутивной информации для каждого объекта карты можно хранить
 некоторый слайд или рисунок, в котором могут быть свои графические объекты,
 по которым в свою очередь можно задавать атрибутивную информацию и слайды.</p>

<h4><a name="Rastr_map">Растровая карта</a></h4>

<p>Растровая карта представляет собой набор внутрисистемных растровых файлов
 <.PCM> или стандартных растровых файлов <.BMP> или <.PCX> строго привязанных
 в системе LasGIS к геодезическим координатам, которые первыми выводятся на
 экран или принтер. Таким образом, получается возможность создавать векторную
 карту по растровому изображению. Технология создания растровой карты состоит
 из трех этапов:</p>
<ol style="list-style-type: arabic-numbers" >
  <li>Получение растрового файла сканированием исходного картографического
      материала или другим способом. Осуществляется специальными сторонними
      программами;</li>
  <li>Чистка полученного растра для получения приемлемого качества изображения.
      Осуществляется программой MakeRastr, входящей в систему LasGIS (в некоторых
      случаях возможно применение сторонних растровых редакторов);</li>
  <li>Привязка изображения по географическим точкам к растровой карте.
      Осуществляется программой MakeRastr.</li>
</ol>
<p>Растровая карта как и векторная разделена по масштабным уровням.
 Предусмотрена возможность выводить плоскостные объекты в полупрозрачном
 режиме. В этом случае на экране и принтере одновременно видно как растровую
 карту, так и объекты карты.</p>
<p>Размер растровой карты практически неограничен и определяется лишь размером
 носителя. Так, например, карта масштаба 1:10000 для города Омска находится во
 втором уровне и состоит из 1108 файлов <.PCM> с общим размером 218 Мбайт.
 Карта масштаба 1:500 для города Омска состоит из 8800 файлов <.PCM> с общим
 размером 40 Гбайт или 8 Гбайт для файлов <.PCX>. В данный момент в
 Главомскархитектуре производится постоянный мониторинг планшетов масштаба
 1:500. То есть, при изменении планшета, планшет сканируется и обновляется
 в растровой карте.</p>

<h4><a name="Format_exchange">Обмен данными</a></h4>

<p>Имеется открытый внешний ФОРМАТ ОБМЕНА ДАННЫХ, позволяющий обмениваться
   графическими данными с другими системами.</p>

<h4><a name="Programme_Language">Язык программирования</a></h4>

<p>Для обеспечения возможности решения прикладных задач управления городским
  хозяйством на основе кадастровой информации (задачи моделирования и
  прогнозирования), в систему LasGIS встроен ЯЗЫК ПРОГРАММИРОВАНИЯ.</p>
<p>СИНТАКСИС языка подобен синтаксису языков FOXPRO или CLIPER.</p>
<p>ФУНКЦИИ языка позволяют:</p>
<ol style="list-style-type: arabic-numbers" >
  <li>Работать с семантической базой данных;</li>
  <li>Работать с графическими объектами карты;</li>
  <li>Работать с текстовыми файлами (читать/писать строки файла);</li>
  <li>Работать с обменными файлами (читать/писать объекты карты);</li>
  <li>Позволяют выводить отчеты, содержащие как текстовую, так и графическую
      информацию;</li>
  <li>Создавать пользовательские меню, открывать окна, обрабатывать события и т.д.</li>
</ol>

<p>Практически на основе языка в системе LasGIS можно создавать автоматизированные
 СИСТЕМЫ ВЕДЕНИЯ ОТРАСЛЕВЫХ КАДАСТРОВ на основе банка кадастровой информации.</p>