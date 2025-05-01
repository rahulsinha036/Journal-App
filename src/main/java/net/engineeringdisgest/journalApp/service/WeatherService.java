package net.engineeringdisgest.journalApp.service;

import net.engineeringdisgest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
//    private static final String apiKeyWeather = "698a2f682de6a83d10975";
    // Instead of hardcode api, we can put in yml file and use value annotation to use the API

    @Value("${weather.api.key}")
    private String apiKeyWeather;

    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate; // It is a class in spring which process http request and return us the response


    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKeyWeather);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);// Deserialize the JSON to POJO format
        // Here we can also add try catch block and Http status code to check the whether response created or not
        WeatherResponse body = response.getBody();
        return body;
    }
}
