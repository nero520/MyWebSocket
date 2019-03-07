package me.test.websocket;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 * 类级别的注释，告诉java平台他注释的类
 * 实际是要成为一个websocket端点
 */
@ServerEndpoint("/echo")
public class EchoServer {


    @OnMessage
    public String echo(String incomingMessage) {

        return "I got this ("+incomingMessage+") so I am sending it back!";
    }
}
