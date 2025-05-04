package net.engineeringdisgest.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotEmpty
    @Schema(description = "Give Username")
    private String userName;
    @NotEmpty
    @Schema(description = "Give Password")
    private String password;
}
