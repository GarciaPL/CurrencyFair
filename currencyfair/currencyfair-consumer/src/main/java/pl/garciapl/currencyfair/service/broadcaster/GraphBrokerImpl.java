package pl.garciapl.currencyfair.service.broadcaster;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.garciapl.currencyfair.service.domain.graph.Graph;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Created by lukasz on 29.05.15.
 */
@EnableScheduling
public class GraphBrokerImpl implements GraphBroker {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private Gson gson;

    @Value("#{propSource.volumeGraphs}")
    private String volumeGraph;

    @Value("#{propSource.currencySumGraphs}")
    private String currencySumGraph;

    @Value("#{propSource.countriesSumGraphs}")
    private String countriesSumGraph;

    @Value("#{propSource.currenciesPairsGraphs}")
    private String currenciesPairsGraph;

    @Value("#{propSource.euroGraphs}")
    private String euroGraph;

    private int volumeGraphCounter;

    private BigDecimal euroQuantity;

    private HashMap<String, BigDecimal> countriesSum;

    private HashMap<String, BigDecimal> currencySum;

    private HashMap<String, BigDecimal> currenciesPairs;

    private GraphBrokerImpl() {
        volumeGraphCounter = 0;
        euroQuantity = BigDecimal.ZERO;
        countriesSum = new HashMap<>();
        currencySum = new HashMap<>();
        currenciesPairs = new HashMap<>();
    }

    @Scheduled(fixedDelay = 10000)
    public void volumeGraphSender() {

        Graph graph = new Graph();
        graph.setVolume(getVolumeGraphCounter());
        String json = gson.toJson(graph);

        MessageBuilder<byte[]> messageBuilder = MessageBuilder.withPayload(json.getBytes());
        simpMessagingTemplate.send("/" + volumeGraph, messageBuilder.build());
        setVolumeGraphCounter(0);
    }

    public void volumeGraphData(int counter) {
        setVolumeGraphCounter(getVolumeGraphCounter() + counter);
    }

    @Scheduled(fixedDelay = 2000)
    public void currencySumSender() {

        Graph graph = new Graph();
        graph.setCurrencySum(getCurrencySum());
        String json = gson.toJson(graph);

        MessageBuilder<byte[]> messageBuilder = MessageBuilder.withPayload(json.getBytes());
        simpMessagingTemplate.send("/" + currencySumGraph, messageBuilder.build());
        getCurrencySum().clear();
    }

    public void currencySumGraphData(String currency, BigDecimal value) {
        if (getCurrencySum().containsKey(currency)) {
            BigDecimal current = getCurrencySum().get(currency);
            current = current.add(value);
            getCurrencySum().put(currency, current);
        } else {
            getCurrencySum().put(currency, value);
        }
    }

    @Scheduled(fixedDelay = 2000)
    public void countriesSumSender() {

        Graph graph = new Graph();
        graph.setCountriesSum(getCountriesSum());
        String json = gson.toJson(graph);

        MessageBuilder<byte[]> messageBuilder = MessageBuilder.withPayload(json.getBytes());
        simpMessagingTemplate.send("/" + countriesSumGraph, messageBuilder.build());
        getCountriesSum().clear();
    }

    public void countriesSumGraphData(String country, BigDecimal value) {
        if (getCountriesSum().containsKey(country)) {
            BigDecimal current = getCountriesSum().get(country);
            current = current.add(value);
            getCountriesSum().put(country, current);
        } else {
            getCountriesSum().put(country, value);
        }
    }

    @Scheduled(fixedDelay = 2000)
    public void currenciesPairsSender() {

        Graph graph = new Graph();
        graph.setCurrenciesPairs(getCurrenciesPairs());
        String json = gson.toJson(graph);

        MessageBuilder<byte[]> messageBuilder = MessageBuilder.withPayload(json.getBytes());
        simpMessagingTemplate.send("/" + currenciesPairsGraph, messageBuilder.build());
        getCurrenciesPairs().clear();
    }

    public void currenciesPairsGraphData(String pair, BigDecimal rate) {
        if (getCurrenciesPairs().containsKey(pair)) {
            getCurrenciesPairs().put(pair, rate);
        } else {
            getCurrenciesPairs().put(pair, rate);
        }
    }

    @Scheduled(fixedDelay = 2000)
    public void euroGraphSender() {

        Graph graph = new Graph();
        graph.setEuro(getEuroQuantity());
        String json = gson.toJson(graph);

        MessageBuilder<byte[]> messageBuilder = MessageBuilder.withPayload(json.getBytes());
        simpMessagingTemplate.send("/" + euroGraph, messageBuilder.build());
        setEuroQuantity(BigDecimal.ZERO);
    }

    public void euroGraphData(BigDecimal value) {
        setEuroQuantity(getEuroQuantity().add(value));
    }

    public int getVolumeGraphCounter() {
        return volumeGraphCounter;
    }

    public void setVolumeGraphCounter(int volumeGraphCounter) {
        this.volumeGraphCounter = volumeGraphCounter;
    }

    public BigDecimal getEuroQuantity() {
        return euroQuantity;
    }

    public void setEuroQuantity(BigDecimal euroQuantity) {
        this.euroQuantity = euroQuantity;
    }

    public HashMap<String, BigDecimal> getCountriesSum() {
        return countriesSum;
    }

    public void setCountriesSum(HashMap<String, BigDecimal> countriesSum) {
        this.countriesSum = countriesSum;
    }

    public HashMap<String, BigDecimal> getCurrencySum() {
        return currencySum;
    }

    public void setCurrencySum(HashMap<String, BigDecimal> currencySum) {
        this.currencySum = currencySum;
    }

    public HashMap<String, BigDecimal> getCurrenciesPairs() {
        return currenciesPairs;
    }

    public void setCurrenciesPairs(HashMap<String, BigDecimal> currenciesPairs) {
        this.currenciesPairs = currenciesPairs;
    }
}
