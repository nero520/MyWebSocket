package me.test.websocket;


import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;

public class ProgrammaticEchoServer extends Endpoint {

    public void onOpen(Session session, EndpointConfig endpointConfig) {

        final Session mySession = session;
        mySession.addMessageHandler(new MessageHandler.Whole<String>() {

            public void onMessage(String message) {

                try {
                    mySession.getBasicRemote().sendText("programmaticEcho I got this ("+message+") so I am sending it back!");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    System.out.println("oh dear,something went wrong trying to send the message back:"+ioe.getMessage());
                }

            }
        });

    }
}
