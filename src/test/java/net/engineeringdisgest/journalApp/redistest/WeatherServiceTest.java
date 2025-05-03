package net.engineeringdisgest.journalApp.redistest;

import net.engineeringdisgest.journalApp.controller.JournalUserController;
import net.engineeringdisgest.journalApp.service.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private JournalUserController userController;

    @Disabled
    @Test
    public void getAPI() {
        Assertions.assertNotNull(weatherService.getWeather("Ranchi"));
    }

    @Disabled
    @Test
    public void userWeatherAPI() {
        Assertions.assertNotNull(userController.greeting());
    }
}
