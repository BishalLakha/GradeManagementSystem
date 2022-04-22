package com.database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClassManagement {

    public  Integer selectClass(Connection conn, String course_number, String term, String section_number){
        try {
            Statement stmt = conn.createStatement();
            List<String> query = new ArrayList<>();
            query.add(String.format("SELECT * FROM Class WHERE course_number = '%s'", course_number));
            if(term != ""){
                query.add(String.format("term = '%s'", term));
            }
            if(section_number != ""){
                query.add(String.format("section_number = '%d'", Integer.valueOf(section_number)));
            }
            String SELECT_CLASS = String.join(" AND ", query);
            ResultSet rs = stmt.executeQuery(SELECT_CLASS);
            System.out.println("\n Activated Course Details");
            Integer course_id = 0;
            Integer counter = 0;
            while (rs.next()) {
                counter += 1;
                System.out.println("Course Number: " + rs.getString("course_number") + " Term: " + rs.getString("term")
                        + " Section Number: " + rs.getInt("section_number") + " Description: " + rs.getString("description"));
                course_id = rs.getInt("id");
            }
            if (counter != 1){
                System.out.println("\n More than one class selected");
                return 0;
            }
            return  course_id;

        } catch (SQLException error){
            error.printStackTrace();
        }
        return 0;
    }

    public void createClass(Connection conn,String course_number, String term, Integer section_number,String description )
    {
        try {
            Statement stmt = conn.createStatement();
            String CREATE_CLASS = "INSERT INTO Class (course_number, term, section_number, description) " +
                    "VALUES ('"+course_number+"','"+term+"','"+section_number+"','"+description+"')";
            stmt.executeUpdate(CREATE_CLASS);
            System.out.println("Class created");

        } catch (SQLException error){
//            error.printStackTrace();
            System.out.println("Class already exists");

        }
    }

    public void listClasses(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            String LIST_CLASSES = "SELECT COUNT(username) AS student_count, class_id FROM Enroll GROUP BY class_id";
            ResultSet rs = stmt.executeQuery(LIST_CLASSES);
            while (rs.next()) {
                System.out.println("Course Id: " + rs.getInt("class_id") + " has total " + rs.getInt("student_count") + " students.");
            }
        } catch (SQLException error){
            error.printStackTrace();
        }

    }

    public Integer showClass(Connection conn, Integer class_id) {
        /*Show details of active class*/
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Class WHERE id=" + class_id;
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n Active Course Details");
            while (rs.next()) {
                System.out.println("Course Number: " + rs.getString("course_number") + " Term: " + rs.getString("term")
                        + " Section Number: " + rs.getInt("section_number") + " Description: " + rs.getString("description"));
            }
            System.out.println("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return class_id;
    }
}
