package com.example.sportalproject.service;

import com.example.sportalproject.exceptions.BadRequestException;
import com.example.sportalproject.exceptions.NotFoundException;
import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.categoryDTOs.CategoryAddDTO;
import com.example.sportalproject.model.entity.Category;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Category addCategory(User admin, CategoryAddDTO categoryDTO) {


        if (!admin.is_admin()) {
            throw new UnauthorisedException("You are not authorised for this operation.");
        }
        Optional<Category> cat = categoryRepository.getByName(categoryDTO.getName());
        if (cat.isPresent()) {
            throw new BadRequestException("This category already exists!");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
        return category;
    }

    public List<Category> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public void deleteCategory(long category_id) {
        Optional<Category> opt = categoryRepository.findById(category_id);
        if (!opt.isPresent()) {
            throw new NotFoundException("Category not found!");
        }
        categoryRepository.deleteById(category_id);
    }

    public Category getById(long id) {
    Optional<Category> category=categoryRepository.findById(id);
    if(!category.isPresent()){
        throw new NotFoundException("Category not found");
    }
    Category cat= modelMapper.map(category,Category.class);
    return cat;

    }
}
