package net.engineeringdisgest.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder // it creates a private constructor
@NoArgsConstructor
@AllArgsConstructor
public class UserEntry {
    @Id
    private ObjectId id;
    @Indexed(unique = true) // by default indexing is not done, we have to set a property in app.properties file
    @NonNull
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef // Parent-Child relationship established here.
    private List<JournalEntry> journalEntry = new ArrayList<>(); // new ArrayList<>() --> whenever new user is created there will journalEntry list is empty created instead of null
    private List<String> roles; // Authorization --> after login what users can do
}
