package me.test.websocket.springboot;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.Resource;

/**
 * https://www.cnblogs.com/zhuxiaojie/p/6238826.html
 */
@Configuration
public class WebSocketConfig {

    @Resource
    public RequestListener requestListener;

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return  new ServerEndpointExporter();
    }



    @Bean
    public ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<RequestListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<RequestListener>();
        servletListenerRegistrationBean.setListener(requestListener);
        return servletListenerRegistrationBean;
    }

}
