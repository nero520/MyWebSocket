package me.test.websocket;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 类级别的注释，告诉java平台他注释的类
 * 实际是要成为一个websocket端点
 */
@ServerEndpoint("/echoAnnotion")
public class EchoAnnotionServer {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static CopyOnWriteArraySet<EchoAnnotionServer> webSocketSet = new CopyOnWriteArraySet<EchoAnnotionServer>();
    @OnOpen
    public void init(Session session){
        this.session = session;
        webSocketSet.add(this);
    }

//    @OnOpen
//    public void init(Session session, ServerEndpointConfig serverEndpointConfig){
//
//
//    }

    @OnMessage
    public String echo(String incomingMessage) {

        return "I got this ("+incomingMessage+") so I am sending it back!";
    }

//    @OnMessage
//    public void processBinary(byte[] messageData,Session session){
//
//    }
}
