package net.engineeringdisgest.journalApp.controller;

import net.engineeringdisgest.journalApp.cache.AppCache;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/health-check")
    public String getHealthCheck() {
        return "OK";
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntry> allUsersList = userService.getAll();
        if (allUsersList != null && !allUsersList.isEmpty()) {
            return new ResponseEntity<>(allUsersList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody UserEntry userEntry) {
        userService.saveAdmin(userEntry);
    }

    // it will reload the init method when we have updated the something in mongodb while running
    // we just have to all it and again debug
    @GetMapping("/clear-cache")
    public void clearcahe() {
        appCache.init();
    }
    // Instead of manually clearing the cache we have used cron and schedulling to reload the appCache
}
