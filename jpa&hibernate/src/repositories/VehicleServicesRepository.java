package ru.fedusiv.files_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fedusiv.files_server.entities.VehicleService;

@Repository
public interface VehicleServicesRepository extends JpaRepository<VehicleService, Long> {
}
