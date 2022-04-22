package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradeManagement {
    public Float showGradesByUsername(Connection conn, Integer classId, String username, Boolean print) {
        try {
            Statement stmt = conn.createStatement();
            String query = String.format("SELECT c.category_name,c.weight,\n" +
                    "SUM(a.points) AS total_points, \n" +
                    "SUM(s.grade) AS obtained_marks,\n" +
                    "ROUND((SUM(s.grade)/SUM(a.points)),2) as Grade\n" +
                    "FROM Solution s \n" +
                    "JOIN Assignment a \n" +
                    "ON a.id = s.assignment_name \n" +
                    "JOIN Category c \n" +
                    "ON a.category = c.id \n" +
                    "WHERE c.class_id = '%d' AND \n" +
                    "s.username = '%s' \n" +
                    "GROUP BY c.category_name,c.weight ",classId,username);

            ResultSet rs = stmt.executeQuery(query);
            Float total_grade = 0.0F;
            while (rs.next()) {
                if (print){
                    System.out.println("Category: " + rs.getString("category_name") + " Total points: "+ rs.getInt("total_points") +
                            " Obtain marks: " + rs.getInt("obtained_marks") + " Grade: " + rs.getFloat("Grade"));

                }

                total_grade += rs.getInt("weight") * rs.getFloat("Grade");
            }

            if (print){
                System.out.println("Total grade: " + total_grade);
            }

            return total_grade;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0F;
    }

    public void gradeBook(Connection conn,Integer classId){
        try {
            Statement stmt = conn.createStatement();
            String query = String.format("SELECT s.username , s.student_id , s.first_name ,s.last_name \n" +
                    "FROM Student s \n" +
                    "JOIN Enroll e \n" +
                    "ON e.username = s.username \n" +
                    "WHERE e.class_id = '%d'", classId);

            ResultSet rs = stmt.executeQuery(query);


            System.out.println("Gradebook");

            while (rs.next()) {
                Float grade = showGradesByUsername(conn,classId,rs.getString("username"),false);
                System.out.println("Username: " + rs.getString("username") + " Student_id: "+ rs.getInt("student_id") +
                        " First name: " + rs.getString("first_name") + " Last name: " + rs.getString("last_name") + " Grade: " + grade);

            }


        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
