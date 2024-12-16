package gov.tfl.interfac;


import gov.tfl.model.Station;

import java.util.List;

public interface FareInterface {

    public double calculateTubeFare(List<Integer> startZones, List<Integer> endZones);
    public double calculateBusFare();
    public  double calculateTotalFare(String journeyType, Station startStation, Station endStation);
}
