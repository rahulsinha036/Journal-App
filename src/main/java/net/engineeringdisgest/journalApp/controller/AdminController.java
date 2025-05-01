package net.engineeringdisgest.journalApp.controller;

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
}
