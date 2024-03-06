package com.example.the2games;

public class Score {
    private String username;
    private int bestScore;


    public Score(String username, int bestScore) {
        this.username = username;
        this.bestScore = bestScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
}
