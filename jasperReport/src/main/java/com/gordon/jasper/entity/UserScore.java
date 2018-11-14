package com.gordon.jasper.entity;

/**
 * Created by gordon on 2018/8/26.
 */
public class UserScore {

    private int id;
    private String name;
    private String course;
    private double score;

    public UserScore(){}

    public UserScore(int id, String name, String course, double score) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
