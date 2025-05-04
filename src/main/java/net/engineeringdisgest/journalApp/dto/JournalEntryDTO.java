package net.engineeringdisgest.journalApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.engineeringdisgest.journalApp.enums.Sentiment;

@Data
@NoArgsConstructor
public class JournalEntryDTO {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private Sentiment sentiment;

}
