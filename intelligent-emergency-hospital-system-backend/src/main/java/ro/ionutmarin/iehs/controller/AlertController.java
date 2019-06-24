package ro.ionutmarin.iehs.controller;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.dao.AlertDaoImpl;
import ro.ionutmarin.iehs.dao.DoctorDao;
import ro.ionutmarin.iehs.dao.UserDao;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.UserEntity;
import ro.ionutmarin.iehs.model.Alert;
import ro.ionutmarin.iehs.model.AlertAck;
import ro.ionutmarin.iehs.model.Greetings;
import ro.ionutmarin.iehs.model.HelloMessage;
import ro.ionutmarin.iehs.service.AlertService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ro.ionutmarin.iehs.util.Constants.ALERT_CONFIRMED;
import static ro.ionutmarin.iehs.util.Constants.ALERT_INITIALIZED;

//@Controller
@RestController
public class AlertController implements Observer {

    FluxProcessor processor = DirectProcessor.create().serialize();
    FluxSink sink = processor.sink();
    AtomicLong counter = new AtomicLong();

    @Autowired
    private AlertDao alertDao;

    @MessageMapping("/alert/ack")
   // @SendTo("/topic/alerts")
    public void greeting(AlertAck alertAck) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("Received alert ack for username: " + alertAck.getUsername()
                + " and alert uid: " + alertAck.getAlertUid());

        AlertEntity alertEntity = alertDao.findAlertByUid(alertAck.getAlertUid());
        System.out.println("founded alert with id:" + alertEntity);
        alertEntity.setStatus(ALERT_CONFIRMED);
        alertDao.save(alertEntity);

        this.notifyClientForUpdate();
    }

    @MessageMapping("/topic/alerts/{username}")
    @SendTo("/topic/alerts/{username}")
    public Alert alert(@DestinationVariable String username, Alert alert) {
        //return new Greetings(message);
        return alert;
    }

    @SuppressWarnings("unused")
    @SubscribeMapping({ "/topic/alerts", "/topic/alerts/{username}" })
    public void listen(@DestinationVariable String username, Message message, MessageHeaders headers, MessageHeaderAccessor accessor) throws Exception {
        int i = 0;
        System.out.println("subscribed");
    }

    @GetMapping("/notifyClient")
    public void notifyClientForUpdate() {
        System.out.println("prepare to send sse notification to clients");
        sink.next("New alert! please refresh" + counter.getAndIncrement());
    }

    @GetMapping(path = "/emergency/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> sse() {
        System.out.println("send sse notification to clients");
        return processor.map(e -> ServerSentEvent.builder(e).build());
    }

    public static final String ROOM = "camera";
    public static final String BED = "pat";
    public static final String DELIMITER_CHAR = ",";

    @Autowired
    DoctorDao doctorDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AlertService alertService;

    @RequestMapping(path="/webhook/sms", produces="application/xml")
    @ResponseBody
    public void receiveSMS(HttpServletRequest request, HttpServletResponse response) {
        String messageBody = request.getParameter("Body");
        String fromNumber = request.getParameter("From"); // cu +40
        String fromNumberWithouCountryPrefix = fromNumber.substring(2, fromNumber.length());

        System.out.println("Message received via SMS from number" + fromNumber + " with message: " + messageBody);

        int roomNumber = -1;
        int bedNumber = -1;

        if (messageBody.toLowerCase().trim().contains("ok")) {
            ackAlertViaSms(fromNumberWithouCountryPrefix);
        } else {
            String [] splittedValues = messageBody.split(DELIMITER_CHAR);
            for(String a: splittedValues) {
                String pair = a.toLowerCase();
                if (pair.contains(ROOM)) {
                    roomNumber = Integer.parseInt(pair.replace(ROOM, Strings.EMPTY).trim());
                } else if (pair.contains(BED)) {
                    bedNumber = Integer.parseInt(pair.replace(BED, Strings.EMPTY).trim());
                }
            }
            if (roomNumber != -1 && bedNumber != -1) {
                try {
                    alertService.resolveAlert(roomNumber, bedNumber);

                    // Create a TwiML response for reply to message
                    Body replyMessageBody = new Body.Builder("Alerta a fost trimisa cu succes!").build();
                    com.twilio.twiml.messaging.Message sms = new com.twilio.twiml.messaging.Message.Builder().body(replyMessageBody).build();
                    MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

                    response.setContentType("application/xml");

                    try {
                        response.getWriter().print(twiml.toXml());
                    } catch (Exception e) {
                        System.out.println("Error on replying to alert sms");
                    }
                } catch (Exception e) {
                    System.out.println("Error on resolving alert");
                }
            }
        }

    }

    @RequestMapping(value = "/ackByPhoneNumber")
    public void sendMessage(@RequestParam("phoneNumber") String phoneNumber) throws Exception {
        this.ackAlertViaSms(phoneNumber);
    }

    private void ackAlertViaSms(String fromNumberWithouCountryPrefix) {
        try {
//            DoctorEntity doctorEntity = doctorDao.findByPhoneNumber(fromNumberWithouCountryPrefix);
            DoctorEntity doctorEntity = doctorDao.findAll()
                    .stream()
                    .filter(d -> d.getPhoneNumber().equals(fromNumberWithouCountryPrefix))
                    .collect(Collectors.toList())
                    .get(0);
            UserEntity userEntity = userDao.findById(doctorEntity.getUserId());
            List<AlertEntity> alerts = alertDao.findAlertByUsernameAndStatus(userEntity.getUsername(), ALERT_INITIALIZED);
            alerts.stream().forEach(a -> {
                a.setStatus(ALERT_CONFIRMED);
                alertDao.save(a);
            });
            this.notifyClientForUpdate();
        } catch (Exception e) {
            System.out.println("Error on ack alert");
        }
    }

    @RequestMapping(path="/callback/status/sms")
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String messageSid = request.getParameter("MessageSid");
        String messageStatus = request.getParameter("MessageStatus");

        System.out.println("SID: " + messageSid + ", Status:" + messageStatus);
    }
}
