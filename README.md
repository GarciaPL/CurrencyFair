## CurrencyFair

The projects is splited into two modules : **currencyfair-gateway** and **currencyfair-consumer**. 

The first module **currencyfair-gateway** is responsible for exposing endpoint used for retriving from user request in JSON format. Request is validated due to it's fields. If request is not valid user will receive detailed information about which fields are incorrect. Otherwise request will be forwarded to message broker RabbitMQ.

The second module **currencyfair-consumer** fetches messages from message broker RabbitMQ to process it via Data Processor. Once it's done the processed data are broadcasted to frontend.

## Technology

##### Environment

- Technology : Java 1.7.0_65
- Application server : JBoss Application Server 7.1.1
- Framework : Spring Framework 4.0.1.RELEASE

##### Message broker

- RabbitMQ 

##### Libraries

- Spring Beans – 4.0.1.RELEASE
- Spring Tx – 4.0.1.RELEASE
- Spring Context – 4.0.1.RELEASE
- Spring Context Support – 4.0.1.RELEASE
- Spring Aop – 4.0.1.RELEASE
- Spring Aspects - 4.0.1.RELEASE
- Spring Web – 4.0.1.RELEASE
- Spring Web MVC – 4.0.1.RELEASE
- Spring AMQP - 1.4.5.RELEASE
- Spring Erlang - 1.4.5.RELEASE
- Spring Rabbit - 1.4.5.RELEASE
- Spring WebSocket - 4.0.1.RELEASE
- Spring Messaging - 4.0.1.RELEASE
- Spring Integration - 4.1.0.RELEASE
- Spring Integration AMQP - 4.1.0.RELEASE
- Spring Integration Stream - 4.1.0.RELEASE
- Spring Test - 4.0.1.RELEASE
- Jackson Core - 2.5.0
- Jackson Databind - 2.5.0
- Jackson Annotations - 2.5.0
- Jackson Mapper ASL – 1.9.10
- Gson – 2.2.4
- Guava - 18.0
- Unirest - 1.4.5
- Javax Servlet API – 3.1.0
- JSTL – 1.2
- SLF4J - 1.7.8
- Commons Logging - 1.2
- JUnit - 4.10
- Mockito - 1.9.5
- Hamcrest - 1.3
- Highcharts - 4.1.5
- Twitter Bootstrap - 3.3.4
- SockJS - 0.3.4

##### External resources
- ECB Currency Rates - http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml

## Architecture

![Architecture](https://github.com/GarciaPL/CurrencyFair/blob/master/images/CF.png "Architecture")

## Modules
#### Endpoint

Endpoint consumes JSON data on context /currencyfair-gateway/endpoint like below :

```javascript
{ 
    "userId": "12345",
    "currencyFrom": "EUR",
    "currencyTo": "GBP",
    "amountSell": 1000,
    "amountBuy": 747.10,
    "rate": 0.7471,
    "timePlaced" : "24-JAN-15 10:27:44",
    "originatingCountry" : "FR"
}
```
Message is validated under empty of above fields, timePlaced format (dd-MMM-yy hh:mm:ss), existing of currencyFrom and currencyTo in ISO 4217, existing of originatinCountry in ISO 3166-1 and by calculations of amountSell, amountBuy and rate. 

#### Data processor

Module is reponsible for calculating all data required by frontend to display it on charts.

#### GraphBroker

Module sends data supplied by Data processor to specific Stomp endpoints. 

#### Frontend

Each graph subscribes data via Stomp over WebSocket from specific context. At this moment there are following graphs :

1. Volume Graph - Presents summarize quantity of processed financial data
    
![Volume Graph](https://github.com/GarciaPL/CurrencyFair/blob/master/images/VolumeGraph.png "VolumeGraph")
2. Currency Graph - Presents summarize volume of processed financial data for appropriate currencies

![Currency Graph](https://github.com/GarciaPL/CurrencyFair/blob/master/images/CurrencyGraph.png "CurrencyGraph")
3. Countries Graph - Presents summarize volume of processed financial data for appropriate countries

![Countries Graph](https://github.com/GarciaPL/CurrencyFair/blob/master/images/CountriesGraph.png "CountriesGraph")
4. Euro Graph - Presents summarize volume of processed financial data converted to EUR based currency using Reference rates from European Central Bank

![Euro Graph](https://github.com/GarciaPL/CurrencyFair/blob/master/images/EuroGraph.png "EuroGraph")
5. Currencies Pairs Graph - Presents rate of processed financial data for appropriate pairs - EUR & USD, EUR & GBP and EUR & CHF

![EURUSD Pair](https://github.com/GarciaPL/CurrencyFair/blob/master/images/EURUSDPair.png "EURUSDPair")

![EURGBP Pair](https://github.com/GarciaPL/CurrencyFair/blob/master/images/EURGBPPair.png "EURGBPPair")

![EURCHF Pair](https://github.com/GarciaPL/CurrencyFair/blob/master/images/EURCHFPair.png "EURCHFPair")

## Installation

##### Currencyfair-Gateway

1. Modify properties located in currencyfair.properties (optional)
2. Build WAR and deploy to JBoss AS
3. Go to http://localhost:8080/currencyfair-gateway/

##### Currencyfair-Consumer

1. Modify properties located in currencyfair.properties (optional)
2. Build WAR and deploy to JBoss AS
3. Go to http://localhost:8080/currencyfair-consumer/

## Properties

Each module have file called **currencyfair.properties** under path /src/main/resources where you can adjust parameters of applications work.

1. Currencyfair-gateway
    * rabbitMQUsername=guest
    * rabbitMQPassword=guest
    * rabbitMQHost=localhost
    * rabbitMQPort=5672
    * rabbitMQQueueName=currencyfairQueue
    * rabbitMQExchange=currencyfairExchange
    * rabbitMQRoutingKey=currencyfair.finance
    * rabbitMQChannelCache=25
    * rabbitMQAutoDelete=false
    * rabbitMQDurable=true
    * currencyfairUser=cf
    * currencyfairPassword=cf

2. Currencyfair-consumer 
    * rabbitMQUsername=guest
    * rabbitMQPassword=guest
    * rabbitMQHost=localhost
    * rabbitMQPort=5672
    * rabbitMQQueueName=currencyfairQueue
    * rabbitMQExchange=currencyfairExchange
    * rabbitMQConcurrentConsumers=10
    * rabbitMQChannelCache=25
    * rabbitMQAutoDelete=false
    * rabbitMQDurable=true
    * stomp=graphs
    * volumeGraphs=volume
    * currencySumGraphs=currency_sum
    * countriesSumGraphs=countries_sum
    * currenciesPairsGraphs=currencies_pairs
    * euroGraphs=euro
    * currencyRates=http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml

## Load testing

Script used to run load tests in Apache JMeter can be found in repository under directory /misc/jmeter.

## Postman

Configuration of Postman which can help to make initial request to Endpoint can be found in repository under directory /misc/postman.

## License

Code released under the MIT License (MIT). Docs released under Creative Commons.

## References
- [Spring Integration](http://projects.spring.io/spring-integration/)
- [RabbitMQ](https://www.rabbitmq.com)
- [Postman](https://www.getpostman.com)
- [Apache JMeter](http://jmeter.apache.org)
- [Twitter Bootstrap](http://getbootstrap.com)
- [Highcharts](http://www.highcharts.com)