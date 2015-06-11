package pl.garciapl.currencyfair.service.ecb;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import pl.garciapl.currencyfair.service.domain.ecb.Cube;
import pl.garciapl.currencyfair.service.domain.ecb.Envelope;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by lukasz on 05.06.15.
 */
public class ECBRates {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Value("#{propSource.currencyRates}")
    private String currencyRatesUrl;

    private List<Cube> cubes;

    public void fetchECBRates() {
        try {
            String body = Unirest.get(currencyRatesUrl).asString().getBody();
            if (body == null) {
                logger.severe("Cannot fetch data from " + currencyRatesUrl);
            } else {
                try {
                    InputStream inputStream = IOUtils.toInputStream(body, "UTF-8");
                    final JAXBContext jaxbContext = JAXBContext.newInstance(Envelope.class);
                    final Envelope envelope = (Envelope) jaxbContext.createUnmarshaller().unmarshal(new StreamSource(inputStream));

                    if (envelope.getCube().getCubes().size() > 0) {
                        setCubes(envelope.getCube().getCubes().get(0).getCubes());
                    }
                } catch (Exception e) {
                    logger.severe(e.getMessage());
                }
            }
        } catch (UnirestException e) {
            logger.severe(e.getMessage());
        }
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public void setCubes(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public String getCurrencyRatesUrl() {
        return currencyRatesUrl;
    }

    public void setCurrencyRatesUrl(String currencyRatesUrl) {
        this.currencyRatesUrl = currencyRatesUrl;
    }
}
