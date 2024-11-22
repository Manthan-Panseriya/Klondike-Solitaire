// package com.klondike_solitaire;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.ResultSet;

// public class LeaderBoard extends JFrame {
//     JPanel panel;
//     private static ImageIcon im;
//     private static Image img;
//     private JLabel rankLabel, userNameLabel, scoreLabel;
//     // private JLabel[][] rankLabel;

//     static {
//         im = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\gp.png");
//         img = im.getImage().getScaledInstance(440, 440, Image.SCALE_DEFAULT);
//         im = new ImageIcon(img);
//     }

//     public static void main(String[] args) {
//         new LeaderBoard();
//     }

//     LeaderBoard() {

//         panel = new JPanel();
//         panel.setBounds(0, 0, 440, 440);
//         panel.setBackground(Color.BLACK);
//         panel.setLayout(null);

//         add(panel);

//         JPanel bluePanel = new JPanel();
//         bluePanel.setBackground(Color.BLUE);
//         bluePanel.setBounds(0, 0, 450, 35);
//         bluePanel.setLayout(null);
//         panel.add(bluePanel);

//         rankLabel = initLabels(rankLabel, "Rank");
//         rankLabel.setBounds(15, 3, 120, 28);
//         bluePanel.add(rankLabel);

//         userNameLabel = initLabels(userNameLabel, "Username");
//         userNameLabel.setBounds(150, 3, 180, 28);
//         bluePanel.add(userNameLabel);

//         scoreLabel = initLabels(scoreLabel, "Score");
//         scoreLabel.setBounds(345, 3, 150, 28);
//         bluePanel.add(scoreLabel);

//         JButton button = new JButton("Back");
//         button.setBackground(Color.red);
//         button.setForeground(Color.WHITE);
//         button.setFocusable(false);
//         button.setFont(new Font("Tahoma", Font.BOLD, 16));
//         button.setBounds(140, 390, 150, 30);
//         button.setFocusable(false);
//         panel.add(button);
//         button.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 setVisible(false);
//             }
//         });

//         setUndecorated(true);
//         setSize(450, 450);
//         setLocation(510, 220);
//         setVisible(true);
//         setLayout(null);

//     }

//     private JLabel initLabels(JLabel label, String name) {

//         label = new JLabel(name);
//         label.setBackground(Color.BLUE);
//         label.setForeground(Color.white);
//         label.setFont(new Font("Consalas", Font.BOLD, 25));
//         label.setOpaque(true);

//         return label;
//     }

//     private void setRankLabels() {

//         // rankLabel = new JLabel[4][3];

//         int arr[][] = new int[3][4];

//         for (int i = 0; i < 4; i++) {
//             for (int j = 0; j < 3; j++) {

//             }
//         }
//     }

// }
package com.klondike_solitaire;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LeaderBoard extends JFrame {
    JPanel panel;
    private JTable table;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        new LeaderBoard();
    }

    LeaderBoard() {
        panel = new JPanel();
        panel.setBounds(0, 0, 440, 440);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        add(panel);

        // Table headers
        String[] columnNames = { "Rank", "Username", "Score" };

        // Create JTable and ScrollPane
        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 50, 400, 300);
        panel.add(scrollPane);

        JButton button = new JButton("Back");
        button.setBackground(Color.red);
        button.setForeground(Color.WHITE);
        button.setFocusable(false);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBounds(140, 390, 150, 30);
        button.setFocusable(false);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // Fetch data and populate table
        fetchAndPopulateTable(columnNames);

        setUndecorated(true);
        setSize(450, 450);
        setLocation(510, 220);
        setVisible(true);
        setLayout(null);
    }

    private void fetchAndPopulateTable(String[] columnNames) {
        // DefaultTableModel to hold the data for JTable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            // Sample database connection code, replace with your connection setup
            // Database.getConnection();

            // Query to fetch top 3 users by score
            String query = "SELECT u.user_username, best_score FROM statistics s inner join users u on u.user_id = s.user_id group by u.user_username,s.best_score ORDER BY best_score DESC LIMIT 3";
            Database.prepareStatement(query);
            ResultSet rs = Database.pst.executeQuery();

            int rank = 1; // Rank counter

            // Loop through the result set and populate the table
            while (rs.next()) {
                String username = rs.getString(1);
                int score = rs.getInt(2);

                // Add row to the table model
                model.addRow(new Object[] { rank, username, score });
                rank++;
            }

            model.addRow(new Object[] { rank, User.username, User.score });
            // Set the model to the table
            table.setModel(model);
            table.setRowHeight(40);
            table.setBounds(0, 0, 440, 440);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
