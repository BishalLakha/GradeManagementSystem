package com.database;

import com.database.models.Solution;
import com.database.models.Student;
import com.database.models.Assignment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Validator {

    public Student checkIfUserExists(Connection conn, String username){
        try{
            Statement stmt = conn.createStatement();

            String QUERY = String.format("SELECT * FROM Student s" +
                    " WHERE s.username = '%s'", username);
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                Student student = new Student(rs.getString("username"), rs.getInt("student_id")
                        , rs.getString("last_name"), rs.getString("first_name"));
                return student;
            }

        } catch (SQLException error){
            error.printStackTrace();

        }
        return null;
    }

    public Solution getSolution(Connection conn, String username, Integer assignment_name) {
        try {
            Statement stmt = conn.createStatement();
            String query = String.format("SELECT * FROM Solution WHERE username= '%s' AND assignment_name='%d'",username, assignment_name);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Solution solution = new Solution(rs.getInt("id"), rs.getString("username"),
                        rs.getString("assignment_name"), rs.getFloat("grade"));
                return solution;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isValidGrade(Connection conn, Integer assignment_name, Float grade) {
        Float totalPoints = getTotalPoints(conn, assignment_name);
        if (totalPoints == 0.0f) {
            System.out.println("Assignment not found");
            return false;
        } else {
            if (grade > totalPoints) {
                System.out.println("Grade greater than total points");
                return false;
            }
        }
        return true;
    }

    public Float getTotalPoints(Connection conn, Integer assignment_name) {
        try {
            Statement stmt = conn.createStatement();
            String query = String.format("SELECT * FROM Assignment WHERE id= '%d' ",assignment_name);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                Assignment assignment = new Assignment(rs.getInt("id"),rs.getString("name"), rs.getString("category"),rs.getFloat("points"));
                return assignment.getPoints();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
}

