package ru.fedusiv.files_server.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
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
    private List<VehicleService> vehicleServices;

}
