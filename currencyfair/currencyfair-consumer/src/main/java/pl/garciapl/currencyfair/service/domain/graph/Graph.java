package pl.garciapl.currencyfair.service.domain.graph;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by lukasz on 29.05.15.
 */
public class Graph implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer volume;
    private HashMap currenciesPairs;
    private HashMap currencySum;
    private HashMap countriesSum;
    private BigDecimal euro;
    private Date date = new Date();

    public Graph() {
    }

    public HashMap getCountriesSum() {
        return countriesSum;
    }

    public void setCountriesSum(HashMap countriesSum) {
        this.countriesSum = countriesSum;
    }

    public HashMap getCurrencySum() {
        return currencySum;
    }

    public void setCurrencySum(HashMap currencySum) {
        this.currencySum = currencySum;
    }

    public BigDecimal getEuro() {
        return euro;
    }

    public void setEuro(BigDecimal euro) {
        this.euro = euro;
    }

    public HashMap getCurrenciesPairs() {
        return currenciesPairs;
    }

    public void setCurrenciesPairs(HashMap currenciesPairs) {
        this.currenciesPairs = currenciesPairs;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "volume=" + volume +
                ", currenciesPairs=" + currenciesPairs +
                ", currencySum=" + currencySum +
                ", countriesSum=" + countriesSum +
                ", euro=" + euro +
                ", date=" + date +
                '}';
    }
}
