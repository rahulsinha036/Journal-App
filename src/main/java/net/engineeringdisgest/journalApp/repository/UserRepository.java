package net.engineeringdisgest.journalApp.repository;

import net.engineeringdisgest.journalApp.entity.JournalEntry;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntry, ObjectId> {

    UserEntry findByUserName(String username);

    void deleteByUserName(String name);
}
