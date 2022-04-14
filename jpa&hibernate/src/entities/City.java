package ru.fedusiv.files_server.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import ru.fedusiv.files_server.entities.converters.CityHallConverter;
import ru.fedusiv.files_server.entities.listeners.CityListener;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@DynamicInsert
@Table(name = "cities")
@EntityListeners(CityListener.class)
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
    private Mayor mayor;

    @Convert(converter = CityHallConverter.class)
    private CityHall cityHall;

}
