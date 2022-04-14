package ru.fedusiv.files_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fedusiv.files_server.entities.City;

@Repository
public interface CitiesRepository extends JpaRepository<City, Long> {


}
