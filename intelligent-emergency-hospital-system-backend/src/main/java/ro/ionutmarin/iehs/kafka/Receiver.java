package ro.ionutmarin.iehs.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.ionutmarin.iehs.controller.Observer;
import ro.ionutmarin.iehs.model.Alert;
import ro.ionutmarin.iehs.model.HelloMessage;
import ro.ionutmarin.iehs.sms.SmsService;

@Service
public class Receiver {

    private final Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    SmsService smsService;

    @Autowired
    Observer observer;

    @Autowired
    private SimpMessagingTemplate template;

    @KafkaListener(topics = "${kafka.topic.alerts}", groupId = "${kafka.group.id.alerts}")
    public void consume(@Payload Alert data,
                        @Headers MessageHeaders headers) {
        logger.info("$$ Business consumer -> received data='{}'", data);
        try {
            logger.info("$$ Business consumer -> prepare to send sms alert");
            smsService.sendSms(data.getToPhoneNumber(), data.getMessage());
        } catch (Exception e) {
            logger.error("----------EEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!", e);
            System.out.println("----------EEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!");
            System.out.println(e);
        }

        try {
            logger.info("$$ Business consumer -> prepare to send web alert");
            template.convertAndSend("/topic/alerts/" + data.getUsername(), data);
        }  catch (Exception e) {
            logger.error("----------EEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!", e);
            System.out.println("----------EEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!");
            System.out.println(e);
        }
    }
}
