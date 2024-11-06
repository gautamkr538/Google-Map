package com.googlemap.googlemaplocation.Controller;

import com.googlemap.googlemaplocation.DTO.DirectionResponse;
import com.googlemap.googlemaplocation.DTO.LocationResponse;
import com.googlemap.googlemaplocation.Service.GoogleMapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location")
public class GoogleMapController {

    private final GoogleMapService googleMapsService;
    public GoogleMapController(GoogleMapService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @Operation(summary = "Get the user's live location", description = "Fetches the current live location of the user based on geolocation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched live location"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/live-location")
    public ResponseEntity<LocationResponse.Location> getLiveLocation() {
        LocationResponse.Location location = googleMapsService.getLiveLocation();
        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get best route to a destination", description = "Provides the optimal route from the user's live location to the specified destination address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched route to destination"),
            @ApiResponse(responseCode = "404", description = "Route not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/destination-route")
    public ResponseEntity<DirectionResponse.Route> getRoute(
            @Parameter(description = "Destination address to reach", required = true)
            @RequestParam String destination) {
        LocationResponse.Location liveLocation = googleMapsService.getLiveLocation();
        DirectionResponse.Route route = googleMapsService.getBestRoute(liveLocation, destination);
        return ResponseEntity.ok(route);
    }
}


