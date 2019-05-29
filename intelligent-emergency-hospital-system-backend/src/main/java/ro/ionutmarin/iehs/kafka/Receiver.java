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
            smsService.sendSms(data.getToPhoneNumber(), data.getMessage());
        } catch (Exception e) {
            System.out.println("\n\n\nEEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!");
            System.out.println(e);
        }

        try {
            template.convertAndSend("/topic/alerts/" + data.getUsername(), new HelloMessage(data.getMessage()));
        }  catch (Exception e) {
            System.out.println("\n\n\nEEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!");
            System.out.println(e);
        }
    }
}
