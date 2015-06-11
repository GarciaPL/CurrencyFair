package pl.garciapl.currencyfair.controller;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created with IntelliJ IDEA.
 * User: lciesluk
 * Date: 20.05.15
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = HomeController.class)
public class HomeControllerTest {

    private MockMvc mockMvc;
    private InternalResourceViewResolver viewResolver;

    @Before
    public void setup() {
        viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).setViewResolvers(viewResolver).build();
    }

    @Test
    public void homeControllerMainTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals("Hello CurrencyFair!", contentAsString);
    }

    @Test
    public void homeControllerHomeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("index")).andReturn();
    }

    @Test
    public void homeControllerIndexTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/index")).andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.view().name("index")).andReturn();
    }
}
