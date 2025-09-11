package org.example;

public class PaymentProcessor {
    private PaymentStrategy strategy;

//    public PaymentProcessor(PayByCreditCard payByCreditCard) {
//    }

    public void setPaymentStrategy(PaymentStrategy strategy){
            this.strategy = strategy;
    }

    public void processPayment(double amount){
        if (strategy == null){
            throw new IllegalStateException("Payment strategy not set!");
        }
        strategy.pay(amount);
    }


}
