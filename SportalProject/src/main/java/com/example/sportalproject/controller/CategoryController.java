package com.example.sportalproject.controller;

import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.categoryDTOs.CategoryAddDTO;
import com.example.sportalproject.model.entity.Category;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.service.CategoryService;
import com.example.sportalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @PutMapping("/addCategory")
    public Category addCategory(@RequestBody CategoryAddDTO categoryDTO, HttpSession session, HttpServletRequest request) {
        BaseController.validateSession(session, request);
        User admin = userService.getById((long) session.getAttribute(BaseController.USER_ID));

        return categoryService.addCategory(admin, categoryDTO);
    }

    @GetMapping("/getAllCategories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/deleteCategory{id}")
    public void deleteCategory(@PathVariable long id, HttpSession session) {
        User u = userService.getById((Long) session.getAttribute(BaseController.USER_ID));
        if (!u.is_admin()) {
            throw new UnauthorisedException("You are not authorised for this operation.");
        }
        categoryService.deleteCategory(id);

    }
}
