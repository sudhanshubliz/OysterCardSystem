package gov.tfl.interfac;

public interface UserAmountInterface {

    public void loadAmount(double amount);
    public  void deductAmount(double amount) throws Exception;
    public void refundAmount(double amount);
}
