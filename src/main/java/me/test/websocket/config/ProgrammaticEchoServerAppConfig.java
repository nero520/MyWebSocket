package me.test.websocket.config;

import me.test.websocket.ProgrammaticEchoServer;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.HashSet;
import java.util.Set;

public class ProgrammaticEchoServerAppConfig implements ServerApplicationConfig {

    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
        Set configs = new HashSet<ServerEndpointConfig>();
        ServerEndpointConfig sec = ServerEndpointConfig.Builder.create(ProgrammaticEchoServer.class,"/programmaticEcho").build();
        configs.add(sec);
        return configs;
    }

    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
        return scanned;
    }
}
