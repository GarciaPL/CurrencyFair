package pl.garciapl.currencyfair.service.ecb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lukasz on 11.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:currencyfair-consumer-ctx.xml")
@PropertySource("classpath*:currencyfair.properties")
public class ECBParserTest {

    @Value("#{propSource.currencyRates}")
    private String currencyRatesUrl;

    private ECBRates ecbRates;

    @Before
    public void setup() {
        ecbRates = new ECBRates();
        ecbRates.setCurrencyRatesUrl(currencyRatesUrl);
    }

    @Test
    public void ecbRatesTest() {
        ecbRates.fetchECBRates();
    }
}
