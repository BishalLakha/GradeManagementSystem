package com.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Main {
    public static Connection makeConnection() {
        try {
            // Local db
//            String DB_URL = "jdbc:mysql://localhost:3306/db_final_project?verifyServerCertificate=false&useSSL=true";
//            String username = "root";
//            String password = "p@ssW0rd";

            // Onyx
            String DB_URL = "jdbc:mysql://localhost:53261/db_final_project?verifyServerCertificate=false&useSSL=true";
            String username = "msandbox";
            String password = "p@ssword";

            Connection conn = DriverManager.getConnection(DB_URL, username, password);
            System.out.println("Database [test db] connection succeeded!");
            System.out.println();
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // The newInstance() call is a workaround for some broken Java implementations
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println();
            System.out.println("JDBC driver loaded");
            System.out.println();

            // Create connections
            Connection conn = makeConnection();

            // Create table
            CreateTable createTable = new CreateTable();
            createTable.createTables(conn);

            // Create class management object
            ClassManagement classManagement = new ClassManagement();
            CategoryAndAssignmentManagement categoryAndAssignmentManagement = new CategoryAndAssignmentManagement();
            StudentManagement studentManagement = new StudentManagement();
            GradeManagement gradeManagement = new GradeManagement();

            // Show menu
            Menu menu = new Menu();
            Integer activeClassId = 0;
            try {
                Integer choice = 0;
                Scanner scan = new Scanner(System.in);
                while (choice != 5) {
                    menu.mainMenu();
                    choice = scan.nextInt();
                    if (choice != 1 && activeClassId == 0) {
                        System.out.println("Please activate your class ");
                    } else {
                        System.out.println("\n Active Class Id: " + activeClassId);
                        switch (choice) {
                            case 1:
                                activeClassId = menu.menuClassManagement(conn, classManagement, activeClassId);
                                break;
                            case 2:
                                menu.menuCategoryAndAssignmentManagement(conn,categoryAndAssignmentManagement,activeClassId);
                                break;
                            case 3:
                                menu.menuStudentManagement(conn,studentManagement,activeClassId);
                                break;
                            case 4:
                                menu.menuGradeManagement(conn, gradeManagement,activeClassId);
                                break;
                            case 5:
                                System.out.println("Database Connection Closed");
                                conn.close();
                                break;
                            default:
                                System.out.println(" Invalid option. Exiting the shell. \n");
                                break;
                        }
                    }
                }
                scan.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Run Query
            conn.close();
            System.out.println();
            System.out.println("Database connection closed");
            System.out.println();
        } catch (Exception error) {
            // handle the error
            System.err.println(error);
        }
    }
}
