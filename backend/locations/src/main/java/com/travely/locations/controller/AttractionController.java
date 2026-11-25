package com.travely.locations.controller;

import com.travely.locations.attraction.AttractionRequest;
import com.travely.locations.attraction.AttractionResponse;
import com.travely.locations.attraction.Category;
import com.travely.locations.common.PageResponse;
import com.travely.locations.service.AttractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.net.URI;

@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public PageResponse<AttractionResponse> search(
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String name,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return attractionService.search(locationId, category, name, pageable);
    }

    @GetMapping("/{id}")
    public AttractionResponse findById(@PathVariable Long id) {
        return attractionService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AttractionResponse> create(@Valid @RequestBody AttractionRequest request) {
        AttractionResponse created = attractionService.create(request);
        return ResponseEntity
                .created(URI.create("/api/attractions/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public AttractionResponse update(@PathVariable Long id, @Valid @RequestBody AttractionRequest request) {
        return attractionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        attractionService.delete(id);
    }
}
