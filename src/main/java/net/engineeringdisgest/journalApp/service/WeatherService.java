package net.engineeringdisgest.journalApp.service;

import net.engineeringdisgest.journalApp.api.response.WeatherResponse;
import net.engineeringdisgest.journalApp.cache.AppCache;
import net.engineeringdisgest.journalApp.constants.Placeholders;
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

    // Intead of using hardcode api, we will AppCache
//    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate; // It is a class in spring which process http request and return us the response

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
//        String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<city>", city).replace("<apikey>", apiKeyWeather);
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
//            String finalAPI = API.replace("API_KEY", apiKeyWeather).replace("CITY", city);
            String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apiKeyWeather).replace(Placeholders.CITY, city);
            int c = 0;
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);// Deserialize the JSON to POJO format
            // Here we can also add try catch block and Http status code to check the response created or not
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 6000L);
            }
            return body;
        }
    }
}
