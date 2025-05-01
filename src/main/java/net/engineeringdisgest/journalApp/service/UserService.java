package net.engineeringdisgest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // We can use annotation slf4j
   // private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public boolean savedNewUser(UserEntry user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            // by default if any error comes--> error,warn and info will get printed in console
            log.error("Error occurred for {} : {}", user.getUserName(), e.getMessage());
            log.warn("Error is warn");
            log.info("Error is info");
            log.debug("Error is Debug");
            log.trace("Error is trace");


            // this is used we have to instizied Logger
            //            logger.warn("This is Warn Log");
//            logger.info("This is Info Log");
//            logger.debug("This is Debug Log");
//            logger.trace("This is Trace Log");
            return false;
        }

    }

    public void saveAdmin(UserEntry user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(UserEntry user) {
        userRepository.save(user);
    }

    public List<UserEntry> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntry> getByID(ObjectId Id) {
        return userRepository.findById(Id);
    }

    public void deleteById(ObjectId Id) {
        userRepository.deleteById(Id);
    }

    public UserEntry findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
