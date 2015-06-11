package pl.garciapl.currencyfair.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukasz on 05.06.15.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(
                apiInfo()).includePatterns("/endpoint/*");
    }

    @Bean
    public Map<Class, String> defaultParameterDataTypes() {
        Map<Class, String> dataTypeMappings = new HashMap<>();
        dataTypeMappings.put(char.class, "string");
        dataTypeMappings.put(String.class, "string");
        dataTypeMappings.put(Integer.class, "int32");
        dataTypeMappings.put(int.class, "int32");
        dataTypeMappings.put(Long.class, "int64");
        dataTypeMappings.put(BigInteger.class, "int64");
        dataTypeMappings.put(long.class, "int64");
        dataTypeMappings.put(Float.class, "float");
        dataTypeMappings.put(float.class, "float");
        dataTypeMappings.put(Double.class, "double");
        dataTypeMappings.put(double.class, "double");
        dataTypeMappings.put(BigDecimal.class, "double");
        dataTypeMappings.put(Byte.class, "byte");
        dataTypeMappings.put(byte.class, "byte");
        dataTypeMappings.put(Boolean.class, "boolean");
        dataTypeMappings.put(boolean.class, "boolean");
        dataTypeMappings.put(Date.class, "date-time");
        return dataTypeMappings;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "CurrencyFair API",
                "",
                "",
                "lukaszciesluk@gmail.com",
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }
}