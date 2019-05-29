package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.dao.AppointmentDao;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.model.Alert;
import ro.ionutmarin.iehs.model.HelloMessage;
import ro.ionutmarin.iehs.service.AlertService;
import ro.ionutmarin.iehs.sms.SmsService;

import java.sql.Timestamp;

@RestController
public class AlertSenderController {

    public static final String ALERT_TYPE_SMS = "sms";
    public static final String ALERT_TYPE_NOTIFICATION = "notification";

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private AlertDao alertDao;

    @Autowired
    private SmsService smsService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private AppointmentDao appointmentDao;

    @RequestMapping(value = "/sendMessage")
    public void sendMessage(@RequestParam("username") String username,
                            @RequestParam("message") String message,
                            @RequestParam("roomNumber") int roomNumber) throws Exception {
        AlertEntity alertEntity = new AlertEntity(username,
                message,
                new Timestamp(System.currentTimeMillis()),
                ALERT_TYPE_NOTIFICATION,
                roomNumber);
        alertDao.save(alertEntity);

        this.template.convertAndSend("/topic/alerts/" + username, new HelloMessage(message));
    }

    @RequestMapping(value = "/sendAlert")
    public void simple(@RequestParam("username") String username,
                       @RequestParam("message") String message,
                       @RequestParam("roomNumber") int roomNumber) {
        AlertEntity alertEntity = new AlertEntity(username,
                message,
                new Timestamp(System.currentTimeMillis()),
                ALERT_TYPE_NOTIFICATION,
                roomNumber);
        alertDao.save(alertEntity);

        template.convertAndSend("/topic/alerts/" + username, new HelloMessage(message));
    }

    @RequestMapping(value = "/sendSms")
    public void sendSms(@RequestParam("username") String username,
                        @RequestParam("message") String message,
                        @RequestParam("phoneNumber") String phoneNumber,
                        @RequestParam("roomNumber") int roomNumber) throws Exception {
        AlertEntity alertEntity = new AlertEntity(username,
                message,
                new Timestamp(System.currentTimeMillis()),
                ALERT_TYPE_SMS,
                roomNumber);
        alertDao.save(alertEntity);

        smsService.sendSms(phoneNumber, message);
    }

    @RequestMapping(value ="/resolveAlert")
    public Alert resolveAlert(@RequestParam("roomNumber") int roomNumber,
                              @RequestParam("bedNumber") int bedNumber) throws Exception {
        return alertService.resolveAlert(roomNumber, bedNumber);
    }
}
