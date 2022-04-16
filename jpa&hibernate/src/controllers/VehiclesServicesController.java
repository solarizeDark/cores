package ru.fedusiv.files_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.fedusiv.files_server.dtos.TechMaintenanceStationDto;
import ru.fedusiv.files_server.dtos.VehicleServiceDto;
import ru.fedusiv.files_server.entities.TechMaintenanceStation;
import ru.fedusiv.files_server.entities.VehicleService;
import ru.fedusiv.files_server.repositories.VehicleServicesRepository;

import java.util.List;
import java.util.Set;

@RestController
public class VehiclesServicesController {

    @Autowired
    private VehicleServicesRepository vehicleServicesRepository;

    @GetMapping("/vh/get/{id}")
    public VehicleService getById(@PathVariable("id") Long id) {
        return vehicleServicesRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/vh/get/tms/{id}")
    public List<TechMaintenanceStationDto> tmStationsForVehicleServiceById(@PathVariable("id") Long id) {
        return VehicleServiceDto
                        .of(vehicleServicesRepository
                                .findById(id)
                                .orElseThrow(IllegalArgumentException::new)
                        ).getTechMaintenanceStationDtos();
    }


}
