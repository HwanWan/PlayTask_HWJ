package com.jnu.playtask_hwj.data;

public class Reward {
    private String title;
    private String score;

    public Reward() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Reward(String title, String score) {
        this.title = title;
        this.score = score;
    }
}
