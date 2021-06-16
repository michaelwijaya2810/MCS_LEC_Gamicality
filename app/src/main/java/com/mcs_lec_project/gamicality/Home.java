package com.mcs_lec_project.gamicality;

public class Home {
    private int id;
    private String name;
    private String date;
    private int imageId;
    private String content;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
