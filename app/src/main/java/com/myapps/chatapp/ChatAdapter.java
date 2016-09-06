package com.myapps.chatapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JaspreetKaur on 04-09-2016.
 */
public class ChatAdapter extends BaseAdapter{

    private static LayoutInflater inflater = null;
    ArrayList<ChatMessage> chatMessageList;

    public ChatAdapter(Activity activity, ArrayList<ChatMessage> list){
        chatMessageList = list;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = (ChatMessage) chatMessageList.get(position);
        View vi = convertView;
        if(convertView==null){
            vi = inflater.inflate(R.layout.chat_bubble, null);
        }
        TextView msg = (TextView) vi.findViewById(R.id.messageText);
        msg.setText(message.body);
        LinearLayout layout = (LinearLayout) vi.findViewById(R.id.bubbleLayout);
        LinearLayout parentLayout = (LinearLayout) vi.findViewById(R.id.bubbleLayoutParent);
        if(message.isMine){
            layout.setBackgroundResource(R.drawable.bubble1);
            parentLayout.setGravity(Gravity.LEFT);
        }
        else{
            layout.setBackgroundResource(R.drawable.bubble2);
            parentLayout.setGravity(Gravity.RIGHT);
        }
        msg.setTextColor(Color.BLACK);
        return vi;
    }

    public void add(ChatMessage object){
        chatMessageList.add(object);
    }
}
