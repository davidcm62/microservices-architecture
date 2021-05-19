package ga.davidcm.authservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ga.davidcm.authservice.service.AuthenticationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitReceiver {

    @Autowired
    private AuthenticationService authenticationService;

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(String event) {
        System.out.printf("[auth-service]: user created event received %s\n",event);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(event);

            String id = json.get("id").asText();
            String username = json.get("username").asText();
            String password = json.get("password").asText();

            this.authenticationService.save(id, username, password);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
