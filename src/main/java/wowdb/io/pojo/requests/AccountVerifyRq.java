package wowdb.io.pojo.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
public class AccountVerifyRq {
    @Schema(required = true, description = "Account username")
    @NotNull(message = "account_name does not exist")
    private String account_name;

    @Schema(required = true, description = "Account password")
    @NotNull(message = "account_password does not exist")
    private String account_password;
}
