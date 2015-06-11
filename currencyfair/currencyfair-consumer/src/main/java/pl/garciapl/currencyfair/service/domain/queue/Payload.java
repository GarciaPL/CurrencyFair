package pl.garciapl.currencyfair.service.domain.queue;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by lukasz on 17.05.15.
 */
public class Payload {

    private Integer userId;

    private String currencyFrom;

    private String currencyTo;

    private BigDecimal amountSell;

    private BigDecimal amountBuy;

    private Double rate;

    private String timePlaced;

    private String originatingCountry;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public BigDecimal getAmountSell() {
        return setRound(amountSell);
    }

    public void setAmountSell(BigDecimal amountSell) {
        this.amountSell = setRound(amountSell);
    }

    public BigDecimal getAmountBuy() {
        return setRound(amountBuy);
    }

    public void setAmountBuy(BigDecimal amountBuy) {
        this.amountBuy = setRound(amountBuy);
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getTimePlaced() {
        return timePlaced;
    }

    public void setTimePlaced(String timePlaced) {
        this.timePlaced = timePlaced;
    }

    public String getOriginatingCountry() {
        return originatingCountry;
    }

    public void setOriginatingCountry(String originatingCountry) {
        this.originatingCountry = originatingCountry;
    }

    public BigDecimal setRound(BigDecimal decimal) {
        return decimal.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public String toString() {
        return "Payload{" +
                "userId=" + userId +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", amountSell=" + amountSell +
                ", amountBuy=" + amountBuy +
                ", rate=" + rate +
                ", timePlaced='" + timePlaced + '\'' +
                ", originatingCountry='" + originatingCountry + '\'' +
                '}';
    }
}
