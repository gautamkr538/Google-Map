package com.googlemap.googlemaplocation.Service.GoogleMapServiceImpl;

import com.googlemap.googlemaplocation.DTO.DirectionResponse;
import com.googlemap.googlemaplocation.DTO.LocationResponse;
import com.googlemap.googlemaplocation.Exception.LocationNotFoundException;
import com.googlemap.googlemaplocation.Exception.RouteNotFoundException;
import com.googlemap.googlemaplocation.Service.GoogleMapService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GoogleMapServiceImpl implements GoogleMapService {

    private final RestTemplate restTemplate;
    public GoogleMapServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Value("${google.map.geolocation.api.key}")
    private String googleApiKey;
    private static final Logger log = LoggerFactory.getLogger(GoogleMapServiceImpl.class);
    private static final String GEOLOCATION_URL = "https://www.googleapis.com/geolocation/v1/geolocate?key=";
    private static final String DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json?";

    @Override
    public LocationResponse.Location getLiveLocation() {
        log.info("Fetching live location...");
        String url = GEOLOCATION_URL + googleApiKey;
        try {
            ResponseEntity<LocationResponse> response = restTemplate.postForEntity(url, null, LocationResponse.class);

            if (response.getBody() != null) {
                log.info("Live location fetched successfully: {}", response.getBody().getLocation());
                return response.getBody().getLocation();
            } else {
                throw new LocationNotFoundException("Empty response from geolocation API.");
            }
        } catch (Exception e) {
            log.error("Failed to fetch live location: {}", e.getMessage());
            throw new LocationNotFoundException("Unable to fetch live location.", e);
        }
    }

    @Override
    public DirectionResponse.Route getBestRoute(LocationResponse.Location origin, String destinationAddress) {
        log.info("Fetching best route from {} to {}", origin, destinationAddress);
        String url = DIRECTIONS_URL + "origin=" + origin.getLat() + "," + origin.getLng()
                + "&destination=" + destinationAddress + "&key=" + googleApiKey;
        try {
            ResponseEntity<DirectionResponse> response = restTemplate.getForEntity(url, DirectionResponse.class);

            if (response.getBody() != null && !response.getBody().getRoutes().isEmpty()) {
                log.info("Route fetched successfully.");
                return response.getBody().getRoutes().get(0);
            } else {
                throw new RouteNotFoundException("No route found.");
            }
        } catch (Exception e) {
            log.error("Failed to fetch route: {}", e.getMessage());
            throw new RouteNotFoundException("Could not find a route to the destination.", e);
        }
    }
}
