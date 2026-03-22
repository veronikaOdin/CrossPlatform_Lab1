package task2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ObjectInspector {

    public static void inspectAndInvoke(Object obj) {
        Class<?> clazz = obj.getClass();

        System.out.println("Створення об'єкту...");
        System.out.println("Реальний тип об'єкта: " + clazz.getName());
        System.out.println("\n--- Стан об'єкту (Поля та значення) ---");

        // Отримуємо всі поля (навіть приватні)
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Зламуємо доступ до приватних полів
            try {
                System.out.println(field.getType().getSimpleName() + " " + field.getName() + " = " + field.get(obj));
            } catch (IllegalAccessException e) {
                System.out.println("Немає доступу до поля: " + field.getName());
            }
        }

        System.out.println("\n--- Список відкритих методів без параметрів ---");
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> validMethods = new ArrayList<>();

        int counter = 1;
        for (Method method : methods) {
            // Фільтруємо: тільки публічні і тільки без параметрів
            if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0) {
                validMethods.add(method);
                System.out.println(counter + "). " + method.getReturnType().getSimpleName() + " " + method.getName() + "()");
                counter++;
            }
        }

        if (validMethods.isEmpty()) {
            System.out.println("Немає доступних методів для виклику.");
            return;
        }

        // Просимо користувача вибрати метод
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведіть порядковий номер методу [1-" + validMethods.size() + "]: -> ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= validMethods.size()) {
            Method selectedMethod = validMethods.get(choice - 1);
            System.out.println("Виклик методу " + selectedMethod.getName() + "...");
            try {
                // Викликаємо обраний метод
                Object result = selectedMethod.invoke(obj);
                if (selectedMethod.getReturnType() != void.class) {
                    System.out.println("Результат виклику методу: " + result);
                }
            } catch (Exception e) {
                System.out.println("Помилка під час виклику методу: " + e.getMessage());
            }
        } else {
            System.out.println("Неправильний вибір.");
        }
    }

    public static void main(String[] args) {
        // Створюємо наш тестовий об'єкт і передаємо його в інспектор
        SmartGadget myGadget = new SmartGadget();
        inspectAndInvoke(myGadget);
    }
}