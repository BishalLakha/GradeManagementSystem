package com.database;

import com.database.models.Solution;
import com.database.models.Student;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class StudentManagement {
    public void addStudent(Connection conn,Integer classId,String username,Integer student_id, String last_name, String first_name){
        try {
            Validator validator = new Validator();
            Student student = validator.checkIfUserExists(conn,username);
            Statement stmt = conn.createStatement();
            if (student == null) {
                String query = "INSERT INTO Student (student_id,username, last_name, first_name) " +
                        "VALUES ('" + student_id + "', '" + username + "','" + last_name + "','" + first_name + "')";
                stmt.executeUpdate(query);
                System.out.println("\n Student Created Successfully! \n");
            }else {
                student_id = student.getStudentId();
                if (student.getLastName() == last_name) {
                    System.out.println("Updating last name");
                }
                if (student.getFirstName() == first_name) {
                    System.out.println("Updating first name");
                }
                String sql = "UPDATE  Student SET last_name='" + last_name +
                        "',first_name='" + first_name + "' WHERE username=" + username;
                System.out.println("\n Student Updated Successfully! \n");
                stmt.executeUpdate(sql);
            }
            enrollStudent(conn,username,classId);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void showStudentWithString(Connection conn, String string_data, Integer classId){
        try {
            Statement stmt = conn.createStatement();
            String data = "%" + string_data  + "%";
            String query = String.format("SELECT s.username , s.first_name \n" +
                    "FROM Student s\n" +
                    "JOIN Enroll e\n" +
                    "ON s.username = e.username \n" +
                    "WHERE s.username LIKE '%s'  \n" +
                    "OR s.first_name LIKE '%s'\n" +
                    "AND e.class_id = 1", data, data, classId);

            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Student having certain string pattern");
            while (rs.next()) {
                System.out.println(" Username: " + rs.getString("username") + " first_name: " + rs.getString("first_name"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void showStudents(Connection conn, Integer classId){
        try {
            Statement stmt = conn.createStatement();
            String query = String.format("SELECT s.username\n" +
                    "FROM Student s\n" +
                    "JOIN Enroll e \n" +
                    "ON s.username = e.username \n" +
                    "WHERE e.class_id = '%d'\n", classId);
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n All Students! \n");

            while (rs.next()) {
                System.out.println(" Username: " + rs.getString("username"));

            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }


    }


    public void enrollStudent(Connection conn, String username, Integer class_id){
        try {
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO Enroll (username, class_id) " +
                    "VALUES ('"+ username +"','"+ class_id +"')";
            stmt.executeUpdate(query);
            System.out.println("\n Student Enrolled Successfully! \n");
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void enrollByUsername(Connection conn,Integer classId,String username){
        try {
            Validator validator = new Validator();
            Student student = validator.checkIfUserExists(conn,username);
            if (student == null){
                System.out.println("Student does not exist");
            }else{
                enrollStudent(conn,username,classId);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gradeAssignment(Connection conn,Integer classId, Integer assignment_name, String username, Float grade) {
        try {
            Statement stmt = conn.createStatement();
            Validator validator = new Validator();

            if (validator.isValidGrade(conn, assignment_name, grade)) {
                Solution solution = validator.getSolution(conn, username, assignment_name);
                String query;
                if(solution != null ){
                    System.out.println("Updating Grade");
                    query = String.format("UPDATE  Solution SET grade= '%f' WHERE username= '%s' AND assignment_name= '%d",grade,username,assignment_name);
                } else{
                    System.out.println("Inserting grade");
                    query = "INSERT INTO Solution (username, assignment_name, grade) " +
                            "VALUES ('" + username + "','" + assignment_name + "'," + grade + ")";
                }
                stmt.executeUpdate(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
