package ru.fedusiv.files_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fedusiv.files_server.entities.City;
import ru.fedusiv.files_server.repositories.CitiesRepository;

import java.util.List;

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
    public ResponseEntity<City> getById(@PathVariable("id") Long id) {
        City city = citiesRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new ResponseEntity<>(citiesRepository.findById(id).orElseThrow(IllegalArgumentException::new),
                        HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public List<City> getAll() {
        return citiesRepository.findAll();
    }

}
