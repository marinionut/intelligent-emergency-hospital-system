package ro.ionutmarin.iehs.sms;

import com.twilio.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SmsService {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "ACc5fb1c6036f635f82090a40128cc9fcb";
    public static final String AUTH_TOKEN =
            "37b0661fa5249e579fa86f9b6c95fd0e";

    @PostConstruct
    public void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public  void sendSms(String toSmsNumber, String textMessage) {

        MessageCreator messageCreator = Message
                .creator(new PhoneNumber(toSmsNumber), // to
                        new PhoneNumber("+14433718234"), // from
                        textMessage);
        //messageCreator.create();

        //System.out.println(message.getSid());
    }
}

