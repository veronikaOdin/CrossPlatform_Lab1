package task2;

public class SmartGadget {
    // Робимо різні типи полів, щоб було що аналізувати
    private String model = "CyberPad 2026";
    private double batteryLevel = 85.5;
    public int memory = 512;

    public SmartGadget() {}

    // Метод без параметрів (його можна буде викликати)
    public void turnOn() {
        System.out.println("Гаджет увімкнено. Системи в нормі!");
    }

    // Метод без параметрів (повертає значення)
    public double checkBattery() {
        return batteryLevel;
    }

    // Метод з параметром (програма його покаже, але не дасть викликати за умовою)
    public void charge(double amount) {
        batteryLevel += amount;
    }
}