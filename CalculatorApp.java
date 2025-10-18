import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder input;

    public CalculatorApp() {
        input = new StringBuilder();

        // Frame setup
        setTitle("Calculator App");
        setSize(300, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display field
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("C")) {
            input.setLength(0);
            display.setText("");
        } else if (cmd.equals("=")) {
            try {
                double result = evaluate(input.toString());
                display.setText(String.valueOf(result));
                input.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
            }
        } else {
            input.append(cmd);
            display.setText(input.toString());
        }
    }

    private double evaluate(String expression) {
        // Simple evaluation using ScriptEngine
        try {
            return (double) new javax.script.ScriptEngineManager()
                    .getEngineByName("JavaScript")
                    .eval(expression);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Expression");
        }
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}
