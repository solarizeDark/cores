package ru.fedusiv.files_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.fedusiv.files_server.entities.City;
import ru.fedusiv.files_server.repositories.CitiesRepository;

@RestController
public class CitiesController {

    @Autowired
    private CitiesRepository citiesRepository;

    @PostMapping("/save")
    public City save(@RequestBody City city) {
        city = citiesRepository.save(city);
        return city;
    }

    @GetMapping("/get/{id}")
    public City getById(@PathVariable("id") Long id) {
        return citiesRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

}
