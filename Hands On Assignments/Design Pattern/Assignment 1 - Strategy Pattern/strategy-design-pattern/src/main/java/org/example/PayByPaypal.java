package org.example;

public class PayByPaypal implements PaymentStrategy{
    @Override
    public void pay(Double amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}
