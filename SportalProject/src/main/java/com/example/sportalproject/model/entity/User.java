package com.example.sportalproject.model.entity;

import com.example.sportalproject.model.DTO.userDTOs.UserRegisterRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Component
@AllArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "is_admin")
    private boolean is_admin;
    @Column(name = "phone")
    private String phone;
    @Column(name = "profile_img_url")
    private String profileImgUrl;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Article> articles;

    @ManyToMany
    @JoinTable(
            name = "users_like_articles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<Article> likedArticles;

    @ManyToMany
    @JoinTable(
            name = "users_dislike_articles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<Article> dislikedArticles;


    @ManyToMany(mappedBy = "commentDislikers")
    private Set<Comment> dislikedComments;



    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    Set<Comment> comments;

    @ManyToMany(mappedBy = "commentLikers")
    private Set<Comment> likedComments;

    public User(UserRegisterRequestDTO userDTO) {
        this.firstName = userDTO.getFirst_name();
        this.lastName = userDTO.getLast_name();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.is_admin = userDTO.is_admin();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

}
