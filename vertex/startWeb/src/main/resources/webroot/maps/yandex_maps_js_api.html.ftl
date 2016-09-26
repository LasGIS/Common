  <h5>JavaScript API</h5>
  <div id="map" style="width: 1000px; height: 800px"></div>
  <script type="text/javascript">
    ymaps.ready(init);
    var myMap;

    function init(){
       myMap = new ymaps.Map("map", {
           center: [55.22, 73.22],
           zoom: 8
       });
    }
  </script>
