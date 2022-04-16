package ru.fedusiv.files_server.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "public_transport_department")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class VehicleService {

    @Id
    @SequenceGenerator(
            name = "id_seq",
            sequenceName = "pk_ptd_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    @JsonIgnore
    private City city;

    @Column(name = "vehicles_amount")
    private String vehiclesAmount;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        SUBWAY, TRAM, BUS, TROLLEYBUS
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vehicle_services_to_tm_stations",
            joinColumns = @JoinColumn(name = "vh_service_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tm_station_id", referencedColumnName = "id")
    )
    private List<TechMaintenanceStation> techMaintenanceStations;

}
