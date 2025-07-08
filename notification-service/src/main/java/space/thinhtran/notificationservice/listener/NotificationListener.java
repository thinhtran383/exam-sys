package space.thinhtran.notificationservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import space.thinhtran.commonmodule.dto.message.NotificationMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final SimpMessagingTemplate messagingTemplate;

    @JmsListener(destination = "notification.queue")
    public void handleNotification(NotificationMessage notificationMessage) {
        log.info("Received notification message: {}", notificationMessage);

        messagingTemplate.convertAndSendToUser(notificationMessage.getUserId(), "/queue/notify", notificationMessage);

    }
}
