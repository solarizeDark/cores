package ru.fedusiv.files_server.dtos;

import lombok.Builder;
import lombok.Data;
import ru.fedusiv.files_server.entities.City;
import ru.fedusiv.files_server.entities.VehicleService;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class VehicleServiceDto {

    private Long id;
    private City city;
    private String vehiclesAmount;
    private VehicleService.Type type;
    private List<TechMaintenanceStationDto> techMaintenanceStationDtos;

    public static VehicleServiceDto of(VehicleService vehicleService) {

        return VehicleServiceDto.builder()
                .id(vehicleService.getId())
                .city(vehicleService.getCity())
                .techMaintenanceStationDtos(
                        TechMaintenanceStationDto.of(
                                vehicleService.getTechMaintenanceStations()))
                .type(vehicleService.getType())
                .vehiclesAmount(vehicleService.getVehiclesAmount())
                .build();

    }

    public static List<VehicleServiceDto> of(List<VehicleService> vehicleServices) {
        return vehicleServices.stream().map(VehicleServiceDto::of).collect(Collectors.toList());
    }

}
