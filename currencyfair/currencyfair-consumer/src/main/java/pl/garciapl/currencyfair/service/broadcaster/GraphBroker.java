package pl.garciapl.currencyfair.service.broadcaster;

import java.math.BigDecimal;

/**
 * Created by lukasz on 06.06.15.
 */
public interface GraphBroker {

    public void volumeGraphData(int counter);

    public void currencySumGraphData(String currency, BigDecimal value);

    public void countriesSumGraphData(String country, BigDecimal value);

    public void currenciesPairsGraphData(String pair, BigDecimal rate);

    public void euroGraphData(BigDecimal value);
}
