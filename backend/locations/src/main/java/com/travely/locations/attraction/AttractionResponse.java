package com.travely.locations.attraction;

import java.math.BigDecimal;

public record AttractionResponse(
        Long id,
        String name,
        String description,
        Category category,
        Long locationId,
        String locationName,
        Double latitude,
        Double longitude,
        BigDecimal entryPrice,
        boolean hasAudio
) {
    public static AttractionResponse from(Attraction attraction) {
        return new AttractionResponse(
                attraction.getId(),
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCategory(),
                attraction.getLocation().getId(),
                attraction.getLocation().getName(),
                attraction.getLatitude(),
                attraction.getLongitude(),
                attraction.getEntryPrice(),
                attraction.getAudioFilePath() != null
        );
    }
}
