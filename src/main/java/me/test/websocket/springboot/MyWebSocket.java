package me.test.websocket.springboot;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/springboot/websocket",configurator = HttpSessionConfigurator.class)
@Component
public class MyWebSocket {


    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private HttpSession httpSession;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, ServerEndpointConfig config){
        this.session = session;
        webSocketSet.add(this);//加入set中

        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        User user = (User)httpSession.getAttribute(SessionName.USER);
        if(user != null){
            this.session = session;
            this.httpSession = httpSession;
        }else{
            //用户未登陆
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
        for(MyWebSocket item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


}
