package com.database.models;

public class Student {
    private String username;
    private Integer student_id;
    private String last_name;
    private String first_name;

    public Student(String username, Integer student_id, String last_name, String first_name) {
        this.username = username;
        this.student_id = student_id;
        this.last_name = last_name;
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }
    public Integer getStudentId() {
        return student_id;
    }
    public String getLastName() {return last_name; }
    public String getFirstName() {
        return first_name;
    }
}
