package pl.garciapl.currencyfair.controller.json;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by lukasz on 17.05.15.
 */
@ApiModel(value = "Request", description = "CurrencyFair Request")
public class Request {

    @ApiModelProperty(value = "userId", required = true, position = 0)
    private String userId;

    @ApiModelProperty(value = "currencyFrom", required = true, notes = "ISO 4217", position = 1)
    private String currencyFrom;

    @ApiModelProperty(value = "currencyTo", required = true, notes = "ISO 4217", position = 2)
    private String currencyTo;

    @ApiModelProperty(value = "amountSell", required = true, dataType = "java.math.BigDecimal", position = 3)
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal amountSell;

    @ApiModelProperty(value = "amountBuy", required = true, dataType = "java.math.BigDecimal", position = 4)
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal amountBuy;

    @ApiModelProperty(value = "rate", required = true, dataType = "java.lang.Double", position = 5)
    private Double rate;

    @ApiModelProperty(value = "timePlaced", required = false, notes = "DD-MMM-YY HH:MM:SS", position = 6)
    private String timePlaced;

    @ApiModelProperty(value = "originatingCountry", required = true, notes = "ISO 3166-1", position = 7)
    private String originatingCountry;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
        return round(amountSell);
    }

    public void setAmountSell(BigDecimal amountSell) {
        this.amountSell = round(amountSell);
    }

    public BigDecimal getAmountBuy() {
        return round(amountBuy);
    }

    public void setAmountBuy(BigDecimal amountBuy) {
        this.amountBuy = round(amountBuy);
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

    private BigDecimal round(BigDecimal decimal) {
        return (decimal != null) ? decimal.setScale(2, RoundingMode.CEILING) : null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "userId=" + userId +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", amountSell=" + ((amountSell != null) ? amountSell.setScale(2, RoundingMode.CEILING) : null) +
                ", amountBuy=" + ((amountBuy != null) ? amountBuy.setScale(2, RoundingMode.CEILING) : null) +
                ", rate=" + rate +
                ", timePlaced='" + timePlaced + '\'' +
                ", originatingCountry='" + originatingCountry + '\'' +
                '}';
    }
}
