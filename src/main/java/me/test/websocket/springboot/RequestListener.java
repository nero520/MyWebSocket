package me.test.websocket.springboot;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

@Component
public class RequestListener implements ServletRequestListener {


    public void requestDestroyed(ServletRequestEvent sre) {

    }

    public void requestInitialized(ServletRequestEvent sre) {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();
    }
}
