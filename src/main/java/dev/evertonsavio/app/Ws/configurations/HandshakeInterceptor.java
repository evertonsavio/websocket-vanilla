package dev.evertonsavio.app.Ws.configurations;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
import java.util.Random;

/**
 * @author everton.savio
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        attributes.put("user", getRandomNickName());
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

    public String getRandomNickName(){
        String[] nickNameArray={"Captão América","Deadpool","Superman","Hulk","Homen de Ferro","Homem Aranha","Thor",
                "Wolverine","Pantera Negra","Colossus", "The Flash", "Aquaman", "Cyborg", "Batman"};
        Random random=new Random();
        return nickNameArray[random.nextInt(13)];
    }
}
