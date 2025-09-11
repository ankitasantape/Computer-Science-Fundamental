package org.example;

public class PayByCreditCard implements PaymentStrategy{
    @Override
    public void pay(Double amount) {
        System.out.println("Paid" + amount + " using Credit Card");
    }
}
