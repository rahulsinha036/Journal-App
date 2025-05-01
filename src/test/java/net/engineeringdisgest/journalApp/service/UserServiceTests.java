package net.engineeringdisgest.journalApp.service;

import net.engineeringdisgest.journalApp.entity.UserEntry;
import net.engineeringdisgest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// it will start the application context
//@ActiveProfiles("dev") --> it will check which bean of prod or dev and according to that it will run
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //@Disabled // it will disable the below method while testing
    //@Test
//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "rahulsinha036",
//            "Ajit",
//            "Kamal"
//    })
    // another way to pass value
//    @ValueSource(strings = {  // int -> we can give any data type according to value
//            "rahulsinha036",
//            "Ajit",
//            "Kamal"
//            })
//    public void testfindByUserName(String name) {
//        assertEquals(4, 2 + 2);
//        assertNotNull(userRepository.findByUserName("Ajit"));
        // assert basically means in hindi is daabba karna
//        assertTrue(5 > 3);
//        UserEntry rahulsinha036 = userRepository.findByUserName("rahulsinha036");
//        assertTrue(!rahulsinha036.getJournalEntry().isEmpty());
//        assertNotNull(userRepository.findByUserName(name), "Failed for " + name);
//    }

//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,2,3", // 1->a, 2->b, 3->expected
//            "4,4,8",
//            "3,3,9"
//    })
//    public void test(int a, int b, int expected) {
//        assertEquals(expected, a, b, "falied for " + expected);
//    }


    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(UserEntry name) {
        assertTrue(userService.savedNewUser(name));
    }
}
