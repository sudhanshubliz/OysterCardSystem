package gov.tfl.model;

import gov.tfl.interfac.UserAmountInterface;

public class UserOysterCard implements UserAmountInterface {
    private double balance;

    public UserOysterCard(double initialBalance) {
        this.balance = initialBalance;
    }

    public void loadAmount(double amount) {
        balance = balance + amount;
    }

    public void deductAmount(double amount) throws Exception {
        synchronized (this){
            if (balance >= amount) {
                balance = balance - amount;
            } else {
                throw new Exception("Insufficient balance!");
            }
        }
    }

    public void refundAmount(double amount) {
        balance = balance + amount;
    }

    public double getBalance() {
        return balance;
    }

}
