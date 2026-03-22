package task5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

// Інтерфейс для математичних функцій
interface MathFunction {
    double calculate(double x);
}

// Перша проста функція
class SquareFunction implements MathFunction {
    public double calculate(double x) { return x * x; }
    public String toString() { return "x^2"; }
}

// Друга складна функція (з методички)
class ComplexFunction implements MathFunction {
    private double a = 2.5;
    public double calculate(double x) {
        return Math.exp(-Math.abs(a) * x) * Math.sin(x);
    }
    public String toString() { return "exp(-|2.5|*x)*sin(x)"; }
}

// Шпигун 1: Профілювальник (засікає час виконання у наносекундах)
class ProfilerHandler implements InvocationHandler {
    private Object target;
    public ProfilerHandler(Object target) { this.target = target; }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        Object result = method.invoke(target, args);
        long end = System.nanoTime();
        System.out.println("[" + target + "]." + method.getName() + " зайняло " + (end - start) + " нс");
        return result;
    }
}

// Шпигун 2: Трасувальник (виводить параметри і результати)
class TracerHandler implements InvocationHandler {
    private Object target;
    public TracerHandler(Object target) { this.target = target; }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Викликано [" + target + "]." + method.getName() + " з аргументами " + Arrays.toString(args));
        Object result = method.invoke(target, args);
        System.out.println("Результат = " + result);
        return result;
    }
}

// Головний клас, який усе це запускає
public class ProxyDemo {
    public static void main(String[] args) {
        MathFunction f1 = new ComplexFunction();
        MathFunction f2 = new SquareFunction();

        System.out.println("--- Профілювання (Час виконання) ---");
        MathFunction profilerProxy1 = (MathFunction) Proxy.newProxyInstance(
                MathFunction.class.getClassLoader(),
                new Class[]{MathFunction.class},
                new ProfilerHandler(f1)
        );
        MathFunction profilerProxy2 = (MathFunction) Proxy.newProxyInstance(
                MathFunction.class.getClassLoader(),
                new Class[]{MathFunction.class},
                new ProfilerHandler(f2)
        );

        profilerProxy1.calculate(1.0);
        profilerProxy2.calculate(1.0);

        System.out.println("\n--- Трасування (Аргументи та результати) ---");
        MathFunction tracerProxy1 = (MathFunction) Proxy.newProxyInstance(
                MathFunction.class.getClassLoader(),
                new Class[]{MathFunction.class},
                new TracerHandler(f1)
        );
        MathFunction tracerProxy2 = (MathFunction) Proxy.newProxyInstance(
                MathFunction.class.getClassLoader(),
                new Class[]{MathFunction.class},
                new TracerHandler(f2)
        );

        tracerProxy1.calculate(1.0);
        tracerProxy2.calculate(1.0);
    }
}