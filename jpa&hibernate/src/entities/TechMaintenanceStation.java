package ru.fedusiv.files_server.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tech_maintenance_stations")
public class TechMaintenanceStation {

    @Id
    @SequenceGenerator(
            name = "id_seq",
            sequenceName = "pk_tms_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "id_seq")
    private Long id;

    private String address;

    @ManyToMany(mappedBy = "techMaintenanceStations")
    private Set<VehicleService> vehicleServices;

}
