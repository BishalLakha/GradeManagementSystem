package com.database.models;

public class Solution {
    public Integer id;
    public String username;
    public String assignmentName;
    public Float grade;

    public Solution(Integer id, String username, String assignmentName, Float grade) {
        this.id = id;
        this.username = username;
        this.assignmentName = assignmentName;
        this.grade = grade;
    }

    public Float getGrade() {
        return grade;
    }

}
