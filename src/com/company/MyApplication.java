package com.company;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyApplication {
    DB db = new DB();
    Scanner keyboard = new Scanner(System.in);	//Create a Scanner object to read input
    boolean discount = false;     		// flag, true if user is eligible for discount
    char choice;                  		// user's choice
    String input;                 		// user input
    String[] orders = new String[10];	// full info about order(s); NOTE: String default value is null
    int numOfOrders = 0;				// number of pizzas ordered
    static String your_name;
    static String str="";               // your final order as toString
    // Welcome message
    public void method(){
        System.out.println("===========================================");
        System.out.println("   Welcome to \"Eat&Chat\" Pizza Order!");
        System.out.println("===========================================");
        System.out.print("Today is: ");
        printCurrentDate();		// prints current date (today)
        System.out.println();
        System.out.print("> Is it your BIRTHDAY? (10% discount available on presenting ID)  (Y/N):  ");
        input = keyboard.nextLine();

        // Discount	Eligibility
        // Determine if user is eligible for discount by having birthday today
        // ADD LINES HERE
        if (input.equals("y") || input.equals("Y")){
        discount = true;
        }

        orders[numOfOrders++] = orderPizza();	// get first order
        previewOrder(orders);	// view order info

        //Repeated Menu Options
        // Keep displaying the menu options until user is done
        // ADD LINES HERE, modify the code below if necessary
        while(true){
            System.out.println("===========================================");
            System.out.println("-------------------MENU--------------------");
            System.out.println("1 - Complete");
            System.out.println("2 - Add another order");
            System.out.println("3 - Search your order with order ID");
            System.out.println("4 - Add new type of pizza");
            System.out.println("5 - Exit");
            System.out.print("> Choose one of the above (Enter a number): ");	// print action menu options
            int input = keyboard.nextInt();

            switch(input){

            // Complete order
                case 1:
                    System.out.println("Your name: ");
                    your_name = keyboard.nextLine();
                    System.out.println("Your surname: ");
                    String your_surname = keyboard.nextLine();
                    System.out.println("Phone number: ");
                    String your_number = keyboard.nextLine();
                    System.out.println("Address: ");
                    String your_address = keyboard.nextLine();
                    try {
                        db.smPerson(your_name, your_surname, your_number, your_address);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    confirmOrder(orders, discount);			// complete order
                    break;

            // Add another pizza order
                case 2:
                    orders[numOfOrders++] = orderPizza();	// save new order
                    previewOrder(orders);					// view order info
                    break;

            // Search with ID
                case 3:
                    System.out.println("Your order ID: ");
                    int searchID = keyboard.nextInt();
                    try {
                        db.searchOrder(searchID);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (StringIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                    break;

            // Add new your new pizza
                case 4:
                    System.out.println("Name:");
                    String namecase3 = keyboard.nextLine();
                    System.out.println("Price for 30 cm: ");
                    int price30case3 = keyboard.nextInt();
                    System.out.println("Price for 35 cm: ");
                    int price35case3 = keyboard.nextInt();
                    try {
                        db.addPizza(namecase3, price30case3, price35case3);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;

            // Exit
                case 5:
                System.out.println("Good bye!");
                System.exit(0);							// exit program

                default:
                System.exit(0);                           // exit program
        }

    }
}


    /**
     The method is used to order one single pizza.
     Gets user preferences, saves all the detailed info as one String, and returns it.
     */
    public static String orderPizza(){
        DB db = new DB();
        Scanner keyboard = new Scanner(System.in);
        String input;                 	//user input
        char choice;                  	//user's choice
        int size;                   	//size of the pizza
        int cost = 0;          			//cost of the pizza
        int numberOfToppings = 0;     	//number of toppings
        String toppings = "Cheese";  	//list of toppings
        String result = "";				// resultant String object to be returned
        final int toppingCost = 200;	// cost for one topping

        //prompt user and get pizza size choice
        try {
            db.allPizza();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------");
        System.out.println("Do you want to add more ingredients? (Y/N): ");
        input = keyboard.nextLine();
        choice = input.charAt(0);
        if (choice == 'Y' || choice == 'y'){
            System.out.println("Additional toppings are 200T each," + " choose from:");
            System.out.println("- Extra Mozzarella, Jalapeno pepper's, Chilli sauce, Mushrooms");
            System.out.print("> Do you want Extra Mozzarella?  (Y/N):  ");
            input = keyboard.nextLine();
            choice = input.charAt(0);
            if (choice == 'Y' || choice == 'y')
            {
                numberOfToppings += 1;
                toppings = toppings + " + Extra Mozzarella";
            }
            System.out.print("> Do you want Jalapeno pepper's?  (Y/N):  ");
            input = keyboard.nextLine();
            choice = input.charAt(0);
            if (choice == 'Y' || choice == 'y')
            {
                numberOfToppings += 1;
                toppings = toppings + " + Jalapeno pepper's";
            }
            System.out.print("> Do you want Chilli sauce?  (Y/N):  ");
            input = keyboard.nextLine();
            choice = input.charAt(0);
            if (choice == 'Y' || choice == 'y')
            {
                numberOfToppings += 1;
                toppings = toppings + " + Chilli sauce";
            }
            System.out.print("> Do you want Mushrooms?  (Y/N):  ");
            input = keyboard.nextLine();
            choice = input.charAt(0);
            if (choice == 'Y' || choice == 'y')
            {
                numberOfToppings += 1;
                toppings = toppings + " + Mushrooms";
            }
        }
        int price= db.price;
        String name=db.y;
        int cost1 = numberOfToppings * toppingCost;
        price = price+cost1;
        //save the order information
        result +=  name + " pizza, " + toppings;
        // add the cost to result
        result += ":" + price;
        return result;

    }

    /**
     Processes the orders array, prints full info about each order and the total cost at the end
     */
    public static void previewOrder(String[] orders){
        System.out.println("-----------------------------");
        System.out.println("Your order: ");

        // Order Info
        // Print individual order info
        // ADD LINES HERE, modify the code below
        for (int i = 0; i < 10; i++) {
            if (orders[i]!= null) {
                System.out.println((i+1)+")" + orders[i]);
                str+= Arrays.toString(new String[]{orders[i]});
            }
        }
        // print total cost
        System.out.println("Total: " + getTotalCost(orders) + " тг.");
    }

    // Total Cost
    // Implement the method below
    /**
     Parses the orders array to calculate the total cost
     */
    public static int getTotalCost(String[] orders){
        // return total cost
        int sum = 0;
        for (int i = 0; i < orders.length; i++){
            if(orders[i] != null){
                String s = orders[i];
                for (int j = 2; j < s.length(); j++) {
                    int num = 0;
                    if (Character.isDigit(s.charAt(j))){
                        num=Integer.parseInt(s.substring(j));
                        sum += num;
                        break;
                    }
                }
            }
        }
        return sum;

    }
    /**
     Order confirmation: prints full order info, calculates discount (if applicable),
     and displays other details like date, time and order ID
     */
    public static void confirmOrder(String[] orders, boolean discount){
        final int DISCOUNT_AMOUNT = 10;	// discount amount in percentage
        DB db = new DB();
        //display order confirmation
        System.out.println("#############################################");
        previewOrder(orders);

        // calculate total cost
        int cost = getTotalCost(orders);

        // Discount Calculation
        // Apply discount only if user is eligible
        // update and print the cost with discount
        if(discount==true){
            System.out.println("-----------------------------");
            System.out.println("Total cost with discount: " + ((double)0.9 * cost));
        }
        // ADD LINES HERE
        try {
            db.getPerson(your_name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------");
        System.out.println("Your order will be ready for pickup in 30 minutes.");
        System.out.println("Full name: " + db.name + " " + db.surname);
        System.out.println("Your phone number: " + db.phonenumber);
        System.out.println("Your address: " + db.address);
        System.out.print("Date: ");
        printCurrentDate();				// prints current date
        System.out.print("\tTime: ");
        printCurrentTime();				// prints current time
        System.out.println();
        System.out.println("Order ID: " + db.id_person);// generates random ID
        try {
            db.addCheck(db.id_person, str);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Current Date
    // Implement the method below
    /**
     Prints the current system date in DD.MM.YYYY format
     HINT:
     */
    public static void printCurrentDate(){
        // print current date
        LocalDate seichas = LocalDate.now(); // Create a date object
        System.out.print(seichas);
    }

    // Current Time
    // Implement the method below
    /**
     Prints the current system time in HH:MM format
     */
    public static void printCurrentTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println( sdf.format(cal.getTime()) );
    }

}
