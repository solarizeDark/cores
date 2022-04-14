package ru.fedusiv.files_server.entities.listeners;

import lombok.extern.slf4j.Slf4j;
import ru.fedusiv.files_server.entities.City;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

@Slf4j
public class CityListener {

    @PrePersist
    public void logNewUser(City city) {
        log.info(String.format("New city: %s", city.getName()));
    }

    @PostPersist
    public void logPersistedUser(City city) {
        log.info(String.format("City persisted: %s, %s",
                city.getId(), city.getName()));
    }

}
