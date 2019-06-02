package ro.ionutmarin.iehs.controller;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.dao.DoctorDao;
import ro.ionutmarin.iehs.dao.UserDao;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.entity.DoctorEntity;
import ro.ionutmarin.iehs.entity.UserEntity;
import ro.ionutmarin.iehs.service.AlertService;
import ro.ionutmarin.iehs.sms.SmsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static ro.ionutmarin.iehs.util.Constants.ALERT_CONFIRMED;
import static ro.ionutmarin.iehs.util.Constants.ALERT_INITIALIZED;

@RestController
public class SmsController  {

    public static final String ROOM = "camera";
    public static final String BED = "pat";
    public static final String DELIMITER_CHAR = ",";
    @Autowired
    DoctorDao doctorDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AlertDao alertDao;

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
                    Message sms = new Message.Builder().body(replyMessageBody).build();
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


    private void ackAlertViaSms(String fromNumberWithouCountryPrefix) {
        try {
            DoctorEntity doctorEntity = doctorDao.findByPhoneNumber(fromNumberWithouCountryPrefix);
            UserEntity userEntity = userDao.findById(doctorEntity.getUserId());
            List<AlertEntity> alerts = alertDao.findAlertByUsernameAndStatus(userEntity.getUsername(), ALERT_INITIALIZED);
            alerts.stream().forEach(a -> {
                a.setStatus(ALERT_CONFIRMED);
                alertDao.save(a);
            });
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
