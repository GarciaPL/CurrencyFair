var euroData;

$('#euroChart').highcharts({
    chart: {
        type: 'line',
        events: {
            load: function () {
                euroData = this.series[0];
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
            text: 'Euro volume'
        }
    },
    legend: {
        enabled: true
    },
    tooltip: {
        valueDecimals: 2,
        valueSuffix: ' EUR'
    },
    plotOptions: {
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            name: 'Euro',
            data: [ ]
        }
    ]
});

var socket = new SockJS('/currencyfair-consumer/graphs');
var client = Stomp.over(socket);

client.connect('cf', 'cf', function (frame) {

    client.subscribe("/euro", function (message) {
        var request = JSON.parse(message.body);
        var point = [ request.date, parseInt(request.euro) ];
        euroData.addPoint(point, true, false);
    });

});