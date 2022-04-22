package com.database;

import com.database.models.Assignment;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CategoryAndAssignmentManagement {
    public void createCategory(Connection conn, Integer classId, String category_name, Integer weight) {
        try {

            Statement stmt = conn.createStatement();
            String CREATE_CATEGORY = "INSERT INTO Category (class_id,category_name, weight) " +
                    "VALUES ('" + classId + "', '" + category_name + "', '" + weight + "')";
            stmt.executeUpdate(CREATE_CATEGORY);
            System.out.println("\n Category Created Successfully! \n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showCategory(Connection conn, Integer classId) {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Category WHERE class_id=" + classId;
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\nAll categories");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id")+ " Name: " + rs.getString("category_name") + " Weight: " + rs.getString("weight"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAssignment(Connection conn, Integer classId) {
        try {

            Statement stmt = conn.createStatement();
            String query = String.format("\n" +
                    "SELECT a.id, a.name,a.points, c.category_name FROM Assignment a\n" +
                    "JOIN Category c \n" +
                    "ON c.id = a.category \n" +
                    "WHERE c.class_id = '%d'",classId);

            ResultSet rs = stmt.executeQuery(query);

            System.out.println("All assignments");

            List<Assignment> assignmentList = new ArrayList<>();
            while (rs.next()) {
                Assignment assignment = new Assignment(rs.getInt("id"), rs.getString("name"), rs.getString("category_name"), rs.getFloat("points"));
                assignmentList.add(assignment);
            }
            Map<String, List<Assignment>> groupedByCategory = assignmentList.stream()
                    .collect(Collectors.groupingBy(Assignment::getCategory));
            for (Map.Entry<String, List<Assignment>> entry : groupedByCategory.entrySet()) {
                System.out.println("Category: " + entry.getKey());
                entry.getValue().forEach(assignment -> {
                    System.out.println("id: " + assignment.getId() + " Name: " + assignment.getName() + " Points:" + assignment.getPoints());
                });
//                System.out.println("");
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createAssignment(Connection conn, Integer class_id, String name,Integer category, String description, Integer points) {
        try {
            if (class_id !=0) {
                Statement stmt = conn.createStatement();
                String CREATE_ASSIGNMENT = "INSERT INTO Assignment ( class_id,name,category,description,points) " +
                        "VALUES (" + class_id + ",'" + name + "','" + category + "','" + description + "','" + points + "')";
                stmt.executeUpdate(CREATE_ASSIGNMENT);
                System.out.println("\n Assignment Created Successfully! \n");
            }
            else {
                System.out.println("Please activate a class first");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
