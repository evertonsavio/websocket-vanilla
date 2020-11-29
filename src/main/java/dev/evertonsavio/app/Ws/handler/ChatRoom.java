package dev.evertonsavio.app.Ws.handler;

import com.google.gson.Gson;
import dev.evertonsavio.app.Ws.model.MessageModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatRoom extends AbstractWebSocketHandler {

    public final static List<WebSocketSession> sessionList = Collections.synchronizedList(new ArrayList<WebSocketSession>());
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    FileOutputStream output;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("Connection established..."+webSocketSession.getRemoteAddress());
        logger.info(webSocketSession.getAttributes().get("user")+" Login");
        //webSocketSession.sendMessage(new TextMessage("I'm "+(webSocketSession.getAttributes().get("user"))));
        webSocketSession
                .sendMessage(new TextMessage("{\"user\":\"IDENTIDADE\",\"message\":\"Você é o "+(webSocketSession.getAttributes().get("user"))+"\"}"));
        logger.info("I'm "+(webSocketSession.getAttributes().get("user")));
        sessionList.add(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) throws Exception {
        logger.info("Connection closed..."+webSocketSession.getRemoteAddress()+" "+status);
        logger.info(webSocketSession.getAttributes().get("user")+" Logout");
        sessionList.remove(webSocketSession);
    }

    @Override
    public void handleTextMessage(WebSocketSession websocketsession, TextMessage message) throws IOException {
        logger.info("INFO_SESS: "+ websocketsession);

        Gson gson = new Gson();
        MessageModel messageModel = gson.fromJson(message.getPayload(), MessageModel.class);

        if(messageModel.getUser().equals("")){

            logger.info("VALOR NULO");

            for(WebSocketSession session : sessionList){
                logger.info(message.getPayload());
                logger.info(session.getId());

                messageModel.setUser(websocketsession.getAttributes().get("user").toString());

                logger.info(messageModel.getUser());
                logger.info(messageModel.getMessage());
                TextMessage textMessage = new TextMessage(messageModel.toString());
                session.sendMessage(textMessage);
            }
        }else{
            for(WebSocketSession sess: sessionList){
                if(sess.getAttributes().get("user").equals(messageModel.getUser())){

                    logger.info("IGUAL A UM USUARIO EXISTENTE");

                    messageModel.setUser(websocketsession.getAttributes().get("user").toString());

                    logger.info(messageModel.getUser());
                    logger.info(messageModel.getMessage());
                    TextMessage textMessage = new TextMessage(messageModel.toString());
                    sess.sendMessage(textMessage);
                }
            }
        }




       /* String payload=message.getPayload();
        String textString;
        try {
            if(payload.endsWith(":fileStart")){
                output=new FileOutputStream(new File("D:\\images\\"+payload.split(":")[0]));
            }else if(payload.endsWith(":fileFinishSingle")){
                output.close();
                String fileName=payload.split(":")[0];
                for(WebSocketSession session:sessionList){
                    if(session.getId().equals(websocketsession.getId())){
                        textString=" I ("+format.format(new Date())+")<br>";
                    }else{
                        textString=websocketsession.getAttributes().get("user")+" ("+format.format(new Date())+")<br>";
                    }
                    TextMessage textMessage = new TextMessage(textString);
                    session.sendMessage(textMessage);
                    sendPicture(session,fileName);
                }
            }else if(payload.endsWith(":fileFinishWithText")){
                output.close();
                String fileName=payload.split(":")[0];
                for(WebSocketSession session:sessionList){
                    sendPicture(session,fileName);
                }
            }else{
                for(WebSocketSession session: sessionList){
                    if(session.getId().equals(websocketsession.getId())){
                        textString=" I ("+format.format(new Date())+")<br>"+payload;
                    }else{
                        textString=websocketsession.getAttributes().get("user")+" ("+format.format(new Date())+")<br>"+payload;
                    }
                    TextMessage textMessage = new TextMessage(textString);
                    session.sendMessage(textMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
    {
        ByteBuffer buffer= message.getPayload();
        try {
            output.write(buffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.info(throwable.toString());
        logger.info("WS connection error,close..."+webSocketSession.getRemoteAddress());
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }

    public void sendPicture(WebSocketSession session,String fileName){
        FileInputStream input;
        try {
            File file=new File("D:\\images\\"+fileName);
            input = new FileInputStream(file);
            byte bytes[] = new byte[(int) file.length()];
            input.read(bytes);
            BinaryMessage byteMessage=new BinaryMessage(bytes);
            session.sendMessage(byteMessage);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
