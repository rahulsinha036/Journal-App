package net.engineeringdisgest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdisgest.journalApp.dto.JournalEntryDTO;
import net.engineeringdisgest.journalApp.entity.JournalEntry;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.service.JournalEntryService;
import net.engineeringdisgest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs", description = "Read, Update, Delete")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

//    @GetMapping("{userName}")
//    public ResponseEntity<?> getALlJournalEntriesOfUser(@PathVariable String userName) {
//        UserEntry userEntry = userService.findByUserName(userName);
//        List<JournalEntry> journalEntryList = userEntry.getJournalEntry();
//        if (journalEntryList != null && !journalEntryList.isEmpty()) {
//            return new ResponseEntity<>(journalEntryList, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    // Using Auth --> spring security
    @GetMapping
    @Operation(summary = "Get all journals entries of a user")
    public ResponseEntity<?> getALlJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry userEntry = userService.findByUserName(userName);
        List<JournalEntry> journalEntryList = userEntry.getJournalEntry();
        if (journalEntryList != null && !journalEntryList.isEmpty()) {
            return new ResponseEntity<>(journalEntryList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



//    @PostMapping
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userName = authentication.getName();
//            journalEntryService.savedEntry(myEntry, userName);
//            return new ResponseEntity<>(myEntry, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntryDTO journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntry myEntry = new JournalEntry();
            myEntry.setTitle(journalEntry.getTitle());
            myEntry.setContent(journalEntry.getContent());
            myEntry.setSentiment(journalEntry.getSentiment());
            journalEntryService.savedEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/id/{myid}")
//    public ResponseEntity<JournalEntry> getJournalEntryId(@PathVariable ObjectId myid ) {
////        return journalEntryService.getByID(myid).orElse(null);
//        Optional<JournalEntry> journalEntry = journalEntryService.getByID(myid);
////        if(journalEntry.isPresent()) {
////            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
//        // lambda exp
//        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // Using auth --> spring security
    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalEntryId(@PathVariable String myid) {
        ObjectId objectId = new ObjectId(myid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getByID(objectId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


//    @DeleteMapping("/delete/{userName}/{myid}")
//    public ResponseEntity<?> deleteJournalEntryId(@PathVariable ObjectId myid, @PathVariable String userName) {
//        journalEntryService.deleteById(myid, userName);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping("/delete/{myid}")
    public ResponseEntity<?> deleteJournalEntryId(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(myid, userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("put/{userName}/{myid}")
//    public ResponseEntity<?> updateJournalEntryId(
//            @PathVariable ObjectId myid,
//            @RequestBody JournalEntry newEntry,
//            @PathVariable String userName
//    ) {
//        JournalEntry old = journalEntryService.getByID(myid).orElse(null);
//
//        if(old != null) {
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
//            journalEntryService.savedEntry(old);
//            return new ResponseEntity<>(old, HttpStatus.OK);
//        }
////
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PutMapping("put/{myid}")
    public ResponseEntity<?> updateJournalEntryId(
            @PathVariable ObjectId myid,
            @RequestBody JournalEntry newEntry
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getByID(myid);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                journalEntryService.savedEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
