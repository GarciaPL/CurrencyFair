package pl.garciapl.currencyfair.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by lukasz on 23.05.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = GraphController.class)
public class GraphControllerTest {

    private MockMvc mockMvc;
    private InternalResourceViewResolver viewResolver;

    @Before
    public void setup() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new GraphController()).setViewResolvers(viewResolver).build();
    }

    @Test
    public void volumeGraphTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/volumegraph")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("volume")).andReturn();
    }

    @Test
    public void sumCurrencyGraphTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currencysumgraph")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("currencysum")).andReturn();
    }

    @Test
    public void countriesSumGraphTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/countriessumgraph")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("countriessum")).andReturn();
    }

    @Test
    public void currenciesParisGraphTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currenciespairsgraph")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("currenciespairs")).andReturn();
    }

    @Test
    public void euroSumGraph() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/eurograph")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("euro")).andReturn();
    }
}
