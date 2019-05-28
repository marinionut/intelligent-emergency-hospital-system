package ro.ionutmarin.iehs.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ro.ionutmarin.iehs.model.Alert;

@Service
public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);
//    @KafkaListener(topics = "${kafka.topic.alerts}", groupId = "${kafka.group.id.alerts}")
//    public void consume(String message){
//        logger.info(String.format("$$ -> Consumed Message -> %s",message));
//    }

    @KafkaListener(topics = "${kafka.topic.alerts}", groupId = "${kafka.group.id.alerts}")
    public void consume(@Payload Alert data,
                        @Headers MessageHeaders headers) {
        logger.info("$$ Business consumer -> received data='{}'", data);

//        headers.keySet().forEach(key -> {
//            logger.info("$$ -> {}: {}", key, headers.get(key));
//        });
    }
}
