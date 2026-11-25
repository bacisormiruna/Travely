package com.travely.locations.repository;

import com.travely.locations.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsByNameIgnoreCaseAndCountryIgnoreCase(String name, String country);

    boolean existsByNameIgnoreCaseAndCountryIgnoreCaseAndIdNot(String name, String country, Long id);
}
