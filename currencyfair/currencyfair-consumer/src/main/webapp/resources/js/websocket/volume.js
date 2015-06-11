var volumeData;

$('#volumeChart').highcharts({
    chart: {
        type: 'line',
        events: {
            load: function () {
                volumeData = this.series[0];
            }
        }
    },
    title: {
        text: false
    },
    xAxis: {
        type: 'datetime'
    },
    yAxis: {
        title: {
            text: false
        }
    },
    legend: {
        enabled: true
    },
    plotOptions: {
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    exporting: {
        enabled: true
    },
    series: [
        {
            name: 'Volume',
            data: [ ]
        }
    ]
});

var socket = new SockJS('/currencyfair-consumer/graphs');
var client = Stomp.over(socket);

client.connect('cf', 'cf', function (frame) {

    client.subscribe("/volume", function (message) {
        var request = JSON.parse(message.body);
        var point = [ request.date, parseInt(request.volume) ];
        volumeData.addPoint(point, true, false);
    });

});