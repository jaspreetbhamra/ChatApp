package com.myapps.chatapp;

import android.os.Looper;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.util.logging.Handler;

/**
 * Created by JaspreetKaur on 04-09-2016.
 */
public class MyXMPP {
    public static boolean connected = false;
    public boolean loggedIn = false;
    public static boolean isConnecting = false;
    public static boolean isToasted = true;
    private boolean chat_Created = false;
    private String serverAddress;
    public static XMPPTCPConnection connection;
    public static String loginUser;
    public static String passwordUser;
    Gson gson;
    MyService Context;
    public static MyXMPP instance = null;
    public static boolean instanceCreated = false;

    public MyXMPP(MyService context, String serverAddress, String loginUser, String passwordUser){
        this.serverAddress = serverAddress;
        this.loginUser = loginUser;
        this.passwordUser = passwordUser;
        this.context = context;
        init();
    }

    public static MyXMPP getInstance(MyService context, String server, String user, String pass){
        if(instance==null){
            instance = new MyXMPP(context, server, user, pass);
            instanceCreated = true;
        }
        return instance;
    }

    public org.jivesoftware.smack.chat.Chat myChat;

    ChatManagerListenerImpl mChatManagerListener;
    MMessageListener mMessageListener;
    String text = "";
    String mMessage = "", mReceiver = "";
    static {
        try{
            Class.forName("org.jivesoftware.smack.ReconnectionManager");
        }
        catch (ClassNotFoundException ex){

        }
    }

    public void init(){
        gson = new Gson();
        mMessageListener = new MMessageListener(context);
        mChatManagerListener = new ChatManagerListenerImpl();
    }

    private void initialiseConnection(){
        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setServiceName(serverAddress);
        config.setHost(serverAddress);
        config.setPort(5222);
        config.setDebuggerEnabled(true);
        XMPPTCPConnection.setUseStreamManagementResumptiodDefault(true);
        XMPPTCPConnection.setUseStreamManagementDefault(true);
        connection = new XMPPTCPConnection(config.build());
        XMPPConnectionListener connectionListener= new XMPPConnectionListener();
        connection.addConnectionListener(connectionListener);
    }

    public void disconnect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection.disconnect();
            }
        }).start();

    }

    public void connect(final String caller){
        AssyncTask<Void ,Void ,Boolean> connectionThread=new AssyncTask<>(){
            @Override
        protected synchronized Boolean doInBackground(Void... arg0){
                if(connection.isConnected())
                    return false;
                isConnecting=true;
                if(isToasted)
                    new Handler(Looper.getMainLooper()).post(new Runnable()
                    {
                        @Override
                        public void run() {
                            Toast.makeText(MyXMPP.this, caller+"=>connecting...", Toast
                                    .LENGTH_SHORT).show();
                        }

                    });



            }
        }
    }
}

class ChatManagerListenerImpl implements ChatManagerListener{

    @Override
    public void chatCreated(final org.jivesoftware.smack.chat.Chat chat, final boolean createdLocally) {
        if(!createdLocally){
            chat.addMessageListener(mMessageListener);
        }
    }
}
