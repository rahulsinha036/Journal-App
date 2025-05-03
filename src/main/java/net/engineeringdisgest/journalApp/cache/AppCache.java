package net.engineeringdisgest.journalApp.cache;

import jakarta.annotation.PostConstruct;
import net.engineeringdisgest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdisgest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache; // it will work as in-memory cache

    @PostConstruct
    public void init() {
        appCache = new HashMap<>(); // reintalizise the map whenever we update the mongo db otherwise it make two different keys of same name;
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
