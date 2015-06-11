package pl.garciapl.currencyfair.controller;

import com.google.gson.Gson;
import junit.framework.Assert;
import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import pl.garciapl.currencyfair.controller.json.Request;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Created by lukasz on 23.05.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath*:currencyfair-gateway-ctx.xml")
public class EndpointControllerTest {

    private Logger logger = Logger.getLogger(getClass().getName());

    private MockMvc mockMvc;
    private InternalResourceViewResolver viewResolver;

    @Autowired
    private Gson gson;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        EndpointController endpointController = new EndpointController();

        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(endpointController).setViewResolvers(viewResolver).build();

        ReflectionTestUtils.setField(endpointController, "gson", new Gson());
    }

    @Test
    public void endpointControllerTest() throws Exception {
        Request request = new Request();
        request.setUserId("12345");
        request.setCurrencyFrom(CurrencyUnit.EUR.getCurrencyCode());
        request.setCurrencyTo(CurrencyUnit.GBP.getCurrencyCode());
        request.setAmountSell(new BigDecimal(1000));
        request.setAmountBuy(new BigDecimal(727.00));
        request.setRate(0.727);
        request.setTimePlaced("24-MAY-15 12:45:14");
        request.setOriginatingCountry(Locale.FRANCE.getCountry());
        String json = gson.toJson(request);

        logger.info("Posting JSON : " + json);

        MvcResult endpoint = mockMvc.perform(MockMvcRequestBuilders.post("/endpoint").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertNotNull(endpoint);
    }
}
