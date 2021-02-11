import { Injectable } from '@angular/core';
import { ChatMessageDto } from '../models/chatMessageDto';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  webSocket: WebSocket;
  chatMessages: ChatMessageDto[] = [];

  constructor() { }

  public openWebSocket(){

    //this.webSocket = new WebSocket(`ws://172.31.1.70:8009/chat/${Math.random()}`); 
    //this.webSocket = new WebSocket(`ws://localhost:59921/socket/eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkOGY3ZDcyMy0wMzQ1LTQ4NDktOTEyNi03ZDFlNTZhN2NiMzciLCJleHAiOjE2MTY5OTcxMjl9.lpa8i1ASJgNDdfhwjTRamhLZWn_QSNLiMvXvx6wN_1ka4RV0toAusjqHqHit3hexMIk1FdaYX7IQB333TRH7Ag`); 
    //this.webSocket = new WebSocket(`ws://localhost:8008/socket/eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4YzQwYWEyMC00MzViLTQxYWEtODE1Yi1iMzlhMWRjYmU2YmMiLCJleHAiOjE2MDg3MzUyMDR9.ja5KmQ3Jr9YfvUYDQWKP7xniEasxuGIpGKsCoFL80gnK4S5vJEJLI2SSDZHzJ3TZKt5hIUAy33IxxsjhDXxTHgpdtidg`); 
    //this.webSocket = new WebSocket(`ws://172.31.1.101:8008/socket/eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NzM3ODNhNy05ZGY2LTQ5NTEtOGUzMS05MzU4N2NlMDNlMmEiLCJleHAiOjE2MDc4OTM2NTN9.i6W5PvRJYSnbpZ9zxGHb4-Ep_IrihEezxIokkV6_BwwXBMByxFI-eegAXpSXvEYL-RDbcrduQSC3l8lG8xhDww`); 
    this.webSocket = new WebSocket(`ws://localhost:8011/websocket-service/socket/eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MjU4ZDY4Ni00YTFjLTRkZTgtYWI3Zi00NDlmZGJmMmExYTgiLCJleHAiOjE2MTE4NjAzMjV9.U7QZzRSnRkAeqG581LTUMUWEqXeDO4oD2gIeLha7puClsFHnXgotcwOQCGVlhA_2WfzGecThzp4rQvBMjEsYTgpdtidg`);
    
    this.webSocket.onopen = (event) => {
      console.log('Open: ', event);
    };

    this.webSocket.onmessage = (event) => {
      console.log("EVENTDATA: ",event.data)
      const chatMessageDto = JSON.parse(event.data);
      this.chatMessages.push(chatMessageDto);
    };

    this.webSocket.onclose = (event) => {
      console.log('Close: ', event);
    };
  }

  public sendMessage(chatMessageDto: ChatMessageDto){
    this.webSocket.send(JSON.stringify(chatMessageDto));
  }

  public closeWebSocket() {
    this.webSocket.close();
  }
}
