package org.javaboy.vhr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author gcx_javaboy
 * @version 1.0
 * @PROJECT_NAME: vhr
 * @DATE: 2021/2/2 20:53
 */
@Configuration
@EnableWebSocketMessageBroker//开启消息代理
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册点
//      registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/ws/ep").setAllowedOrigins("http://localhost:8080").withSockJS();
        //setAllowedOrigins("*")允许前端发送信号
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue");//消息代理。一对一
    }
}
