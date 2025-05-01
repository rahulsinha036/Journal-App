package net.engineeringdisgest.journalApp.controller;

import net.engineeringdisgest.journalApp.api.response.WeatherResponse;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.repository.UserRepository;
import net.engineeringdisgest.journalApp.service.UserService;
import net.engineeringdisgest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class JournalUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;



//    @GetMapping
//    public List<UserEntry> getAllUsers() {
//        return userService.getAll();
//    }

//    @PostMapping
//    public void createUser(@RequestBody UserEntry userEntry) {
//        userService.savedEntry(userEntry);
//    }

//    @PutMapping("/{userName}")
//    public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry, @PathVariable String userName) {
//        UserEntry userInDB = userService.findByUserName(userName);
//        if(userInDB != null) {
//            userInDB.setUserName(userEntry.getUserName());
//            userInDB.setPassword(userEntry.getPassword());
//            userService.savedEntry(userInDB);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    // using auth --> spring secutity demo
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntry userEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        // from here it will check whether the user is present in db or not
        // it will verify hashcode pressent in the db of the password
        UserEntry userInDB = userService.findByUserName(userName);
        userInDB.setUserName(userEntry.getUserName());
        userInDB.setPassword(userEntry.getPassword());
        userService.savedNewUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserEntry userEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }

}
