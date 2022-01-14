package com.slutprojekt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.slutprojekt.GreetingHandler;
@Configuration
// @EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {

    // @CrossOrigin
    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry config) {
    //     config.enableSimpleBroker("/topic");
    //     config.setApplicationDestinationPrefixes("/app");
    // }
    // @CrossOrigin
    // @Override
    // public void registerStompEndpoints(StompEndpointRegistry registry) {
    //     registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS(); 
    // }
    

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(greetingHandler(), "/greeting")
            .setAllowedOriginPatterns("*")
            .withSockJS();
    }

    @Bean
    public WebSocketHandler greetingHandler() {
        return new GreetingHandler();
    }

    

}