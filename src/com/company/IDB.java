package com.company;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    // Connect database (pgadmin) with java
    public Connection getConnection() throws SQLException, ClassNotFoundException;

    // Shows the pizza menu from the database. And the user selects the type of pizza he wants to order
    public void allPizza() throws SQLException, ClassNotFoundException;

    // This method adds a new type of pizza
    public void addPizza(String name, int price_30, int price_35) throws SQLException, ClassNotFoundException;

    // Adding information about the user to the database
    public void smPerson(String person_name, String person_surname, String phonenumber, String addres) throws SQLException, ClassNotFoundException;

    // Gets user information from database
    public void getPerson(String name2) throws SQLException, ClassNotFoundException;

    // Adds an order receipt to the database using a foreign key which is associated with the user id
    public void addCheck(int id, String str) throws SQLException, ClassNotFoundException;

    // Looks for an order receipt using the order id
    public void searchOrder(int id) throws SQLException, ClassNotFoundException;
}