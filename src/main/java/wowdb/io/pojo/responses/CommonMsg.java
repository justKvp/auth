package wowdb.io.pojo.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommonMsg {
    private Integer code;
    private String message;
}
