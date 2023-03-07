package com.example.demo2.model;

public class Question {
    private String text;
    private String difficultyLevel;
    public void setText(String text) {
        this.text = text;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getText() {
        return text;
    }

    public Question(String text, String difficultyLevel) {
        this.text = text;
        this.difficultyLevel = difficultyLevel;
    }

    public String generateAnswer(){
        return text.toLowerCase();
    }
}
