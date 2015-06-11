package pl.garciapl.currencyfair.service.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.garciapl.currencyfair.service.domain.queue.Payload;
import pl.garciapl.currencyfair.service.processor.DataProcessor;

public class QueueListener extends AbstractAdaptableMessageListener {

    @Autowired
    @Qualifier("dataProcessor")
    private DataProcessor dataProcessor;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
    }

    public void onMessagePayload(Payload payload) {
        dataProcessor.processMessage(payload);
    }
}
