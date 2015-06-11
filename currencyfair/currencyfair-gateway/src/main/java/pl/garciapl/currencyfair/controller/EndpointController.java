package pl.garciapl.currencyfair.controller;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.garciapl.currencyfair.controller.json.Request;
import pl.garciapl.currencyfair.controller.validator.RequestValidator;

import java.util.logging.Logger;

/**
 * Created by lukasz on 17.05.15.
 */
@RestController
@Controller
@Api(value = "Endpoint", description = "Endpoint")
public class EndpointController {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("#{propSource.rabbitMQRoutingKey}")
    private String routingKey;

    @Value("#{propSource.rabbitMQExchange}")
    private String exchange;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/endpoint", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ApiOperation(value = "endpoint", notes = "Send financial info", httpMethod = "POST",
            consumes = "application/json", produces = "application/json, text/plain")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful validation of financial data and sending it further to queue"),
            @ApiResponse(code = 400, message = "User retrieve error message"),
            @ApiResponse(code = 201, message = "-")}
    )
    @ResponseBody
    public ResponseEntity<?> endpoint(@RequestBody Request request, BindingResult result) {

        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, result);

        if (result.hasErrors()) {
            Multimap<String, String> entities = LinkedListMultimap.create();
            for (FieldError fieldError : result.getFieldErrors()) {
                entities.put(fieldError.getField(), fieldError.getCode());
            }
            return new ResponseEntity<Object>(gson.toJson(entities.asMap()), HttpStatus.BAD_REQUEST);
        }

        try {
            MessageProperties props = MessagePropertiesBuilder.newInstance()
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON).build();

            String body = gson.toJson(request);
            Message messagePayload = MessageBuilder
                    .withBody(body.getBytes()).andProperties(props)
                    .build();

            logger.info("Send request : " + body);
            rabbitTemplate.send(exchange, routingKey, messagePayload);

        } catch (Exception e) {
            return new ResponseEntity<Object>(gson.toJson(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Object>(gson.toJson("Message processed"), HttpStatus.OK);
    }
}
