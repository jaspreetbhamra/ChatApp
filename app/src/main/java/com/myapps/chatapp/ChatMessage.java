package com.myapps.chatapp;

import java.util.Random;

/**
 * Created by JaspreetKaur on 04-09-2016.
 */
public class ChatMessage {
    public String body,sender,receiver,senderName;
    public String Date,Time;
    public String msgid;
    public boolean isMine;

    public ChatMessage(String sender,String receiver,String messageString,String ID,boolean isMine)
    {
        body=messageString;
        this.isMine=isMine;
        this.sender=sender;
        msgid=ID;
        this.receiver=receiver;
        senderName=sender;
    }

    public void setMsgId(){
        msgid += "-"+String.format("%02d",new Random().nextInt(100));

    }
}

