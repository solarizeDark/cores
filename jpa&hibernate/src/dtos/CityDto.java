package ru.fedusiv.files_server.dtos;

import lombok.*;
import ru.fedusiv.files_server.entities.City;
import ru.fedusiv.files_server.entities.CityHall;
import ru.fedusiv.files_server.entities.Mayor;
import ru.fedusiv.files_server.entities.VehicleService;
import ru.fedusiv.files_server.entities.converters.CityHallConverter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDto {

    private String name;
    private String country;
    private Integer population;
    private Set<VehicleService> vehicleServices;

    public static CityDto of(City city) {
        return CityDto.builder()
                .name(city.getName())
                .country(city.getCountry())
                .population(city.getPopulation())
                .vehicleServices(city.getVehicleServices())
                .build();
    }

    public static List<CityDto> of(List<City> cities) {
        return cities.stream().map(CityDto::of).collect(Collectors.toList());
    }

}
