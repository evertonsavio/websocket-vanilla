export class Options{
    device: string;
    status: number;
    user: string;

    constructor(device: string, status: number, user: string ){
        this.device = device;
        this.status = status;
        this.user = user;
    }

}

export class ChatMessageDto {
    command: number;
    sender: string;
    recipient: string;
    options: Options

    constructor(command: number ,sender: string,recipient: string, options: Options){
        this.command = command;
        this.sender= sender;
        this.recipient = recipient;
        this.options = options;
    }
}
