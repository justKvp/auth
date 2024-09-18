package wowdb.io.pojo.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
public class AccountCreateRq {
    @Schema(required = true, description = "Account username")
    @NotNull(message = "account_name does not exist")
    private String account_name;

    @Min(message = "Minimal lenght of password : 1", value = 1)
    @Schema(required = true, description = "Account password")
    @NotNull(message = "account_password does not exist")
    private String account_password;

    @Schema(required = true, description = "Account email")
    @NotNull(message = "account_email does not exist")
    private String account_email;
}
