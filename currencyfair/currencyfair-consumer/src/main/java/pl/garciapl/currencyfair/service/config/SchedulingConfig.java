package pl.garciapl.currencyfair.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * Created by lukasz on 29.05.15.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    @Qualifier("messageBrokerSockJsTaskScheduler")
    @Autowired
    private Executor messageBroker;

    @Qualifier("defaultSockJsTaskScheduler")
    @Autowired
    private Executor defaultSockJs;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(defaultSockJs);
    }
}