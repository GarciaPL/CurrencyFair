package pl.garciapl.currencyfair.service.domain.ecb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by lukasz on 10.06.15.
 */
@XmlRootElement(name = "Envelope", namespace = ECBNamespace.GESMES)
public class Envelope {

    @XmlElement(name = "Cube", namespace = ECBNamespace.ECB)
    private Cube cube;

    public Cube getCube() {
        return cube;
    }

}

