import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame {
    private JTextField displayField;
    private String currentInput;
    private double currentResult;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayField = new JTextField();
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "sin", "cos", "tan", "sqrt"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String buttonText = clickedButton.getText();

            if (buttonText.equals("=")) {
                calculateResult();
            } else if (buttonText.equals("sqrt")) {
                calculateSquareRoot();
            } else if (buttonText.equals("sin")) {
                calculateTrigFunction("sin");
            } else if (buttonText.equals("cos")) {
                calculateTrigFunction("cos");
            } else if (buttonText.equals("tan")) {
                calculateTrigFunction("tan");
            } else {
                appendToDisplay(buttonText);
            }
        }
    }

    private void appendToDisplay(String text) {
        currentInput = (currentInput == null) ? text : currentInput + text;
        displayField.setText(currentInput);
    }

    private void calculateResult() {
        try {
            String expression = currentInput.replaceAll("sin", "Math.sin")
                    .replaceAll("cos", "Math.cos")
                    .replaceAll("tan", "Math.tan");
            currentResult = (double) new javax.script.ScriptEngineManager().getEngineByName("JavaScript").eval(expression);
            displayField.setText(String.valueOf(currentResult));
            currentInput = null;
        } catch (Exception e) {
            displayField.setText("Error");
        }
    }

    private void calculateSquareRoot() {
        try {
            double inputValue = Double.parseDouble(currentInput);
            currentResult = Math.sqrt(inputValue);
            displayField.setText(String.valueOf(currentResult));
            currentInput = null;
        } catch (NumberFormatException e) {
            displayField.setText("Error");
        }
    }

    private void calculateTrigFunction(String functionName) {
        try {
            double inputValue = Double.parseDouble(currentInput);
            switch (functionName) {
                case "sin":
                    currentResult = Math.sin(Math.toRadians(inputValue));
                    break;
                case "cos":
                    currentResult = Math.cos(Math.toRadians(inputValue));
                    break;
                case "tan":
                    currentResult = Math.tan(Math.toRadians(inputValue));
                    break;
            }
            displayField.setText(String.valueOf(currentResult));
            currentInput = null;
        } catch (NumberFormatException e) {
            displayField.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScientificCalculator calculator = new ScientificCalculator();
            calculator.setVisible(true);
        });
    }
}
