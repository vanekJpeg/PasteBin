package ru.vanek.pastebin.models;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
@Entity
@Data
@Table(name = "pastes")
public class Paste {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User author;
    @Column(name="rate")
    private double rate;
    @Column(name="views")
    private int views;
    @Column(name="text")
    private String text;
    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name="expiration_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationAt;


    public Paste() {
    }

    public Paste(String name, User author, String text) {
        this.name = name;
        this.author = author;
        this.text = text;
    }
}
