package com.example.sportalproject.model.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table (name = "article_category")
@Getter
@Setter
@NoArgsConstructor
@Component
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Article> articles;


}
