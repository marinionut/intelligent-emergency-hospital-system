package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.model.HelloMessage;

@RestController
public class AlertSenderController {

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/sendMessage")
    public void sendMessage() throws Exception {
        this.template.convertAndSend("/topic/alerts", new HelloMessage(
                String.valueOf(Math.random())));
    }

}
