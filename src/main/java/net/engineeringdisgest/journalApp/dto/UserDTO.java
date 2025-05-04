package net.engineeringdisgest.journalApp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty
    @Schema(description = "Give Username")
    private String userName;
    @Schema(description = "Give Email (Optional)")
    private String email;
    @Schema(description = "Give Sentiment (Optional)")
    private boolean sentimentAnalysis;
    @NotEmpty
    @Schema(description = "Give Password")
    private String password;

}
