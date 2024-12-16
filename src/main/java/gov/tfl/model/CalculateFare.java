package gov.tfl.model;

import gov.tfl.constant.GlobalConstant;
import gov.tfl.interfac.FareInterface;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculateFare implements FareInterface {

    private static final double MAX_FARE = 3.20;
    private static final double BUS_FARE = 1.80;
    private static final Map<Set<Integer>, Double> TUBE_FARES = Map.ofEntries(
            Map.entry(Set.of(1), 2.50),  // Anywhere in Zone 1
            Map.entry(Set.of(2), 2.00),  // Any one zone outside Zone 1
            Map.entry(Set.of(1, 2), 3.00),  // Two zones including Zone 1
            Map.entry(Set.of(2, 3), 2.25),  // Two zones excluding Zone 1
            Map.entry(Set.of(1, 2, 3), 3.20),  // Three zones


            Map.entry(Set.of(1, 2, 3, 4), 4.20),  // Four zones
            Map.entry(Set.of(1, 2, 3, 4, 5), 5.20),  // Five zones
            Map.entry(Set.of(1, 2, 3, 4, 5, 6), 6.20)  // Six zones
    );

    public  double calculateTubeFare(List<Integer> startZones, List<Integer> endZones) {
        Set<Integer> zonesCrossed = new HashSet<>(startZones);
        zonesCrossed.addAll(endZones);
        return TUBE_FARES.getOrDefault(zonesCrossed, MAX_FARE);
    }

    public double calculateBusFare(){
        return BUS_FARE;
    }

    public  double calculateTotalFare(String journeyType, Station startStation, Station endStation) {
        if (journeyType.equals(GlobalConstant.BUS)) {
            return calculateBusFare();
        } else if (journeyType.equals(GlobalConstant.TUBE) &&
                (startStation!=null && endStation!=null)) {
            return calculateTubeFare(startStation.getZones(), endStation.getZones());
        }
        return MAX_FARE;
    }

}
