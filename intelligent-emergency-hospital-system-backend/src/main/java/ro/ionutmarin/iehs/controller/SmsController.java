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

}
