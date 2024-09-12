package wowdb.io.pojo.responses;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@RegisterForReflection
public class CommonMsg {
    private Integer code;
    private String message;
}
