package com.example.quizz;
import java.util.List;

public class Question {
    private String postedOn;
    private String question;
    private String qType;
    private List<String> options;
    private String answer;
    private int points;
    private String difficultyLevel;
    private String postedBy;

    // Getters and Setters
    public String getPostedOn() { return postedOn; }
    public void setPostedOn(String postedOn) { this.postedOn = postedOn; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getQType() { return qType; }
    public void setQType(String qType) { this.qType = qType; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }
}
