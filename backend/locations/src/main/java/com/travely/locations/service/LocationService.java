package com.travely.locations.service;

import com.travely.locations.exceptions.ConflictException;
import com.travely.locations.exceptions.ResourceNotFoundException;
import com.travely.locations.location.Location;
import com.travely.locations.location.LocationRequest;
import com.travely.locations.location.LocationResponse;
import com.travely.locations.repository.AttractionRepository;
import com.travely.locations.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final AttractionRepository attractionRepository;

    @Transactional(readOnly = true)
    public List<LocationResponse> findAll() {
        return locationRepository.findAll(Sort.by("name")).stream()
                .map(LocationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public LocationResponse findById(Long id) {
        return LocationResponse.from(getLocationOrThrow(id));
    }

    @Transactional
    public LocationResponse create(LocationRequest request) {
        if (locationRepository.existsByNameIgnoreCaseAndCountryIgnoreCase(request.name(), request.country())) {
            throw new ConflictException("Location '%s, %s' already exists".formatted(request.name(), request.country()));
        }

        Location location = new Location();
        applyRequest(location, request);

        return LocationResponse.from(locationRepository.save(location));
    }

    @Transactional
    public LocationResponse update(Long id, LocationRequest request) {
        Location location = getLocationOrThrow(id);

        if (locationRepository.existsByNameIgnoreCaseAndCountryIgnoreCaseAndIdNot(request.name(), request.country(), id)) {
            throw new ConflictException("Location '%s, %s' already exists".formatted(request.name(), request.country()));
        }

        applyRequest(location, request);
        return LocationResponse.from(location);
    }

    @Transactional
    public void delete(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location with id %d not found".formatted(id));
        }
        if (attractionRepository.existsByLocationId(id)) {
            throw new ConflictException("Location has attractions and cannot be deleted");
        }
        locationRepository.deleteById(id);
    }

    private Location getLocationOrThrow(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id %d not found".formatted(id)));
    }

    private void applyRequest(Location location, LocationRequest request) {
        location.setName(request.name().trim());
        location.setCountry(request.country().trim());
        location.setLatitude(request.latitude());
        location.setLongitude(request.longitude());
    }
}
