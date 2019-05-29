package ro.ionutmarin.iehs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ionutmarin.iehs.kafka.Sender;
import ro.ionutmarin.iehs.model.Alert;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {


    private final Sender sender;

    @Autowired
    public KafkaController(Sender sender) {
        this.sender = sender;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("roomNumber") int roomNumber, @RequestParam("message") String message){
        Alert alert = new Alert();
        alert.setRoomNumber(roomNumber);
        alert.setMessage(message);
        this.sender.sendMessage(alert);
    }

}
