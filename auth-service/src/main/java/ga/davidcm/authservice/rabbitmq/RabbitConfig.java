package ga.davidcm.authservice.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private static final String USER_CREATED_EXCHANGE = "USER_CREATED_EXCHANGE";

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange(USER_CREATED_EXCHANGE);
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    @Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }
}
