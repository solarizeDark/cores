package ru.fedusiv.files_server.dtos;

import lombok.Builder;
import lombok.Data;
import ru.fedusiv.files_server.entities.TechMaintenanceStation;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class TechMaintenanceStationDto {

    private Long id;
    private String address;

    public static TechMaintenanceStationDto of(TechMaintenanceStation techMaintenanceStation) {
        return TechMaintenanceStationDto.builder()
                .id(techMaintenanceStation.getId())
                .address(techMaintenanceStation.getAddress())
                .build();
    }

    public static List<TechMaintenanceStationDto> of(List<TechMaintenanceStation> techMaintenanceStations) {
        return techMaintenanceStations.stream().map(TechMaintenanceStationDto::of).collect(Collectors.toList());
    }

}
