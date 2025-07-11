package space.thinhtran.commonmodule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResp {
    private String statusCode;
    private String title;
    private String detail;
    private List<String> fieldErrors = new ArrayList<>();

    public ErrorResp(String statusCode, String title, String detail) {
        this.statusCode = statusCode;
        this.title = title;
        this.detail = detail;
    }
}
