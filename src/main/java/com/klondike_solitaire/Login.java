package com.klondike_solitaire;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends JFrame implements ActionListener {

    protected static final int frameWidth = 1020, frameHeight = 650;

    static ImageIcon loginFrameLogo = new ImageIcon("solitare.jpg");
    ImageIcon solitaireLogo = new ImageIcon("solitare.jpg");

    JPanel mainPanel;
    JLabel solitaireImageLabel, warningLabel, passwordWarner, imageLabel;
    static JTextField emailTxtF;
    JPasswordField passwordTxtF;
    JButton signUpBtn, signInBtn;
    ImageIcon im = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\com\\Images\\solitaire.png");

    // For Email Validation
    // -----------------------------------------------------------------------------
    private final String USERNAME_PATTERN = "^[\\S]{5,}$";
    private final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN);
    // -----------------------------------------------------------------------------

    // For Password Validation
    // -----------------------------------------------------------------------------
    private final String PASSWORD_PATTERN = "^[\\S]{5,}$";
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    // -----------------------------------------------------------------------------

    Login() {
        image();
        initMainPanel();
        initFrame();
    }

    public static void main(String[] args) {
        new Login();
    }

    protected void initFrame() {

        this.setTitle("Login Frame");
        this.setIconImage(loginFrameLogo.getImage());
        this.setSize(frameWidth, frameHeight); // 1020, 650
        this.setResizable(false);
        this.setLayout(null); // managing the layout self
        this.setLocationRelativeTo(null); // sets the frame in center of the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.add(mainPanel);
        this.add(imageLabel);

        // TO-DO apply threading in Database.getConnection()
        try {
            Database.getConnection();
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        this.setVisible(true);
    }

    protected void initMainPanel() {

        mainPanel = new JPanel();
        mainPanel.setBounds(450, 80, frameWidth - 250, 420);
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);

        solitaireImageLabel = new JLabel(solitaireLogo);
        solitaireImageLabel.setBounds(-30, -9, 350, 440);
        mainPanel.add(solitaireImageLabel);

        setEmailPanel();
        setPasswordPanel();
        setButtons();
        initWarningLabel();
    }

    protected void setEmailPanel() {

        JLabel emailString = new JLabel();
        emailString.setText("Username");
        emailString.setBounds(150, 65, 110, 20);
        emailString.setFont(new Font("Consolas", Font.BOLD, 17));
        emailString.setForeground(Color.WHITE);
        mainPanel.add(emailString);

        emailTxtF = new JTextField();
        emailTxtF.setBounds(150, 90, 240, 35);
        emailTxtF.setMargin(new Insets(4, 10, 0, 10));
        emailTxtF.setFont(new Font("Consolas", Font.PLAIN, 16));
        emailTxtF.setForeground(Color.WHITE);
        emailTxtF.setCaretColor(Color.WHITE);
        emailTxtF.setBackground(new Color(27, 27, 27));
        mainPanel.add(emailTxtF);
    }

    protected void setPasswordPanel() {

        JLabel passString = new JLabel();
        passString.setText("Password");
        passString.setBounds(150, 150, 120, 20);
        passString.setFont(new Font("Consolas", Font.BOLD, 16));
        passString.setForeground(Color.WHITE);
        mainPanel.add(passString);

        passwordTxtF = new JPasswordField();
        passwordTxtF.setBounds(150, 175, 240, 35);
        passwordTxtF.setMargin(new Insets(2, 10, 0, 10));
        passwordTxtF.setBackground(new Color(27, 27, 27));
        passwordTxtF.setForeground(Color.WHITE);
        passwordTxtF.setCaretColor(Color.WHITE);
        mainPanel.add(passwordTxtF);
    }

    protected void setButtons() {

        signUpBtn = new JButton();
        signUpBtn.setBounds(160, 300, 100, 32);
        signUpBtn.setText("Sign Up");
        signUpBtn.setMargin(new Insets(3, 5, 0, 5));
        signUpBtn.setFont(new Font("Consolas", Font.BOLD, 16));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setBackground(new Color(50, 205, 50));
        signUpBtn.setFocusable(false);
        signUpBtn.addActionListener(this);
        mainPanel.add(signUpBtn);

        signInBtn = new JButton();
        signInBtn.setBounds(270, 300, 100, 32);
        signInBtn.setText("Sign In");
        signInBtn.setMargin(new Insets(3, 5, 0, 5));
        signInBtn.setFont(new Font("Consolas", Font.BOLD, 16));
        signInBtn.setForeground(Color.WHITE);
        signInBtn.setBackground(new Color(50, 205, 50));
        signInBtn.setFocusable(false);
        signInBtn.addActionListener(this);
        mainPanel.add(signInBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        User.getUsers(); // init HashMap containing user's data

        if (e.getSource() == signUpBtn) {

            // checks for value returned from TextField And PasswordField
            if (isValidUsername(getUsername()) && isValidPassword(getPassword())) {

                // User.users => users = HashMap
                if (User.users.containsKey(getUsername())) {
                    // will run if user is already signed up
                    warningLabel.setText("Already a user, please sign in.");
                    passwordWarner.setText("");
                } else {

                    // run for new users
                    try {

                        // Database Insertion
                        String query = "INSERT INTO users (user_username, user_password) VAlUES (?, ?)";
                        Database.prepareStatement(query);

                        Database.pst.setString(1, getUsername()); // JTextField
                        Database.pst.setString(2, getPassword()); // JPasswordField
                        Database.pst.executeUpdate();

                        User.current_user = new User(getUsername(), getPassword(), User.fetchUserId());

                        query = "INSERT INTO statistics (user_id) VALUES (?)";
                        Database.prepareStatement(query);
                        Database.pst.setInt(1, User.getUserId());
                        Database.pst.executeUpdate();

                        User.updateGamePlayed();
                        User.initUserAttributes();

                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }

                    this.setVisible(false);
                    Solitaire.solitaire = new Solitaire();
                }
            }

            if (!isValidUsername(getUsername())) {

                warningLabel.setText("Invalid Username.");
                passwordWarner.setText("");
            } else if (!isValidPassword(getPassword())) {
                warningLabel.setText("Invalid Password.");
                passwordWarner.setText("More than 4 chars & no spacing.");
            }

        } else if (e.getSource() == signInBtn) {
            // users.get(getUsername()) => returns password
            if (User.users.containsKey(getUsername()) && User.users.get(getUsername()).equals(getPassword())) {

                User.current_user = new User(getUsername(), getPassword(), User.fetchUserId());

                User.updateGamePlayed();
                User.initUserAttributes();

                this.setVisible(false);
                Solitaire.solitaire = new Solitaire();
            } else {
                passwordWarner.setText("");
                warningLabel.setText("Invalid Username OR Password.");
            }
        }
    }

    protected void initWarningLabel() {

        warningLabel = new JLabel();
        warningLabel.setBounds(250, 230, 290, 20);
        warningLabel.setFont(new Font("Consolas", Font.BOLD, 16));
        warningLabel.setForeground(Color.RED);
        mainPanel.add(warningLabel);

        passwordWarner = new JLabel();
        passwordWarner.setBounds(250, 251, 400, 20);
        passwordWarner.setFont(new Font("Consolas", Font.BOLD, 16));
        passwordWarner.setForeground(Color.RED);
        mainPanel.add(passwordWarner);
    }

    protected boolean isValidUsername(String username) {

        if (username == null) {
            return false;
        }

        Matcher matcher = usernamePattern.matcher(username);
        return matcher.matches();
    }

    protected boolean isValidPassword(String password) {

        if (password == null) {
            return false;
        }

        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    void image() {
        imageLabel = new JLabel(im);
        imageLabel.setBounds(50, 50, 400, 400);
    }

    // returns username from JTextField
    protected static String getUsername() {
        return emailTxtF.getText();
    }

    // returns password from JPasswordField
    protected String getPassword() {

        try {
            return passwordTxtF.getText();
        } catch (NullPointerException e) {
            return "";
        }
    }
}