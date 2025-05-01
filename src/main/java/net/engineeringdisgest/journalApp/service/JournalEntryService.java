package net.engineeringdisgest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdisgest.journalApp.entity.JournalEntry;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalIEntryRepository;

    @Autowired
    private UserService userService;

//    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void savedEntry(JournalEntry journalEntry, String userName) {
        try {
            UserEntry userEntry = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalIEntryRepository.save(journalEntry);
            userEntry.getJournalEntry().add(saved);
            userService.saveUser(userEntry);
        } catch (Exception e) {
            log.error("Error in {} ", e.getMessage());
            throw new RuntimeException("An error occurred while saving the entry ", e);
        }
    }

    // Overload Method
    public void savedEntry(JournalEntry journalEntry) {
        journalIEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalIEntryRepository.findAll();
    }

    public Optional<JournalEntry> getByID(ObjectId Id) {
        return journalIEntryRepository.findById(Id);
    }

    @Transactional
    public boolean deleteById(ObjectId Id, String userName) {
        boolean removed = false;
        try {
            UserEntry userEntry = userService.findByUserName(userName);
            removed = userEntry.getJournalEntry().removeIf(x -> x.getId().equals(Id));
            if (removed) {
                userService.saveUser(userEntry);
                journalIEntryRepository.deleteById(Id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the entry " + e);
        }
        return removed;
    }

//    public List<JournalEntry> findByUserName(String userName) {
//
//    }

}
