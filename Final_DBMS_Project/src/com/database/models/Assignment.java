package com.database.models;

public class Assignment {
    public Integer id;
    public String category;
    public String name;
    public Float points;

    public Assignment(Integer id, String name, String category,  Float points) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.points = points;
    }

    public Integer getId(){ return id; }
    public String getCategory() {
        return category;
    }
    public String getName() {
        return name;
    }
    public Float getPoints() {
        return points;
    }
}
