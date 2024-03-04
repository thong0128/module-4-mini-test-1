package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class PostsForm {
    private int id;
    private String name;
    private String title;
    private String description;
    private MultipartFile img;

    public PostsForm() {
    }

    public PostsForm(int id, String name, String title, String description, MultipartFile img) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.img = img;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }
}
