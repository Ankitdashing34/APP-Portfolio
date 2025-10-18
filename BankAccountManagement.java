import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private String name;
    private double balance;

    public BankAccount(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
}

public class BankAccountApp extends JFrame implements ActionListener {
    private JTextField nameField, amountField;
    private JTextArea displayArea;
    private JButton createBtn, depositBtn, withdrawBtn, balanceBtn;
    private BankAccount account;

    public BankAccountApp() {
        setTitle("Bank Account Management");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("Account Holder Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        createBtn = new JButton("Create Account");
        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        balanceBtn = new JButton("Check Balance");

        buttonPanel.add(createBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(balanceBtn);
        add(buttonPanel, BorderLayout.CENTER);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Arial", Font.PLAIN, 16));
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Add listeners
        createBtn.addActionListener(this);
        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        balanceBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String amountText = amountField.getText();
        double amount = 0;

        if (!amountText.isEmpty()) {
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid amount!");
                return;
            }
        }

        if (e.getSource() == createBtn) {
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter account holder name!");
                return;
            }
            account = new BankAccount(name, 0);
            displayArea.setText("Account created for: " + name + "\nInitial Balance: ₹0.00");
        } else if (e.getSource() == depositBtn) {
            if (account == null) {
                JOptionPane.showMessageDialog(this, "No account found! Create one first.");
                return;
            }
            account.deposit(amount);
            displayArea.setText("₹" + amount + " deposited.\nCurrent Balance: ₹" + account.getBalance());
        } else if (e.getSource() == withdrawBtn) {
            if (account == null) {
                JOptionPane.showMessageDialog(this, "No account found! Create one first.");
                return;
            }
            if (!account.withdraw(amount)) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
            } else {
                displayArea.setText("₹" + amount + " withdrawn.\nCurrent Balance: ₹" + account.getBalance());
            }
        } else if (e.getSource() == balanceBtn) {
            if (account == null) {
                JOptionPane.showMessageDialog(this, "No account found! Create one first.");
                return;
            }
            displayArea.setText("Account Holder: " + account.getName() + "\nCurrent Balance: ₹" + account.getBalance());
        }
    }

    public static void main(String[] args) {
        new BankAccountApp();
    }
}
