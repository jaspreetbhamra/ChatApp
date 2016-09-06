package com.myapps.fragments;

/**
 * Created by JaspreetKaur on 04-09-2016.
 */

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.myapps.chatapp.ChatAdapter;
import com.myapps.chatapp.ChatMessage;
import com.myapps.chatapp.CommonMethods;
import com.myapps.chatapp.R;


public class Chats extends Fragment implements OnClickListener {

    private EditText msg_edittext;
    private String user1 = "Grishma", user2 = "Jazz";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ChatAdapter chatAdapter;
    ListView msgListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstancesState){
        View view=inflater.inflate(R.layout.chat_layout, container, false);
        random=new Random();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Chats");
        msg_edittext= (EditText) view.findViewById(R.id.messageEditText);
        msgListView= (ListView) view.findViewById(R.id.messageListView);
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);

        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist= new ArrayList<>();
        chatAdapter= new ChatAdapter(getActivity(),chatlist);
        msgListView.setAdapter(chatAdapter);
        return view;

    }

    public void sendTextMessage(View v){
        String message= msg_edittext.getEditableText().toString();
        if(!message.equalsIgnoreCase("")){
            final ChatMessage chatMessage= new ChatMessage(user1,user2,message,""+random.nextInt
                    (1000),true);
            chatMessage.setMsgId();
            chatMessage.body=message;
            chatMessage.Date=CommonMethods.getCurrentDate();
            chatMessage.Time= CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.sendMessageButton)
            sendTextMessage(v);
    }

}
