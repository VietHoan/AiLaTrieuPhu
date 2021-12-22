package com.example.ailatrieuphu_project.Model;

public class Player {
    int id;
    int score;

    public Player(int id,int score){
        this.id = id;
        this.score = score;
    }
    public Player(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
