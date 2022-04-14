package com.example.sportalproject.repository;

import com.example.sportalproject.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository <Category,Long> {

    Optional<Category> getByName(String name);


}
