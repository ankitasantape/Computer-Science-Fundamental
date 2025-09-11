package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Coffee order1 = new Espresso();
        order1 = new MilkDecorator(order1);
        order1 = new CaramelDecorator(order1);

        System.out.println("Order: " + order1.getDescription());
        System.out.println("Total Cost: $" + order1.getCost());

        System.out.println("--------------------------------------");

        // Order 2: Latte with Whipped Cream
        Coffee order2 = new Latte();
        order2 = new WhippedCreamDecorator(order2);

        System.out.println("Order: " + order2.getDescription());
        System.out.println("Total Cost: $" + order2.getCost());
    }
}