package com.googlemap.googlemaplocation.Service;

import com.googlemap.googlemaplocation.DTO.DirectionResponse;
import com.googlemap.googlemaplocation.DTO.LocationResponse;
import com.googlemap.googlemaplocation.Exception.RouteNotFoundException;

public interface GoogleMapService {
    /**
     * Fetches the current live location using the Google Geolocation API.
     *
     * @return the current location as a LocationResponse.Location object.
     */
    LocationResponse.Location getLiveLocation();

    /**
     * Fetches the best route from the given origin to the specified destination.
     *
     * @param origin the starting point of the route as a GoogleMapRecord.Location object.
     * @param destinationAddress the destination address as a string.
     * @return the best route as a DirectionResponse.Route object.
     * @throws RouteNotFoundException if no route can be found to the destination.
     */
    DirectionResponse.Route getBestRoute(LocationResponse.Location origin, String destinationAddress);

}
