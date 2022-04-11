package com.example.sportalproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "pictures")
@NoArgsConstructor
@Component
@AllArgsConstructor
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "picture_url")
    private String url;

    @ManyToOne
    @JoinColumn(name ="article_id")
    private Article article;


}