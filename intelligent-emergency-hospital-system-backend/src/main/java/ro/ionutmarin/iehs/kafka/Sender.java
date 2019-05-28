package ro.ionutmarin.iehs.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ro.ionutmarin.iehs.model.Alert;

@Service
public class Sender {
    private static final Logger logger = LoggerFactory.getLogger(Sender.class);

    @Value("${kafka.topic.alerts}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Alert> kafkaTemplate;

    public void sendMessage(Alert alert){
        logger.info("$$ -> sending data='{}' to topic='{}'", alert, topic);

        Message<Alert> message = MessageBuilder
                .withPayload(alert)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        this.kafkaTemplate.send(message);
    }
}
