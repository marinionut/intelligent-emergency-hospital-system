package ro.ionutmarin.iehs.sms;

import com.twilio.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;

@Service
public class SmsService {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "ACbf1234e856055e7492c47b385418a6f6";
    public static final String AUTH_TOKEN =
            "a12f4d2747d43cd7ca1c6b6e659512c3";

    public static final String HOSPITAL_PHONE_NUMBER =
            "+18652902543";

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String hospitalPhoneNumber;

    @Value("${ngrok.url}")
    private String ngrokUrl;



    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public  void sendSms(String toSmsNumber, String textMessage) {
        System.out.println("Sending sms to:" + toSmsNumber + " with text: " + textMessage);

        MessageCreator messageCreator = Message
                .creator(new PhoneNumber("+40" + toSmsNumber), // to
                       // new PhoneNumber("+14433718234"), // from
                        new PhoneNumber(hospitalPhoneNumber),
                        textMessage)
                .setStatusCallback(URI.create(ngrokUrl + "/callback/status/sms"));
        messageCreator.create();

        //System.out.println(message.getSid());
    }
}

