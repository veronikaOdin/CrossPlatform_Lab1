package task1;

import javax.swing.*;
import java.awt.*;

public class ClassAnalyzerGUI extends JFrame {
    private JTextField inputField;
    private JTextArea resultArea;

    public ClassAnalyzerGUI() {
        setTitle("Аналізатор класу");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Введіть повне ім'я класу:"));
        inputField = new JTextField(30);
        topPanel.add(inputField);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel bottomPanel = new JPanel();
        JButton analyzeBtn = new JButton("Аналіз");
        JButton clearBtn = new JButton("Очистити");
        JButton exitBtn = new JButton("Завершити");

        analyzeBtn.addActionListener(e -> {
            try {
                String result = ClassAnalyzer.analyzeClass(inputField.getText().trim());
                resultArea.setText(result);
                resultArea.setCaretPosition(0);
            } catch (ClassNotFoundException ex) {
                resultArea.setText("Клас не знайдено! Перевірте правильність написання.");
            }
        });

        clearBtn.addActionListener(e -> {
            inputField.setText("");
            resultArea.setText("");
        });

        exitBtn.addActionListener(e -> System.exit(0));

        bottomPanel.add(analyzeBtn);
        bottomPanel.add(clearBtn);
        bottomPanel.add(exitBtn);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClassAnalyzerGUI().setVisible(true));
    }
}