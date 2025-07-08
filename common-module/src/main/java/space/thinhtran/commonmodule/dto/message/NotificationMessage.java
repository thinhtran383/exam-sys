package space.thinhtran.commonmodule.dto.message;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class NotificationMessage implements Serializable {
    private String userId;
    private String title;
    private String content;
    private String type;
}

