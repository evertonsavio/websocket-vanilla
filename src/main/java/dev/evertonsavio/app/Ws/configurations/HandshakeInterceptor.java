package dev.evertonsavio.app.Ws.configurations;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.security.auth.login.CredentialException;
import java.util.Map;
import java.util.Random;

/**
 * @author everton.savio
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        attributes.put("user", getRandomNickName());

        System.out.println("HEADERS before Handshake");
        System.out.println(request.getPrincipal());
        System.out.println(request.getHeaders().get("sec-websocket-protocol").get(0));

        String path = request.getURI().getPath();
        String ticketId = path.substring(path.lastIndexOf('/') + 1);
        attributes.put("ticket", ticketId);
        System.out.println(request.getURI().getPath());
        System.out.println(request.getURI().getPath().substring(path.lastIndexOf("/") + 1));

/*        if(!request.getHeaders().get("sec-websocket-protocol").get(0).equals("NotAuth")) {
            System.out.println("Nao e IGUAL");
            throw new CredentialException("Problemas nas credenciais");
        }*/
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("HEADERS After Handshake");
        System.out.println(request.getHeaders());
        super.afterHandshake(request, response, wsHandler, ex);
    }



    public String getRandomNickName(){
        String[] nickNameArray={"Captão América","Deadpool","Superman","Hulk","Homem de Ferro","Homem Aranha","Thor",
                "Wolverine","Pantera Negra","Colossus", "Flash", "Aquaman", "Cyborg", "Batman"};
        Random random=new Random();
        return nickNameArray[random.nextInt(13)];
    }
}
