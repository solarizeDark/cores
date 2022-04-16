package ru.fedusiv.files_server.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import ru.fedusiv.files_server.entities.converters.CityHallConverter;
import ru.fedusiv.files_server.entities.listeners.CityListener;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@DynamicInsert
@Table(name = "cities",
        uniqueConstraints = {
            @UniqueConstraint(name = "country_city_unique", columnNames = {"country", "name"})
        }
)
@EntityListeners(CityListener.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class City {

    @Id
    @SequenceGenerator(
            name = "id_seq",
            sequenceName = "pk_cities_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    //    @Basic(optional = false)
    @Column(columnDefinition = "varchar default 'Russia'")
    private String country;

    private Integer population;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride( name = "name", column = @Column(name = "mayor_name", nullable = false)),
        @AttributeOverride( name = "surname", column = @Column(name = "mayor_surname")),
    })
    @Column(unique = true)
    private Mayor mayor;

    @Convert(converter = CityHallConverter.class)
    @Column(unique = true)
    private CityHall cityHall;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "city",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("city")
    private Set<VehicleService> vehicleServices;

}
