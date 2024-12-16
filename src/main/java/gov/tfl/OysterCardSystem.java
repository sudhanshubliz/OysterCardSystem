package gov.tfl;


import gov.tfl.constant.GlobalConstant;
import gov.tfl.model.Station;
import gov.tfl.model.CalculateFare;
import gov.tfl.model.UserOysterCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class OysterCardSystem {

    static Logger logger = Logger.getLogger(OysterCardSystem.class.getName());

    public static void main(String[] args) {

        // Initialize stations
        Map<String, Station> stations = new HashMap<>();
        stations.put(GlobalConstant.HOLBORN, new Station(GlobalConstant.HOLBORN, List.of(1)));
        stations.put(GlobalConstant.EARL_COURT, new Station(GlobalConstant.EARL_COURT, List.of(1, 2)));
        stations.put(GlobalConstant.HAMMERSMITH, new Station(GlobalConstant.HAMMERSMITH, List.of(2)));
        stations.put(GlobalConstant.CHELSEA, new Station(GlobalConstant.CHELSEA, List.of())); // Bus-only stop

        stations.put(GlobalConstant.WIMBELDON, new Station(GlobalConstant.WIMBELDON, List.of(3)));
        stations.put(GlobalConstant.EPPING, new Station(GlobalConstant.EPPING, List.of(6)));

        UserOysterCard card = new UserOysterCard(30.0);


        // Trips
        List<Map<String, String>> trips = List.of(
                Map.of("start", GlobalConstant.HOLBORN, "end", GlobalConstant.EARL_COURT, "type", "tube"),
                Map.of("start", GlobalConstant.EARL_COURT, "end", GlobalConstant.CHELSEA, "type", "bus"),
                Map.of("start", GlobalConstant.EARL_COURT, "end", GlobalConstant.HAMMERSMITH, "type", "tube"),
                Map.of("start", GlobalConstant.EARL_COURT, "end", GlobalConstant.EPPING, "type", "tube"),  // New scenario
                Map.of("start", GlobalConstant.EPPING, "end", GlobalConstant.HOLBORN, "type", "tube"),  // New scenario
                Map.of("start", GlobalConstant.WIMBELDON, "end", "", "type", "tube")  // Swipe in without swipe out
        );

        CalculateFare calculateFare = new CalculateFare();

        // Process trips
        for (Map<String, String> trip : trips) {
            String journeyType = trip.get("type");
            Station startStation = stations.get(trip.get("start"));
            Station endStation = stations.get(trip.get("end"));

            try {
                if (journeyType.equals(GlobalConstant.TUBE)) {
                    logger.info("Starting journey from " + startStation.getName() + " to " + endStation.getName() + " (Tube).");
                    card.deductAmount(calculateFare.calculateTotalFare("tube", startStation, endStation));

                    logger.info("Journey complete. Remaining balance: £" + card.getBalance());
                } else if (journeyType.equals(GlobalConstant.BUS)) {
                    logger.info("Starting bus journey to " + endStation.getName() + ".");
                    card.deductAmount(calculateFare.calculateTotalFare("bus", null, null));
                    logger.info("Bus journey complete. Remaining balance: £" + card.getBalance());
                } else {
                    logger.info("Unknown journey type: " + journeyType);
                }
            } catch (Exception e) {
                logger.severe("Error: " + e.getMessage());
            }
        }

        // Output final balance
        System.out.printf("Final balance: £%.2f%n", card.getBalance());

    }



}
