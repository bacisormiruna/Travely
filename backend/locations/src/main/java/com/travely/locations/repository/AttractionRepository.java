package com.travely.locations.repository;

import com.travely.locations.attraction.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttractionRepository extends JpaRepository<Attraction, Long>,
        JpaSpecificationExecutor<Attraction> {

    @EntityGraph(attributePaths = "location")
    Page<Attraction> findAll(Specification<Attraction> spec, Pageable pageable);

    boolean existsByLocationId(Long locationId);
}
