package net.codejava.blog.models;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    private Long id;
    private String title, anons, full_text;
    private int view;

    public Post() {
    }

    public Post(Long id, String title, String anons, String full_text, int view) {
        this.id = id;
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.view = view;
    }

    public Post(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }


}
