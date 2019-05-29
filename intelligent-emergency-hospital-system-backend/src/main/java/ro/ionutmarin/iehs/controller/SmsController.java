package ro.ionutmarin.iehs.controller;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class SmsController  {
    @RequestMapping(path="/webhook/sms", produces="application/xml")
    @ResponseBody
    public String receiveSMS(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getParameterMap().toString());
        System.out.println(request.getParameterMap().values().toString());
        System.out.println(request.getParameterNames());
        Message sms = new Message.Builder()
                .body(new Body.Builder("The Robots are coming! Head for the hills!").build())
                .build();

        MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

        return twiml.toXml();
    }

    @RequestMapping(path="/webhook/receive/sms", produces="application/xml")
    @ResponseBody
    public String receiveSMS2(@RequestBody String data) {
        Message sms = new Message.Builder()
                .body(new Body.Builder("The Robots are coming! Head for the hills!").build())
                .build();

        MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

        return twiml.toXml();
    }

    @RequestMapping(path="/callback/status/sms")
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String messageSid = request.getParameter("MessageSid");
        String messageStatus = request.getParameter("MessageStatus");

        System.out.println("SID: " + messageSid + ", Status:" + messageStatus);
    }
}
