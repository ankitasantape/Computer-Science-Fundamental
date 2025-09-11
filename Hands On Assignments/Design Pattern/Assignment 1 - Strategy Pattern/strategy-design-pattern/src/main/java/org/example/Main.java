package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        PaymentProcessor processor = new PaymentProcessor();

        processor.setPaymentStrategy(new PayByCreditCard());
        processor.processPayment(1000); // Pay with Credit Card

        processor.setPaymentStrategy(new PayByPaypal());
        processor.processPayment(500); // pay with Paypal

        processor.setPaymentStrategy(new PayByPaypal());
        processor.processPayment(250); // pay with UPI

    }
}