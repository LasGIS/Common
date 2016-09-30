<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>GC MAP API</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="/css/gismodule/0.7.7/leaflet.css">
    <style type="text/css">
        html, body { height: 100%; width: 100%; margin: 0; padding: 0; }
    </style>
    <script language="javascript" type="text/javascript" src="/js/gismodule/0.7.7/leaflet-src.js"></script>
    <script type="text/javascript">
        function noError(){return true;}
        window.onerror = noError;
    </script>
</head>
<body>
    <div id="map" style="height:100%; width: 100%; position: absolute; border: 0; left: 0; top: 0; margin:0; padding: 0;"></div>
<script type="text/javascript">
    window.external = {Y:54.958, X:73.394, Zoom:16, ShowMarker:true};

    function tilePath(tile) {
        return (tile.z + '00' + (1000000000 + tile.x).toString().substr(1)
           + (1000000000 + tile.y).toString().substr(1)).
           replace(/^(\d{1,2})00(\d{3})(\d{3})(\d{3})(\d{3})(\d{3})(\d{3})/, '$1/00/$2/$3/$4/$5/$6/$7');
    }

    window.onload = function() {

        var zoom = window.external.Zoom;
        var centerLatitude = window.external.Y;
        var centerLongitude = window.external.X;

        var map = L.map('map').setView([centerLatitude, centerLongitude], zoom);

        L.tileLayer('http://era-region.glonassunion.ru/tiles/g-map/lv{path}.png', {
            path: tilePath,
            minZoom: 1,
            maxZoom: 17,
            attribution: '&copy; <a href="http://cdcom.ru" target="_blank">CDCOM</a>',
            tms: true
        }).addTo(map);

        if (window.external.ShowMarker) {
            var signIcon = L.icon({
                iconUrl: '/images/marker.png',
                iconSize: [32, 41],
                iconAnchor: [16, 25],
                popupAnchor: [0, -30]
            });

            L.marker([centerLatitude, centerLongitude], {icon: signIcon}).addTo(map);
        }
    }
</script>
</body></html>