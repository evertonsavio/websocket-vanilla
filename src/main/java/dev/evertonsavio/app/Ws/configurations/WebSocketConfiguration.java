package dev.evertonsavio.app.Ws.configurations;

import dev.evertonsavio.app.Ws.handler.ChatRoom;
import dev.evertonsavio.app.Ws.handler.ChatWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * Websocket App
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {



    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(chatRoom(), "/chat")
                .setAllowedOrigins("*")
                .addInterceptors(handshakeInterceptor());

    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor(){
        return new HandshakeInterceptor();
    }

    @Bean
    public ChatRoom chatRoom(){
        return new ChatRoom();
    }



/*  //Versao 1
    public static final String CHAT_ENDPOINT = "/chat";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getChatWebSocketHandler(), CHAT_ENDPOINT)
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler getChatWebSocketHandler(){
        return new ChatWebsocketHandler();
    }*/



}
