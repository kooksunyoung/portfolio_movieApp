package com.movie.kook.movieapp;

public class CommentItem {

    private String id;
    private float starScore;
    private String content;

    public CommentItem(String id, float starScore, String content) {
        this.id = id;
        this.starScore = starScore;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getStarScore() {
        return starScore;
    }

    public void setStarScore(float starScore) {
        this.starScore = starScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "id='" + id + '\'' +
                ", starScore=" + starScore +
                ", content='" + content + '\'' +
                '}';
    }
}
