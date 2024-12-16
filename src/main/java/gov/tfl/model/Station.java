package gov.tfl.model;

import java.util.List;

public class Station {

    private String name;
    private List<Integer> zones;

    public Station(String name, List<Integer> zones) {
        this.name = name;
        this.zones = zones;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getZones() {
        return zones;
    }
}
