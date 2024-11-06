package com.googlemap.googlemaplocation.DTO;

import lombok.Data;
import java.util.List;

@Data
public class DirectionResponse {
    private List<Route> routes;

    @Data
    public static class Route {
        private String summary;
        private List<Leg> legs;
    }

    @Data
    public static class Leg {
        private String start_address;
        private String end_address;
        private Duration duration;
        private Distance distance;
    }

    @Data
    public static class Duration {
        private String text;
        private int value;
    }

    @Data
    public static class Distance {
        private String text;
        private int value;
    }
}
