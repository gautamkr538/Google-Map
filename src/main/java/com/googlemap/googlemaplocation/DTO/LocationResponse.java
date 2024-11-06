package com.googlemap.googlemaplocation.DTO;

import lombok.Data;

@Data
public class LocationResponse {

    private Location location;

    @Data
    public static class Location {
        private double lat;
        private double lng;
    }
}
