package ru.fedusiv.files_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.fedusiv.files_server.entities.VehicleService;
import ru.fedusiv.files_server.repositories.VehicleServicesRepository;

@RestController
public class VehiclesServicesController {

    @Autowired
    private VehicleServicesRepository vehicleServicesRepository;

    @GetMapping("/vh/get/{id}")
    public VehicleService getById(@PathVariable("id") Long id) {

        VehicleService vh = vehicleServicesRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return vehicleServicesRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }


}
