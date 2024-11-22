package com.klondike_solitaire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class User {

    protected static String username, password;
    protected static int user_id, moves, score;
    protected static User current_user;
    protected static int game_played, game_won, best_score;
    protected static String best_time;

    // DS
    // -----------------------------------------------------------------------------
    protected static HashMap<String, String> users;
    // -----------------------------------------------------------------------------

    // call this constructor if a new user sign up
    public User(String username, String password, int user_id) {
        User.username = username;
        User.password = password;
        User.user_id = user_id;
    }

    // return current active user's username
    protected static String getUsername(){
        return username;
    }

    // return current active user's password
    protected static String getPassword(){
        return password;
    }

    // return current active user_id
    protected static int getUserId(){
        return user_id;
    }

    protected static int fetchUserId(){

        String query = "SELECT user_id FROM users WHERE user_username = ?";

        try{
            // retrieving user_id of current active_user
            Database.prepareStatement(query);
            // returns username from TextField directly
            Database.pst.setString(1, Login.getUsername());

            ResultSet rs = Database.pst.executeQuery();

            if (rs.next()){
                user_id = rs.getInt("user_id");
            }

            return user_id;

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return -1;
    }

    // prepare list of total registered users
    static protected void getUsers(){

        String query = "SELECT user_username, user_password FROM users";

        try{
            // Storing the user's data into HashMap
            Database.prepareStatement(query);

            users = new HashMap<>();

            ResultSet rs = Database.pst.executeQuery();

            while(rs.next()){

                String username = rs.getString("user_username");
                String password = rs.getString("user_password");

                users.put(username, password);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    protected static void updateGamePlayed(){

        try{
            String getGamePlayed = "SELECT game_played FROM statistics WHERE user_id = (?)";
            Database.prepareStatement(getGamePlayed);
            Database.pst.setInt(1, getUserId());

            ResultSet rs = Database.pst.executeQuery();

            while (rs.next()){
                game_played = rs.getInt("game_played");
            }

            String updateGamePlayed = "UPDATE statistics SET game_played = (?) WHERE user_id = (?)";
            Database.prepareStatement(updateGamePlayed);
            Database.pst.setInt(1, ++game_played);
            Database.pst.setInt(2, getUserId());

            Database.pst.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    protected static void updateGameWonAttributes(){

        try{
            String updateGameWon = "UPDATE statistics SET game_won = (?), best_time = (?), best_score = (?) WHERE user_id = (?)";
            Database.prepareStatement(updateGameWon);
            Database.pst.setInt(1, ++game_won);
            Database.pst.setInt(4, getUserId());

            if(best_time == null){
                Database.pst.setString(2, Utility.timeValueLabel.getText());
            }else{

                String[] best_timeChecker = best_time.split(":");
                String[] current_timeChecker = Utility.timeValueLabel.getText().split(":");

                if(Integer.parseInt(current_timeChecker[0]) < Integer.parseInt(best_timeChecker[0])){
                    Database.pst.setString(2, Utility.timeValueLabel.getText());
                }
                else if(Integer.parseInt(current_timeChecker[0]) == Integer.parseInt(best_timeChecker[0])){

                    if(Integer.parseInt(current_timeChecker[1]) < Integer.parseInt(best_timeChecker[1])){
                        Database.pst.setString(2, Utility.timeValueLabel.getText());
                    }
                }else{
                    Database.pst.setString(2, best_time);
                }
            }

            if(best_score == 0){
                Database.pst.setInt(3, Integer.parseInt(Utility.scoreValueLabel.getText()));
            }
            else Database.pst.setInt(3, Math.max(Integer.parseInt(Utility.scoreValueLabel.getText()), best_score));

            Database.pst.executeUpdate(); // best-time updated if required
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    protected static void initUserAttributes(){
        try{
            String getGameWon = "SELECT game_won, best_time, best_score FROM statistics WHERE user_id = (?)";
            Database.prepareStatement(getGameWon);
            Database.pst.setInt(1, getUserId());

            ResultSet rs = Database.pst.executeQuery();

            while (rs.next()){
                game_won = rs.getInt("game_won");
                best_time = rs.getString("best_time");
                best_score = rs.getInt("best_score");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}