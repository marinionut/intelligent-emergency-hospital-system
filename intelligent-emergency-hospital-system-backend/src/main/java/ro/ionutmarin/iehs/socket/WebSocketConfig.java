package ro.ionutmarin.iehs.socket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String ENDPOINT_SOCKET = "/gs-guide-websocket";
    public static final String APPLICATION_PREFIX = "/app";
    public static final String BROKER_NAME = "/topic";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(BROKER_NAME);
        config.setApplicationDestinationPrefixes(APPLICATION_PREFIX, BROKER_NAME);
//        config.setApplicationDestinationPrefixes({"/app", "/topic"});.

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ENDPOINT_SOCKET);
        registry.addEndpoint(ENDPOINT_SOCKET).setAllowedOrigins("*").withSockJS();

    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
    }

}
