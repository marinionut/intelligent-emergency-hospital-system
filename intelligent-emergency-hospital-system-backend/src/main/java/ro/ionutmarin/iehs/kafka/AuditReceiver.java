package ro.ionutmarin.iehs.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ro.ionutmarin.iehs.controller.Observer;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.model.Alert;

import java.sql.Timestamp;

@Service
public class AuditReceiver {
    private final Logger logger = LoggerFactory.getLogger(Receiver.class);
//    @KafkaListener(topics = "${kafka.topic.alerts}", groupId = "${kafka.group.id.alerts}")
//    public void consume(String message){
//        logger.info(String.format("$$ -> Consumed Message -> %s",message));
//    }
    @Autowired
    private AlertDao alertDao;

    @Autowired
    Observer observer;

    @KafkaListener(topics = "${kafka.topic.alerts}", groupId = "${kafka.group.id.alerts.audit}")
    public void consume(@Payload Alert data,
                        @Headers MessageHeaders headers) {

        logger.info("$$ Audit consumer: inserting to db -> received data='{}'", data);

        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setMessage(data.getMessage());
        alertEntity.setRoomNumber(data.getRoomNumber());
        alertEntity.setTimestamp(new Timestamp(data.getTimestamp()));
        alertEntity.setType("notification + sms");
        alertEntity.setUsername(data.getUsername());

        alertDao.save(alertEntity);

        try {
            observer.notifyClientForUpdate();
        }  catch (Exception e) {
            System.out.println("\n\n\nEEEEEERRRRROOOORRRRRR!!!!!!!!!!!!!!!!");
            System.out.println(e);
        }
    }
}
