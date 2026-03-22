package task1;

import java.lang.reflect.*;
import java.util.Scanner;

public class ClassAnalyzer {

    public static String analyzeClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return analyzeClass(clazz);
    }

    public static String analyzeClass(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        if (clazz.getPackage() != null) {
            sb.append("package ").append(clazz.getPackage().getName()).append(";\n\n");
        }

        sb.append(Modifier.toString(clazz.getModifiers())).append(" ");
        if (clazz.isInterface()) {
            sb.append("interface ");
        } else {
            sb.append("class ");
        }
        sb.append(clazz.getSimpleName());

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            sb.append(" extends ").append(superclass.getSimpleName());
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            sb.append(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                sb.append(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) sb.append(", ");
            }
        }
        sb.append(" {\n\n");

        sb.append("    // Поля\n");
        for (Field field : clazz.getDeclaredFields()) {
            sb.append("    ").append(Modifier.toString(field.getModifiers())).append(" ")
                    .append(field.getType().getSimpleName()).append(" ")
                    .append(field.getName()).append(";\n");
        }

        sb.append("\n    // Конструктори\n");
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            sb.append("    ").append(Modifier.toString(constructor.getModifiers())).append(" ")
                    .append(clazz.getSimpleName()).append("(");
            Parameter[] params = constructor.getParameters();
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getType().getSimpleName()).append(" par").append(i);
                if (i < params.length - 1) sb.append(", ");
            }
            sb.append(");\n");
        }

        sb.append("\n    // Методи\n");
        for (Method method : clazz.getDeclaredMethods()) {
            sb.append("    ").append(Modifier.toString(method.getModifiers())).append(" ")
                    .append(method.getReturnType().getSimpleName()).append(" ")
                    .append(method.getName()).append("(");
            Parameter[] params = method.getParameters();
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getType().getSimpleName()).append(" par").append(i);
                if (i < params.length - 1) sb.append(", ");
            }
            sb.append(");\n");
        }

        sb.append("}\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type Java class full name (for example java.lang.String)");
        System.out.print("-> ");
        String className = scanner.nextLine();

        try {
            System.out.println("\n" + analyzeClass(className));
        } catch (ClassNotFoundException e) {
            System.out.println("Помилка: Клас не знайдено.");
        }
    }
}