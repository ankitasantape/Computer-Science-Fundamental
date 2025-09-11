package org.example;

public class PayByUPI implements PaymentStrategy{
    @Override
    public void pay(Double amount) {
        System.out.println("Paid " + amount + " using UPI");
    }
}
