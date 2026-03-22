package task3;

public class SimpleCalculator {
    private double a = 1.0;

    // Метод 1: рахує формулу
    public double calculate(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }

    // Метод 2: просто додає два числа (щоб перевірити різні типи параметрів)
    public double add(double a, int b) {
        return a + b;
    }
}