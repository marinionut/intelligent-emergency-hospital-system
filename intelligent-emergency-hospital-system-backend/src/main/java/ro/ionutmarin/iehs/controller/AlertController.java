package ro.ionutmarin.iehs.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;
import ro.ionutmarin.iehs.dao.AlertDao;
import ro.ionutmarin.iehs.dao.AlertDaoImpl;
import ro.ionutmarin.iehs.entity.AlertEntity;
import ro.ionutmarin.iehs.model.Greetings;
import ro.ionutmarin.iehs.model.HelloMessage;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicLong;

//@Controller
@RestController
public class AlertController {

    FluxProcessor processor = DirectProcessor.create().serialize();
    FluxSink sink = processor.sink();
    AtomicLong counter = new AtomicLong();

    @Autowired
    private AlertDao alertDao;

    @MessageMapping("/hello")
    @SendTo("/topic/alerts")
    public Greetings greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greetings("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/topic/alerts/{username}")
    @SendTo("/topic/alerts/{username}")
    public Greetings alert(@DestinationVariable String username, String message) {
        return new Greetings(message);
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
}
