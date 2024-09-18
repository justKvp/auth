package wowdb.io.pojo.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
public class AccountCreateRq {
    @Schema(required = true, description = "Account username")
    @NotBlank(message = "account_name does not exist")
    private String account_name;

    @Schema(required = true, description = "Account password")
    @NotBlank(message = "account_password does not exist")
    private String account_password;

    @Schema(required = true, description = "Account email")
    @NotBlank(message = "account_email does not exist")
    private String account_email;
}
