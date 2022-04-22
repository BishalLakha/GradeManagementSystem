package com.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTable {


    private static final String CLASS_TABLE = "CREATE TABLE IF NOT EXISTS Class (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    course_number VARCHAR(255),\n" +
            "    term VARCHAR(255) NOT NULL,\n" +
            "    section_number INT NOT NULL,\n" +
            "    description TEXT\n" +
            ")";


    private static final String CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS Category (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    category_name VARCHAR(255) NOT NULL,\n" +
            "    class_id INT NOT NULL REFERENCES Class(id),\n" +
            "    weight INT NOT NULL    \n" +
            ")";

    private static final String ASSIGNMENT_TABLE = "CREATE TABLE IF NOT EXISTS Assignment (\n" +
            "\tid INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    class_id INT NOT NULL REFERENCES Class(id),\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "\tcategory INT NOT NULL REFERENCES Category(id),\n" +
            "    description TEXT,\n" +
            "    points INT NOT NULL\n" +
            ")";

    private static final String STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS Student (\n" +
            "student_id INT UNIQUE PRIMARY KEY,\n" +
            "username VARCHAR(255) NOT NULL UNIQUE,\n" +
            "last_name VARCHAR(255) NOT NULL,\n" +
            "first_name VARCHAR(255) NOT NULL\n" +
            ")";

    private static final String ENROLL_TABLE = "CREATE TABLE IF NOT EXISTS Enroll (\n" +
            "\tusername VARCHAR(255) NOT NULL REFERENCES Student(username),\n" +
            "\tclass_id INT NOT NULL REFERENCES Class(id)\n" +
            ")";

    private static final String SOLUTION_TABLE = "CREATE TABLE IF NOT EXISTS Solution (\n" +
            "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    username VARCHAR(255) NOT NULL REFERENCES Student(username),\n" +
            "    assignment_name INT NOT NULL REFERENCES Assignment(id),\n" +
            "    grade FLOAT\n" +
            ")";


    public static void createTables(Connection conn) {
        Statement stmt = null;

        try {
//            conn = makeConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(CLASS_TABLE);
            stmt.executeUpdate(CATEGORY_TABLE);
            stmt.executeUpdate(ASSIGNMENT_TABLE);
            stmt.executeUpdate(STUDENT_TABLE);
            stmt.executeUpdate(ENROLL_TABLE);
            stmt.executeUpdate(SOLUTION_TABLE);

        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
    }
}