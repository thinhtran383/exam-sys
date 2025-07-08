package space.thinhtran.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/noti")
@RequiredArgsConstructor
public class SendNotiController {
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("{username}/{notification}")
    public void sendPrivateNotification(@PathVariable String username, @PathVariable String notification) {
        messagingTemplate.convertAndSendToUser(username, "/queue/notify", notification);
    }
}
