package space.thinhtran.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WsController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String handleHello(String message) {
        log.info("Received from client: {}", message);
        return "Server received: " + message;
    }

    /**
     * 2️⃣ Gửi tin nhắn riêng tới user
     * client gửi: client.send("/app/private", {}, "Private message")
     * server trả về qua "/user/{sessionId}/queue/reply"
     */
    @MessageMapping("/private")
    @SendToUser("/queue/reply")
    public String handlePrivate(String message) {
        log.info("Received private message: {}", message);
        return "Private reply: " + message;
    }

    /**
     * 3️⃣ Gửi chủ động từ server tới client
     * ví dụ gửi thông báo broadcast chủ động tới "/topic/notifications"
     */
    public void sendNotification(String notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    /**
     * 4️⃣ Gửi chủ động từ server tới user cụ thể
     * ví dụ gửi thông báo riêng tới user qua "/user/{username}/queue/notify"
     */
    public void sendPrivateNotification(String username, String notification) {
        messagingTemplate.convertAndSendToUser(username, "/queue/notify", notification);
    }
}
