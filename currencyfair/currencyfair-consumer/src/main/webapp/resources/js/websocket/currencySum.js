var currencySumData;

var chart = new Highcharts.Chart({
    chart: {
        type: 'line',
        events: {
            load: function () {
                currencySumData = this.series[0];
            }
        },
        renderTo: 'currencySumChart'
    },
    title: {
        text: false
    },
    xAxis: {
        type: 'datetime'
    },
    exporting: {
        enabled: true
    },
    yAxis: {
        title: {
            text: "Transaction value"
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
    }
});

var socket = new SockJS('/currencyfair-consumer/graphs');
var client = Stomp.over(socket);

client.connect('cf', 'cf', function (frame) {

    client.subscribe("/currency_sum", function (message) {
        var request = JSON.parse(message.body);

        checkSeries(request.currencySum);

        var seriesSet = chart.series;
        $(seriesSet).each(function (i, serie) {
            var serieId = serie.options.id;
            var serieName = serie.options.name;
            var serieValue = request.currencySum[serieName];

            if (typeof  serieValue != 'undefined') {
                var point = [ request.date, parseFloat(serieValue) ];
                chart.series[serieId].addPoint(point, true, false);
            }

        });

    });

});

function checkSeries(requestMap) {
    var currentSeries = chart.series;
    for (var key in requestMap) {
        var result = checkSerieExist(currentSeries, key);
        if (result == false) {
            chart.addSeries({
                id: currentSeries.length,
                name: key,
                data: []

            }, false);
            chart.redraw();
        }
    }
}

function checkSerieExist(currentSeries, key) {
    var exists = false;
    $(currentSeries).each(function (i, serie) {
        var serieName = serie.options.name;
        if (key == serieName) {
            exists = true;
            return false;
        } else {
            exists = false;
        }
    });

    return exists;
}