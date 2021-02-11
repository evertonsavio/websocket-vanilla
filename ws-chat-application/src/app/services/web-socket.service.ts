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


    this.webSocket = new WebSocket(`ws://localhost:9011/websocket-service/socket/eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzYWViYzcxZC1iZGMwLTQ3YmQtOGMzMi1lYTdhYmZhNjdmZGEiLCJleHAiOjE2MTA4MzE0MDV9.TAvdyZ7XtfCN8kkidAoOhPtCJuNleCAvi_X7bF7Q-wqze4G3i3osU_4LJ_r6YTs0BPzjnjzR693HA_O6-eJaaApdtidg`);

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
