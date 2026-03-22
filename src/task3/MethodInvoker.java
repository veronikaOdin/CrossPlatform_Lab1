package task3;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodInvoker {

    public static void invokeMethodByName(Object obj, String methodName, Object... args) throws FunctionNotFoundException {
        Class<?> clazz = obj.getClass();
        Class<?>[] parameterTypes = new Class<?>[args.length];

        // Визначаємо типи параметрів, які нам передали
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
            // Перетворюємо обгортки (Double, Integer) на примітиви (double, int)
            parameterTypes[i] = mapWrapperToPrimitive(parameterTypes[i]);
        }

        System.out.println("Типи: " + Arrays.toString(parameterTypes) + ", значення: " + Arrays.toString(args));

        try {
            // Шукаємо метод за іменем і типами параметрів
            Method method = clazz.getMethod(methodName, parameterTypes);
            // Викликаємо метод
            Object result = method.invoke(obj, args);
            System.out.println("Результат виклику: " + result + "\n");
        } catch (NoSuchMethodException e) {
            // Якщо метод не знайшли, кидаємо наше виключення
            throw new FunctionNotFoundException("Метод '" + methodName + "' не знайдено в об'єкті.");
        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    // Допоміжний метод-костиль для конвертації типів
    private static Class<?> mapWrapperToPrimitive(Class<?> clazz) {
        if (clazz == Double.class) return double.class;
        if (clazz == Integer.class) return int.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Character.class) return char.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Short.class) return short.class;
        return clazz;
    }

    public static void main(String[] args) {
        SimpleCalculator calc = new SimpleCalculator();
        System.out.println("TestClass [a=1.0, exp(-abs(a)*x)*sin(x)]\n");

        try {
            // 1. Правильний виклик з одним параметром
            invokeMethodByName(calc, "calculate", 1.0);

            // 2. Правильний виклик з двома параметрами (double та int)
            invokeMethodByName(calc, "add", 1.0, 1);

            // 3. Неправильний виклик (такого методу немає)
            invokeMethodByName(calc, "fakeMethod", 5);

        } catch (FunctionNotFoundException e) {
            System.err.println("FunctionNotFoundException: " + e.getMessage());
        }
    }
}