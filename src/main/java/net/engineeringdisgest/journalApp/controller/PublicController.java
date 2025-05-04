package net.engineeringdisgest.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdisgest.journalApp.dto.UserDTO;
import net.engineeringdisgest.journalApp.dto.UserLoginDTO;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdisgest.journalApp.service.UserService;
import net.engineeringdisgest.journalApp.utilis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs", description = "Read, Update, Delete")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

//    @PostMapping("/signup")
//    public void signup(@RequestBody UserEntry userEntry) {
//        userService.savedNewUser(userEntry);
//    }

    // using DTO to call the signup
    @PostMapping("/signup")
    public void signup(@RequestBody UserDTO user) {
        // Creating obj of UserEntry and using UserDTO to get all the information and setting in userEntry
        UserEntry newUser = new UserEntry();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.savedNewUser(newUser);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserEntry userEntry) {
//        try {
//            // It will first authenticate using UsernamePasswordAuthenticationToken and then generate a token
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntry.getUserName(), userEntry.getPassword()));
//            UserDetails userDetails = userDetailsService.loadUserByUsername(userEntry.getUserName());
//            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
//            int a = 1;
//            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Exception occurred while createAuthenticationToken ", e);
//            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
        try {
            // It will first authenticate using UsernamePasswordAuthenticationToken and then generate a token
            UserEntry userEntry = new UserEntry();
            userEntry.setUserName(user.getUserName());
            userEntry.setPassword(user.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntry.getUserName(), userEntry.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEntry.getUserName());
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            int a = 1;
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
