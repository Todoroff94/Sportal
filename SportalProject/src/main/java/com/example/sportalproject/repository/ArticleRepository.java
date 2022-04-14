package com.example.sportalproject.repository;

import com.example.sportalproject.model.entity.Article;
import com.example.sportalproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

    Article findByHeadline(String headline);

    @Query(value = "FROM Article  WHERE headline LIKE %:title%")

    List<Article> findArticleByHeadlineIsContaining(@Param ("title")String title);

    Optional<User> findUserById(int user_id);


}
