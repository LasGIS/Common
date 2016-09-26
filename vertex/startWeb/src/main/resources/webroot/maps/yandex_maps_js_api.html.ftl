  <h5>JavaScript API</h5>
  <div id="map" style="width: 1000px; height: 700px"></div>
  <script type="text/javascript">
    //ymaps.ready(init);
//    var myMap;

    function init(){
      ymaps.modules.require(['Map', 'Placemark', 'Layer']).spread(
          function (Map, Placemark, Layer) {
              var myMap = new Map("map", {
                  center: [55.22, 73.22],
                  zoom: 9
              });
              myMap.geoObjects.add(
                  new Placemark(myMap.getCenter())
              );
            // Добавим на карту слой OSM
            myMap.layers.add(new Layer(
                'http://tile.openstreetmap.org/%z/%x/%y.png', {
                  projection: ymaps.projection.sphericalMercator,
                  tileTransparent: true
                }
            ));
            myMap.copyrights.add('&copy; OpenStreetMap contributors, CC-BY-SA');
          },
          function (error) {
              // Обработка ошибки.
          },
          this
      );
/*
       myMap = new ymaps.Map("map", {
           center: [55.22, 73.22],
           zoom: 8
       });
*/
    }
  </script>
  <script type="text/javascript" src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&onload=init"></script>
