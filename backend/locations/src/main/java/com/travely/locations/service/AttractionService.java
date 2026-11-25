package com.travely.locations.service;

import com.travely.locations.attraction.*;
import com.travely.locations.common.PageResponse;
import com.travely.locations.exceptions.ResourceNotFoundException;
import com.travely.locations.location.Location;
import com.travely.locations.repository.AttractionRepository;
import com.travely.locations.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public PageResponse<AttractionResponse> search(Long locationId, Category category, String name, Pageable pageable) {
        var spec = AttractionSpecifications.withFilters(locationId, category, name);
        var page = attractionRepository.findAll(spec, pageable).map(AttractionResponse::from);
        return PageResponse.from(page);
    }

    @Transactional(readOnly = true)
    public AttractionResponse findById(Long id) {
        return AttractionResponse.from(getAttractionOrThrow(id));
    }

    @Transactional
    public AttractionResponse create(AttractionRequest request) {
        Attraction attraction = new Attraction();
        applyRequest(attraction, request);
        return AttractionResponse.from(attractionRepository.save(attraction));
    }

    @Transactional
    public AttractionResponse update(Long id, AttractionRequest request) {
        Attraction attraction = getAttractionOrThrow(id);
        applyRequest(attraction, request);
        return AttractionResponse.from(attraction);
    }

    @Transactional
    public void delete(Long id) {
        if (!attractionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Attraction with id %d not found".formatted(id));
        }
        attractionRepository.deleteById(id);
    }

    private Attraction getAttractionOrThrow(Long id) {
        return attractionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attraction with id %d not found".formatted(id)));
    }

    private Location getLocationOrThrow(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id %d not found".formatted(locationId)));
    }

    private void applyRequest(Attraction attraction, AttractionRequest request) {
        attraction.setName(request.name().trim());
        attraction.setDescription(request.description().trim());
        attraction.setCategory(request.category());
        attraction.setLocation(getLocationOrThrow(request.locationId()));
        attraction.setLatitude(request.latitude());
        attraction.setLongitude(request.longitude());
        attraction.setEntryPrice(request.entryPrice());
    }
}
