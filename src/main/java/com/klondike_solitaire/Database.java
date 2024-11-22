package com.klondike_solitaire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import  java.io.*;

public class Database {

    protected static Connection con = null;

    protected static PreparedStatement pst = null;

    protected static void getConnection() throws SQLException, IOException {

        FileReader fr = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\com\\klondike_solitaire\\SupabaseData.txt");
        BufferedReader br=new BufferedReader(fr);
        String line=br.readLine();
        String [] arr=new String[3];
        int i=0;
        while (line!=null){
            arr[i]=line;
            i++;
            line=br.readLine();
        }

        con = DriverManager.getConnection(arr[0], arr[1], arr[2]);

        if (con != null)
            System.out.println("Connection Successfully");
        else
            System.out.println("Connection Failed");

    }

    protected static void prepareStatement(String query) throws SQLException{
        pst = con.prepareStatement(query);
    }
}