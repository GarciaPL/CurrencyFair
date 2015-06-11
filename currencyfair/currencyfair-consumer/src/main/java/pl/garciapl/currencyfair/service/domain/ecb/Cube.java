package pl.garciapl.currencyfair.service.domain.ecb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by lukasz on 05.06.15.
 */
public class Cube {

    @XmlElement(name = "Cube", namespace = ECBNamespace.ECB)
    private List<Cube> cubes;

    @XmlAttribute(name = "currency")
    private String currency;

    @XmlAttribute(name = "rate")
    private Double rate;

    public List<Cube> getCubes() {
        return cubes;
    }

    public Double getRate() {
        return rate;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Cube{" + "currency=" + currency + ", rate=" + rate + '}';
    }

}
