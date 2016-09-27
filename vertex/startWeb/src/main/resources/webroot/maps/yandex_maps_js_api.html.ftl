  <h5>JavaScript API</h5>
  <div id="map" style="width: 1000px; height: 700px"></div>
  <script type="text/javascript">

    function init(){
      ymaps.modules.require(['Map', 'Placemark', 'Layer']).spread(
        function (Map, Placemark, Layer) {
          var myMap = new Map("map", {
            center: [55.22, 73.22],
            zoom: 9,
            controls: ['zoomControl', 'searchControl', 'typeSelector', 'rulerControl']
          });
          myMap.geoObjects.add(
              new Placemark(myMap.getCenter())
          );

          // создаём слой open street map
          var myLayer = function() {
            return new ymaps.Layer(
              'http://tile.openstreetmap.org/%z/%x/%y.png', {
                projection: ymaps.projection.sphericalMercator
              }
            );
          };
          // Добавляем его в хранилище слоёв
          ymaps.layer.storage.add('osm#layer', myLayer);
          // Создаём свой тип карты, состоящий из одного слоя
          var myType = new ymaps.MapType('Street Maps', ['osm#layer']);
          // Добавляем его в хранилище типов карты
          ymaps.mapType.storage.add('osm#mapType', myType);
          // Теперь можем устанавливать свой тип карте
          //myMap.setType('osm#mapType');

          var typeSelector = myMap.controls.get('typeSelector');
          typeSelector.addMapType('osm#mapType', 6);

        },
        function (error) {
            // Обработка ошибки.
        },
        this
      );
    }
  </script>
  <script type="text/javascript" src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&mode=debug&onload=init"></script>
