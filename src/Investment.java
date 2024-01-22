import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Investment extends JFrame {
    private JPanel panel;
    private JButton deleteFileButton;
    private JButton createFileButton;
    private JButton readFileButton;
    private JButton submitButton;
    private JLabel resultLbl;

    private JLabel ledger = new JLabel("INVESTMENT LEDGER");
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;

    private String companyName;
    private int numOfShares;
    private double priceOfShares;
    private  String modOfPayment;

    // File to store investment data
    private File investmentFile;


    public void initComponents() {
        panel = new JPanel();
        panel.setLayout(null);


        ledger.setBounds(0, 10, 1000, 30);
        ledger.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        ledger.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // You can customize the border
        ledger.setOpaque(true);
        ledger.setBackground(new Color(234, 234, 236, 94));
        ledger.setHorizontalAlignment(SwingConstants.CENTER);
        ledger.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(ledger);


        textField1.setBounds(50, 150, 200, 30);
        setPlaceholder("Company Name", textField1);
        panel.add(textField1);

        textField2.setBounds(270, 150, 150, 30);
        setPlaceholder("Number Of Shares", textField2);
        panel.add(textField2);

        textField3.setBounds(440, 150, 150, 30);
        setPlaceholder("Price Of Shares", textField3);
        panel.add(textField3);

        textField4.setBounds(610, 150, 150, 30);
        setPlaceholder("Mode of Payment", textField4);
        panel.add(textField4);

        createFileButton.setBounds(420, 80, 150, 30);
        createFileButton.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(createFileButton);

        readFileButton.setBounds(350, 220, 100, 30);
        panel.add(readFileButton);

        deleteFileButton.setBounds(550, 220, 100, 30);
        panel.add(deleteFileButton);

        submitButton.setBounds(780, 150, 150, 30);
        panel.add(submitButton);


        resultLbl.setBounds(30, 480, 1000, 40);
        panel.add(resultLbl);



        createFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the file if it doesn't exist
                if (investmentFile == null || !investmentFile.exists()) {
                    try {
                        investmentFile = new File("Portfolio.txt");
                        investmentFile.createNewFile();
                        JOptionPane.showMessageDialog(Investment.this, "File Created Successfully.", "File Created", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Investment.this, "Error creating the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Investment.this, "File already exists.", "File Exists", JOptionPane.INFORMATION_MESSAGE);
                }
//                // Create a new file or select an existing file
//                JFileChooser fileChooser = new JFileChooser();
//                int result = fileChooser.showSaveDialog(Investment.this);
//
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    investmentFile = fileChooser.getSelectedFile();
//                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (investmentFile == null) {
                        resultLbl.setText("Please create a file first.");
                        return;
                    }

                // Validate data before processing
                if (!validateInput()) {
                    return;
                }

                companyName = textField1.getText();
                numOfShares = Integer.parseInt((textField2.getText()));
                priceOfShares = Double.parseDouble((textField3.getText()));
                modOfPayment = textField4.getText();
                String message1 = "The details of your Investment have been written to the file\n\n";
                String dataToWrite = "Company Name: " + companyName + "\n" +
                        "Number of Shares: " + numOfShares + "\n" +
                        "Price of Shares: " + priceOfShares + "\n" +
                        "Mode of Payment: " + modOfPayment+System.lineSeparator();
                String message = message1 + dataToWrite;

                // Display the information in a pop-up message
                JOptionPane.showMessageDialog(Investment.this, message, "Investment Information", JOptionPane.INFORMATION_MESSAGE);


                // Write data to the file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(investmentFile, true))) {

                    writer.write(dataToWrite);
                    JOptionPane.showMessageDialog(Investment.this, "Data written to the file." + investmentFile.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Investment.this,"Error writing to the file.");
                }
            }
            private boolean validateInput() {
                if (textField1.getText().isEmpty() || textField1.getText().equals("Company Name")) {
                    resultLbl.setText("Please enter a valid company name.");
                    return false;
                }

                try {
                    Integer.parseInt(textField2.getText());
                } catch (NumberFormatException ex) {
                    resultLbl.setText("Please enter a valid number of shares.");
                    return false;
                }

                try {
                    Double.parseDouble(textField3.getText());
                } catch (NumberFormatException ex) {
                    resultLbl.setText("Please enter a valid price of shares.");
                    return false;
                }

                if (textField4.getText().isEmpty() || textField4.getText().equals("Mode of Payment")) {
                    resultLbl.setText("Please enter a valid mode of payment.");
                    return false;
                }

                return true;
            }

            
        });

        readFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (investmentFile == null || !investmentFile.exists()) {
                    resultLbl.setText("File does not exist.");
                    return;
                }

                try {
                    // Read data from the file
                    StringBuilder fileContent = new StringBuilder();
                    java.util.Scanner scanner = new java.util.Scanner(investmentFile);

                    while (scanner.hasNextLine()) {
                        fileContent.append(scanner.nextLine()).append("\n");
                    }

                    // Display the file content in a pop-up message
                    String message = "Content of 'Portfolio.txt':\n\n" + fileContent.toString();
                    JOptionPane.showMessageDialog(Investment.this, message, "File Content", JOptionPane.INFORMATION_MESSAGE);

                    scanner.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    resultLbl.setText("Error reading the file.");
                }
            }
        });
        deleteFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (investmentFile != null && investmentFile.exists()) {
                    if (investmentFile.delete()) {
                        JOptionPane.showMessageDialog(Investment.this, "File 'Portfolio.txt' deleted.", "File Deleted", JOptionPane.INFORMATION_MESSAGE);
                        investmentFile = null;  // Reset the file reference
                    } else {
                        JOptionPane.showMessageDialog(Investment.this, "Error deleting the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(Investment.this, "File 'Portfolio.txt' does not exist.", "File Not Found", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    private void setPlaceholder(String placeholder, JTextField field) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    public Investment(){
        //frame = new JFrame("Unit Convertor");
        initComponents();
        setContentPane(panel);
        setSize(1000,600);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



}


