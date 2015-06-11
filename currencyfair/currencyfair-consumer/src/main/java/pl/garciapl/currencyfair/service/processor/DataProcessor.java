package pl.garciapl.currencyfair.service.processor;

import org.joda.money.CurrencyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import pl.garciapl.currencyfair.service.broadcaster.GraphBroker;
import pl.garciapl.currencyfair.service.domain.ecb.Cube;
import pl.garciapl.currencyfair.service.domain.queue.Payload;
import pl.garciapl.currencyfair.service.ecb.ECBRates;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.logging.Logger;

/**
 * Created by lukasz on 05.06.15.
 */
public class DataProcessor {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    @Qualifier("graphBroker")
    private GraphBroker graphBroker;

    @Value("#{propSource.currencyRates}")
    private String currencyRatesUrl;

    @Autowired
    @Qualifier("ecbRates")
    private ECBRates ecbRates;

    public void processMessage(Payload payload) {

        graphBroker.volumeGraphData(1);

        graphBroker.countriesSumGraphData(payload.getOriginatingCountry(), payload.getAmountSell());

        graphBroker.currencySumGraphData(payload.getCurrencyFrom(), payload.getAmountSell());

        if (CurrencyUnit.of(payload.getCurrencyFrom()).compareTo(CurrencyUnit.EUR) == 0) {
            graphBroker.euroGraphData(payload.getAmountSell());
        } else {
            Cube cube = retrieveCurrency(payload.getCurrencyFrom());
            if (cube != null) {
                BigDecimal divide = payload.getAmountSell().divide(calculateRate(cube.getRate()), getMathContext(cube.getRate()));
                graphBroker.euroGraphData(divide);
            } else {
                logger.info("Unsupported currency (" + payload.getCurrencyFrom() + ") to convert to EUR using Reference rates from European Central Bank");
            }
        }

        if (checkCurrenciesPairs(payload.getCurrencyFrom(), payload.getCurrencyTo())) {
            graphBroker.currenciesPairsGraphData(CurrencyUnit.EUR.getCurrencyCode() + ((CurrencyUnit.of(payload.getCurrencyTo()).compareTo(CurrencyUnit.EUR) == 0) ?
                    CurrencyUnit.of(payload.getCurrencyFrom()).getCurrencyCode() : CurrencyUnit.of(payload.getCurrencyTo()).getCurrencyCode()), calculateRate(payload.getRate()));
        }
    }

    private boolean checkCurrenciesPairs(String currencyFrom, String currencyTo) {
        if ((CurrencyUnit.of(currencyFrom).compareTo(CurrencyUnit.EUR) == 0 && CurrencyUnit.of(currencyTo).compareTo(CurrencyUnit.USD) == 0) ||
                (CurrencyUnit.of(currencyFrom).compareTo(CurrencyUnit.USD) == 0 && CurrencyUnit.of(currencyTo).compareTo(CurrencyUnit.EUR) == 0)) {
            return true;
        } else if ((CurrencyUnit.of(currencyFrom).compareTo(CurrencyUnit.EUR) == 0 && CurrencyUnit.of(currencyTo).compareTo(CurrencyUnit.GBP) == 0) ||
                (CurrencyUnit.of(currencyFrom).compareTo(CurrencyUnit.GBP) == 0 && CurrencyUnit.of(currencyTo).compareTo(CurrencyUnit.EUR) == 0)) {
            return true;
        } else if ((CurrencyUnit.of(currencyFrom).compareTo(CurrencyUnit.EUR) == 0 && CurrencyUnit.of(currencyTo).compareTo(CurrencyUnit.CHF) == 0) ||
                (CurrencyUnit.of(currencyFrom).compareTo(CurrencyUnit.CHF) == 0 && CurrencyUnit.of(currencyTo).compareTo(CurrencyUnit.EUR) == 0)) {
            return true;
        } else {
            return false;
        }
    }

    private Cube retrieveCurrency(String currency) {
        if (ecbRates.getCubes() != null) {
            for (Cube cube : ecbRates.getCubes()) {
                if (cube.getCurrency().equals(currency)) {
                    return cube;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private MathContext getMathContext(Double rate) {
        return new MathContext(countFraction(rate), RoundingMode.HALF_UP);
    }

    private BigDecimal calculateRate(Double rate) {
        BigDecimal decimal = new BigDecimal(rate, MathContext.DECIMAL64);
        return decimal.setScale(countFraction(rate), RoundingMode.CEILING);
    }

    private int countFraction(double input) {
        return new BigDecimal(Double.toString(input)).stripTrailingZeros().scale();
    }

}
