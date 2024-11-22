package com.klondike_solitaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FeedBack extends JFrame {
    private JPanel panelMain;
    private JLabel feedback;
    private JTextArea textArea;

    public FeedBack() {
        button();
        initPanel();
        initFrame();
    }

    private void handleSubmit() {
        String feedback = textArea.getText();
        if (feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Feedback cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Feedback received: " + feedback);

            try{
                File f = new File(System.getProperty("user.dir") + "\\src\\main\\java\\com\\klondike_solitaire\\FeedbackText.txt");
                FileWriter fw = new FileWriter(f, true);
                fw.write(feedback + '\n');
                fw.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }

            textArea.setText("");

            JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new FeedBack();
    }

    void initFrame() {
        this.setTitle("Game FeedBack");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 500);
        setLocation(430, 200);

        this.add(panelMain);
        this.setVisible(true);
    }

    void initPanel() {
        label();

        panelMain = new JPanel();
        panelMain.setLayout(null);
        panelMain.setBounds(330, 330, 450, 500);
        panelMain.setBackground(new Color(123, 50, 250));

        panelMain.add(feedback);
        panelMain.add(textArea);
        panelMain.add(submitButton);

    }

    void label() {
        feedback = new JLabel("Provide Us a Feedback");
        feedback.setBounds(20, 0, 500, 100);
        feedback.setForeground(Color.BLACK);
        feedback.setFont(new Font("Consolas", Font.BOLD, 32));

        textArea = new JTextArea();
        textArea.setBounds(20, 70, 400, 320);
        textArea.setForeground(Color.black);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.setFont(new Font(null, Font.PLAIN, 20));
        textArea.setLineWrap(true);

    }

    JButton submitButton = new JButton("Submit");

    void button() {
        submitButton.setBounds(160, 410, 100, 30);
        submitButton.setFocusable(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
    }
}
