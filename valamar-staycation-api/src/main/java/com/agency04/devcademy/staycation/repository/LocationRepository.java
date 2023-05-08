package com.agency04.devcademy.staycation.repository;

import com.agency04.devcademy.staycation.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query("select l from Location l where l.country = :country")
    Optional<Location> findLocationByCountry(@Param("country") String country);

    Optional<Location> findByPostalCode(String postalCode);

}
