package com.company;

import java.sql.*;
import java.util.Scanner;

public class DB implements IDB {
    Scanner keyboard = new Scanner(System.in);
    private int x;
    public String y;
    public int price=0;
    public int id_person;
    public String name;
    public String surname;
    public String phonenumber;
    public String address;

    public int getPrice() {
        return price;
    }


    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String connectionUrl = "jdbc:postgresql://localhost:5432/Endterm";
        try {
            // Here we load the driver’s class file into memory at the runtime
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            Connection con = DriverManager.getConnection(connectionUrl, "postgres", "0000");

            return con;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @Override
    public void allPizza() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM pizza";
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("-------------------------------------------");
        System.out.println("ID. Pizza Type: Cost(30cm)/Cost(35cm)");
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id") + ". " + resultSet.getString("name") +": " + resultSet.getInt("price_30") + "/" + resultSet.getInt("price_35") + " тг.");
        }
        System.out.println("What type of pizza do you want? (ID): ");
        int iu = keyboard.nextInt();
        System.out.println("What size pizza would you like? (30/35): ");
        int uy = keyboard.nextInt();
        String size = "";
        if (uy==30){
             size = "price_30";
        }else if (uy==35){
            size = "price_35";
        }
        String sql2= "SELECT "+ size +", name FROM pizza WHERE id = " + iu ;
        ResultSet resultSet1 = statement.executeQuery(sql2);
        while (resultSet1.next()){
            x= resultSet1.getInt(size);
            y = resultSet1.getString("name");
            price+=x;
        }


    }

    @Override
    public void addPizza(String name, int price_30, int price_35) throws  SQLException, ClassNotFoundException{
        String sql= "INSERT INTO pizza (name, price_30, price_35) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, price_30);
        preparedStatement.setInt(3, price_35);
        preparedStatement.executeUpdate();
    }

    @Override
    public void smPerson(String person_name, String person_surname, String phonenumber, String addres) throws SQLException, ClassNotFoundException{
        String sql = "INSERT INTO person (name, surname, phone_number, addres) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        preparedStatement.setString(1, person_name);
        preparedStatement.setString(2, person_surname);
        preparedStatement.setString(3, phonenumber);
        preparedStatement.setString(4, addres);
        preparedStatement.executeUpdate();
    }

    @Override
    public void getPerson(String name2) throws  SQLException, ClassNotFoundException{
        String sql2 = "SELECT * FROM person WHERE name = " + "'" + name2 +"'";
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql2);
        while (resultSet.next()){
            id_person = resultSet.getInt("person_id");
            name = resultSet.getString("name");
            surname = resultSet.getString("surname");
            phonenumber = resultSet.getString("phone_number");
            address = resultSet.getString("addres");
        }
    }

    @Override
    public void addCheck(int id, String str) throws SQLException, ClassNotFoundException{
        String sql = "INSERT INTO \"check\" (personID, your_order) values (?, ?)";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2,str);
        preparedStatement.executeUpdate();
    }

    @Override
    public void searchOrder(int id) throws SQLException, ClassNotFoundException{
        String sql = "SELECT * FROM \"check\" WHERE personID = " + id;
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            System.out.println(resultSet.getString("your_order"));
        }
        System.out.println("");
    }



}

