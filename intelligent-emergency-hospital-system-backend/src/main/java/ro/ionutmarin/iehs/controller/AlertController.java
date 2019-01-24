package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ro.ionutmarin.iehs.model.Greetings;
import ro.ionutmarin.iehs.model.HelloMessage;

@Controller
public class AlertController {

    @MessageMapping("/hello")
    @SendTo("/topic/alerts")
    public Greetings greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greetings("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String test(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, " + HtmlUtils.htmlEscape(message.getName());
    }

    @SuppressWarnings("unused")
    @SubscribeMapping({ "/topic/test", "/topic/alerts" })
    public void listen(Message message, MessageHeaders headers, MessageHeaderAccessor accessor) throws Exception {
        int i = 0;
        System.out.println("subscribed");
    }
}
