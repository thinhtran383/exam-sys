package space.thinhtran.userservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import space.thinhtran.commonmodule.dto.message.NotificationMessage;
import space.thinhtran.commonmodule.dto.response.PageResponse;
import space.thinhtran.userservice.dto.response.UserResp;
import space.thinhtran.userservice.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
public class UserController {
    private final UserService userService;
    private final JmsTemplate jmsTemplate;

    @GetMapping
    public ResponseEntity<PageResponse<UserResp>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @PostMapping("/test-push-message")
    public void pushMessage(@RequestBody NotificationMessage message) {
        jmsTemplate.convertAndSend("notification.queue", message);
    }

}
