var currenciesPairsEURUSDData;
var currenciesPairsEURGBPData;
var currenciesPairsEURCHFData;

$('#eurusdpair').highcharts({
    chart: {
        zoomType: 'x',
        type: 'line',
        events: {
            load: function () {
                currenciesPairsEURUSDData = this.series[0];
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
            text: 'Rate'
        }
    },
    legend: {
        enabled: true
    },
    exporting: {
        enabled: true
    },
    plotOptions: {
        area: {
            fillColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                stops: [
                    [0, Highcharts.getOptions().colors[0]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            },
            marker: {
                radius: 2
            },
            lineWidth: 1,
            states: {
                hover: {
                    lineWidth: 1
                }
            },
            threshold: null
        },
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            type: 'area',
            name: 'EUR & USD Rate',
            data: [ ]
        }
    ]
});

$('#eurgbppair').highcharts({
    chart: {
        zoomType: 'x',
        type: 'line',
        events: {
            load: function () {
                currenciesPairsEURGBPData = this.series[0];
            }
        },
        renderTo: 'EURGBPPairChart'
    },
    title: {
        text: false
    },
    xAxis: {
        type: 'datetime'
    },
    yAxis: {
        title: {
            text: 'Rate'
        }
    },
    legend: {
        enabled: true
    },
    exporting: {
        enabled: true
    },
    plotOptions: {
        area: {
            fillColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                stops: [
                    [0, Highcharts.getOptions().colors[0]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            },
            marker: {
                radius: 2
            },
            lineWidth: 1,
            states: {
                hover: {
                    lineWidth: 1
                }
            },
            threshold: null
        },
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            type: 'area',
            name: 'EUR & GBP Rate',
            data: [ ]
        }
    ]
});

$('#eurchfpair').highcharts({
    chart: {
        zoomType: 'x',
        type: 'line',
        events: {
            load: function () {
                currenciesPairsEURCHFData = this.series[0];
            }
        },
        renderTo: 'EURCHFPairChart'
    },
    title: {
        text: false
    },
    xAxis: {
        type: 'datetime'
    },
    yAxis: {
        title: {
            text: 'Rate'
        }
    },
    exporting: {
        enabled: true
    },
    legend: {
        enabled: true
    },
    plotOptions: {
        area: {
            fillColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                stops: [
                    [0, Highcharts.getOptions().colors[0]],
                    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
            },
            marker: {
                radius: 2
            },
            lineWidth: 1,
            states: {
                hover: {
                    lineWidth: 1
                }
            },
            threshold: null
        },
        series: {
            threshold: 0,
            marker: {
                enabled: false
            }
        }
    },
    series: [
        {
            type: 'area',
            name: 'EUR & CHF Rate',
            data: [ ]
        }
    ]
});

var socket = new SockJS('/currencyfair-consumer/graphs');
var client = Stomp.over(socket);

client.connect('cf', 'cf', function (frame) {

    client.subscribe("/currencies_pairs", function (message) {

        var request = JSON.parse(message.body);

        if (typeof request.currenciesPairs.EURUSD != 'undefined') {
            var pointUSD = [ request.date, parseFloat(request.currenciesPairs.EURUSD) ];
            currenciesPairsEURUSDData.addPoint(pointUSD, true, false);
        }

        if (typeof request.currenciesPairs.EURGBP != 'undefined') {
            var pointGBP = [ request.date, parseFloat(request.currenciesPairs.EURGBP) ];
            currenciesPairsEURGBPData.addPoint(pointGBP, true, false);
        }

        if (typeof request.currenciesPairs.EURCHF != 'undefined') {
            var pointCHF = [ request.date, parseFloat(request.currenciesPairs.EURCHF) ];
            currenciesPairsEURCHFData.addPoint(pointCHF, true, false);
        }
    });

});