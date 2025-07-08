package space.thinhtran.notificationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import space.thinhtran.notificationservice.interceptor.WsHandShakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WsConfig implements WebSocketMessageBrokerConfigurer {
    private final WsHandShakeHandler wsHandShakeHandler;
    private final WsHandShakeInterceptor wsHandShakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/notify")
                .setAllowedOrigins("*")
                .setHandshakeHandler(wsHandShakeHandler)
                .addInterceptors(wsHandShakeInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // prefix for @SendTo
        config.setApplicationDestinationPrefixes( "/app"); // prefix for @MessageMapping
        config.setUserDestinationPrefix("/user"); // prefix for @SendToUser
    }


}
