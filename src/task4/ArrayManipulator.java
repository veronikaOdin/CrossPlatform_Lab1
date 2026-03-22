package task4;

import java.lang.reflect.Array;

public class ArrayManipulator {

    // 1. Універсальне створення масиву або матриці
    public static Object createArray(Class<?> type, int... dimensions) {
        return Array.newInstance(type, dimensions);
    }

    // 2. Зміна розміру одновимірного масиву
    public static Object resizeArray(Object oldArray, int newSize) {
        Class<?> elementType = oldArray.getClass().getComponentType();
        Object newArray = Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(Array.getLength(oldArray), newSize);
        if (preserveLength > 0) {
            // Копіюємо старі дані в новий масив
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        }
        return newArray;
    }

    // Зміна розміру матриці (двовимірного масиву)
    public static Object resizeMatrix(Object oldMatrix, int newRows, int newCols) {
        Class<?> elementType = oldMatrix.getClass().getComponentType().getComponentType();
        Object newMatrix = Array.newInstance(elementType, newRows, newCols);
        int rowsToCopy = Math.min(Array.getLength(oldMatrix), newRows);

        for (int i = 0; i < rowsToCopy; i++) {
            Object oldRow = Array.get(oldMatrix, i);
            Object newRow = Array.get(newMatrix, i);
            int colsToCopy = Math.min(Array.getLength(oldRow), newCols);
            System.arraycopy(oldRow, 0, newRow, 0, colsToCopy);
        }
        return newMatrix;
    }

    // 3. Універсальний метод перетворення на рядок
    public static String arrayToString(Object array) {
        if (array == null) return "null";
        if (!array.getClass().isArray()) return array.toString();

        int length = Array.getLength(array);
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            if (element != null && element.getClass().isArray()) {
                sb.append(arrayToString(element)); // Рекурсія для вкладених масивів
            } else {
                sb.append(element);
            }
            if (i < length - 1) sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        // Тестуємо створення одновимірних масивів
        Object intArray = createArray(int.class, 2);
        System.out.println("int[2] = " + arrayToString(intArray));

        Object strArray = createArray(String.class, 3);
        System.out.println("String[3] = " + arrayToString(strArray));

        Object doubleArray = createArray(Double.class, 5);
        System.out.println("Double[5] = " + arrayToString(doubleArray));

        System.out.println("\n-------------------------\n");

        // Тестуємо створення матриці
        Object matrix = createArray(int.class, 3, 5);

        // Заповнюємо матрицю даними для перевірки
        for (int i = 0; i < 3; i++) {
            Object row = Array.get(matrix, i);
            for (int j = 0; j < 5; j++) {
                Array.set(row, j, (i * 10) + j);
            }
        }
        System.out.println("int[3][5] = " + arrayToString(matrix));

        // Тестуємо зміну розмірів зі збереженням даних
        System.out.println("\n--- Збільшуємо розмір до 4x6 ---");
        Object biggerMatrix = resizeMatrix(matrix, 4, 6);
        System.out.println("int[4][6] = " + arrayToString(biggerMatrix));

        System.out.println("\n--- Зменшуємо розмір до 2x2 ---");
        Object smallerMatrix = resizeMatrix(matrix, 2, 2);
        System.out.println("int[2][2] = " + arrayToString(smallerMatrix));
    }
}