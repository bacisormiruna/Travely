package com.travely.locations.location;

public record LocationResponse(
        Long id,
        String name,
        String country,
        Double latitude,
        Double longitude
) {
    public static LocationResponse from(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude()
        );
    }
}
