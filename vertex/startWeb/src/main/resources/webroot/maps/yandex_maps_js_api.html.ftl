  <div id="map" style="height:100%; width: 100%; position: absolute; border:0; left:0; top:0; margin:0; padding: 0;z-index:0"></div>
  <script type="text/javascript">

    /**
     * создаём слой open street map
     * @param typeSelector
     */
    function createOpenStreetMapLayer(typeSelector) {
      var myLayer = function () {
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

      typeSelector.addMapType('osm#mapType', 6);
    }

    function tilePath(tileNumber, tileZoom) {
        return (tileZoom + '00' + (1000000000 + tileNumber[0]).toString().substr(1)
           + (1000000000 + tileNumber[1]).toString().substr(1)).
           replace(/^(\d{1,2})00(\d{3})(\d{3})(\d{3})(\d{3})(\d{3})(\d{3})/, '$1/00/$2/$3/$4/$5/$6/$7');
    }

    /**
     * создаём слой cd com
     * @param typeSelector
     */
    function createCdComMapLayer(typeSelector) {
      var myLayer = function () {
        var project = Object.create(ymaps.projection.sphericalMercator);
        var mercator = Object.create(project._mercator);
        var latitudeToY = mercator.latitudeToY;
        var yToLatitude = mercator.yToLatitude;
        mercator.latitudeToY = function (lat) {
          return latitudeToY(lat)
        };
        mercator.yToLatitude = function (lat) {
          return yToLatitude(lat)
        };
        project._mercator = mercator;
        return new ymaps.Layer(
            function (tileNumber, tileZoom) {
              return 'http://era-region.glonassunion.ru/tiles/g-map/lv' + tilePath(tileNumber, tileZoom) + '.png';
            }, {
              projection: project
            }
        );
      };
      // Добавляем его в хранилище слоёв
      ymaps.layer.storage.add('cdcom#layer', myLayer);
      // Создаём свой тип карты, состоящий из одного слоя
      var myType = new ymaps.MapType('cd com Maps', ['cdcom#layer']);
      // Добавляем его в хранилище типов карты
      ymaps.mapType.storage.add('cdcom#mapType', myType);
      // Теперь можем устанавливать свой тип карте
      //myMap.setType('osm#mapType');

      typeSelector.addMapType('cdcom#mapType', 7);
    }

    function init() {
      ymaps.modules.require(['Map', 'Placemark', 'Layer']).spread(
        function (Map, Placemark, Layer) {
          var myMap = new Map("map", {
            center: [${context.window.external.Y}, ${context.window.external.X}],
            zoom: ${context.window.external.Zoom},
            controls: ['zoomControl', 'searchControl', 'typeSelector', 'rulerControl']
          });
          myMap.geoObjects.add(
              new Placemark(myMap.getCenter(), {
                hintContent: 'Место происшествия',
//                  balloonContent: 'Это красивая метка'
                balloonContentHeader: 'содержимое заголовка балуна геообъекта;',
                balloonContentBody: 'содержимое основой части балуна геообъекта;',
                balloonContentFooter: 'содержимое нижней части балуна геообъекта.'
              }, {
                  // Опции.
                  // Необходимо указать данный тип макета.
                  iconLayout: 'default#image',
                  // Своё изображение иконки метки.
                  iconImageHref: '/images/marker.png',
                  // Размеры метки.
                  iconImageSize: [32, 41],
                  // Смещение левого верхнего угла иконки относительно
                  // её "ножки" (точки привязки).
                  iconImageOffset: [-16, -30],
                  balloonOffset: [0, -35],
                  hideIconOnBalloonOpen: false
              })
          );
          var typeSelector = myMap.controls.get('typeSelector');
          createOpenStreetMapLayer(typeSelector);
          createCdComMapLayer(typeSelector);
        },
        function (error) {
            // Обработка ошибки.
        },
        this
      );
    }
  </script>
  <script type="text/javascript" src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&mode=debug&onload=init"></script>
