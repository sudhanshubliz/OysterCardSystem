import gov.tfl.constant.GlobalConstant;
import gov.tfl.model.CalculateFare;
import gov.tfl.model.Station;
import gov.tfl.model.UserOysterCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OysterCardTest {

    CalculateFare calculateFare = new CalculateFare();

    // Test case 1: Sufficient balance for a tube journey
    @Test
    public void testTubeTravel() throws Exception {
        UserOysterCard testCard1 = new UserOysterCard(5.0);
        Station holborn = new Station(GlobalConstant.HOLBORN, List.of(1));
        Station earlCourt = new Station(GlobalConstant.EARL_COURT, List.of(1));
        double amount = calculateFare.calculateTotalFare(GlobalConstant.TUBE, holborn, earlCourt);
        testCard1.deductAmount(amount);
        System.out.println("Test 1 passed: Balance after tube journey: £" + testCard1.getBalance() + "\nDeducted Amount: £" + amount);
        Assertions.assertEquals(2.0, testCard1.getBalance());
    }


    // Test case 2: Insufficient balance for a journey
    @Test
    public void testTravelForInsuffcientBalance() throws Exception {
        UserOysterCard testCard2 = new UserOysterCard(1.0);
        Station holborn = new Station(GlobalConstant.HOLBORN, List.of(1));
        Station earlCourt = new Station(GlobalConstant.EARL_COURT, List.of(1, 2));
        try {
            testCard2.deductAmount(calculateFare.calculateTotalFare(GlobalConstant.TUBE, holborn, earlCourt));
            System.out.println("Test 2 failed: Insufficient balance not handled.");
        } catch (Exception e) {
            System.out.println("Test 2 Error: " + e.getMessage());
        }

    }

    // Test case 3: Epping to Holborn (6 ->1)
    @Test
    public void testTravelForEppingToHolborn() throws Exception {
        UserOysterCard testCard3 = new UserOysterCard(30);
        Station epping = new Station(GlobalConstant.EPPING, List.of(6));
        Station holbornLast = new Station(GlobalConstant.HOLBORN, List.of(1));

        try {
            double amount = calculateFare.calculateTotalFare(GlobalConstant.TUBE, epping, holbornLast);
            testCard3.deductAmount(amount);
            System.out.println("Test 3 : Result £" + testCard3.getBalance() +" Deduct Amount £"+amount);
        } catch (Exception e) {
            System.out.println("Test 3 Error: " + e.getMessage());
        }
    }


    // Test case 4: Bus journey
    @Test
    public void testTravelForBus() throws Exception {
        UserOysterCard testCard4 = new UserOysterCard(10.0);
        double amount = calculateFare.calculateTotalFare(GlobalConstant.BUS, null, null);
        testCard4.deductAmount(amount);
        Assertions.assertEquals(8.2,testCard4.getBalance());

        System.out.println("Test 4 passed: Balance after bus journey: £" + testCard4.getBalance() +" Deduct Amount £"+amount);

    }

    @Test
    public void testTravelForHemmisTOWimblendon() throws Exception {

        UserOysterCard testCard6 = new UserOysterCard(10.0);
        Station hemmis = new Station(GlobalConstant.HAMMERSMITH, List.of(2));
        Station wimblendon = new Station(GlobalConstant.WIMBELDON, List.of(3));

        double amount = calculateFare.calculateTotalFare(GlobalConstant.TUBE, hemmis, wimblendon);
        testCard6.deductAmount(amount);
        System.out.println("Test 6 passed: Wimblendon: £" + testCard6.getBalance() + "\nDeduct Amount £" + amount);
    }

    //Test Case 5: Swiping in at Wimbledon but not out  3 – in – Max fare
   @Test
   public void testTravelForWimbledonNotTap() throws Exception {
        try {
            UserOysterCard testCard5 = new UserOysterCard(10);
            Station wimblendon = new Station(GlobalConstant.WIMBELDON, List.of(3));
            double amount = calculateFare.calculateTotalFare(GlobalConstant.TUBE, wimblendon, null);
            testCard5.deductAmount(amount);
            System.out.println("Test 5 passed:Max fare Applied: £" + testCard5.getBalance() +"\nDeduct Amount £"+amount);
            Assertions.assertEquals(6.8, testCard5.getBalance());

        } catch (Exception e) {
            System.out.println("Test 5 Error: " + e.getMessage());
        }
    }
}
