package mitchell.com.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Scanner;
import java.sql.*;


public class MitchellWS {

	    private static Scanner inputScanner = new Scanner(System.in);
		private static String username = "";
		private static String password = "";
		
		private static String url="jdbc:mysql:///claimdb";

		private static Connection dbConnection;
		
		public static Boolean exit = false;
		public static Boolean logout = false;
		
		public static void main(String[] args) {

			System.out.println("*************** Mitchell International Claims Processing *****************");
			
			fnStart();
		}
		
		public static void fnStart(){
			
			fnUserConnect();
			
			System.out.println("MSG: User Authenticated Successfully");
			
			fnDBOperations();
		}

		public static void fnUserConnect(){
			
			System.out.println("\nUser Authentication\n--------------------\n");
			
			System.out.print("Enter Username: ");
			username = inputScanner.next();
			
			System.out.print("Enter Password: ");
			password = inputScanner.next();
			
			try {
				
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				dbConnection = DriverManager.getConnection(url,username,password); 
				
			} catch (InstantiationException e) {
				  System.out.println("ERROR: " + e.getMessage());
				System.out.println("MSG: Exiting...");
				System.exit(0);

			} catch (IllegalAccessException e) {
				  System.out.println("ERROR: " + e.getMessage());
				System.out.println("MSG: Exiting...");
				System.exit(0);

			} catch (ClassNotFoundException e) {
				  System.out.println("ERROR: " + e.getMessage());
				System.out.println("MSG: Exiting...");
				System.exit(0);

			} catch (SQLException e) {
				  System.out.println("ERROR: " + e.getMessage());
				
				System.out.print("Would you like to try again? (y/n): ");
				String userInput = inputScanner.next();
				if(userInput.equalsIgnoreCase("y")) {
					fnUserConnect();
				} else {
					System.out.println("MSG: Exiting...");
					System.exit(0);
				}		
				}
			
		}
		
		public static void fnDBOperations(){
			
			exit = false;
			logout = false;
			
			Mitchell_DBOperations dbOp = new Mitchell_DBOperations(dbConnection);
			
			while(!exit){
				dbOp.fnOperationList();
			}
			
			if(logout){
				
				try {
					dbConnection.close();
					System.out.println("MSG: Successfully Logged out of DB.");
				} catch (SQLException e) {
					  System.out.println("ERROR: " + e.getMessage());
				}
				
				System.out.print("\n\nWould you like to login again? (y/n): ");
				String input = inputScanner.next();
				
				if(input.equals("y"))
					fnStart();
				else{
					
					inputScanner.close();
					System.out.println("MSG: Exiting...");
				}
			}
			else{
				try {
					dbConnection.close();
					System.out.println("MSG: Successfully Logged out of DB.");
				} catch (SQLException e) {
					  System.out.println("ERROR: " + e.getMessage());
				}				
			inputScanner.close();
			System.out.println("MSG: Exiting...");
			}			
		}		
	}

