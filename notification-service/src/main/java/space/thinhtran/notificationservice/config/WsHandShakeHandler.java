package space.thinhtran.notificationservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import space.thinhtran.commonmodule.util.AuthenticationUtil;

import java.security.Principal;
import java.util.Map;

@Component
@Slf4j(topic = "HandShake")
public class WsHandShakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = AuthenticationUtil.extractUserId();

        return () -> userId;
    }
}
