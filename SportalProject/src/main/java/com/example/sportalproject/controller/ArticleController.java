package com.example.sportalproject.controller;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleAddDTO;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleResponseDTO;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleWithoutOwnerDTO;
import com.example.sportalproject.model.entity.Article;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.repository.ArticleRepository;
import com.example.sportalproject.repository.UserRepository;
import com.example.sportalproject.service.ArticleService;
import com.example.sportalproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class ArticleController  {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    @PostMapping("/newArticle")
    public ResponseEntity<ArticleWithoutOwnerDTO> createArticle(@RequestBody ArticleAddDTO article, HttpSession session, HttpServletRequest request) {

        SessionValidator.validateSession(session, request);
        User u = userService.getById((long) session.getAttribute(SessionValidator.USER_ID));
        ArticleWithoutOwnerDTO art = articleService.create(article, u);
        return ResponseEntity.ok(art);
    }


    @GetMapping("/article{id}")
    public ResponseEntity<ArticleResponseDTO> getById(@PathVariable int id) {

        Article art = articleService.getById(id);
        ArticleResponseDTO dto = modelMapper.map(art, ArticleResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/article/search/{title}")
    public ResponseEntity<List<ArticleResponseDTO>> searchArticleByName(@PathVariable String title) {
        List<ArticleResponseDTO> articles = articleService.searchArticleByName(title);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/5MostViewed")
    public ResponseEntity<List<ArticleResponseDTO>> get5MostViewedArticles() {
        List<ArticleResponseDTO> articles = articleService.getAllById();
        return ResponseEntity.ok(articles);
    }


    @GetMapping("/articles/all")
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles() {

        List<ArticleResponseDTO> articles = articleService.getAllById();
        return ResponseEntity.ok(articles);
    }


    @PutMapping("/like/article{id}")
    public ResponseEntity<ArticleResponseDTO> likeArticle(@PathVariable long id, HttpSession session, HttpServletRequest request) {

        Article article = articleService.getById(id);
        SessionValidator.validateSession(session, request);
        User u = articleService.getUserById(((long) session.getAttribute(SessionValidator.USER_ID)));
        ArticleResponseDTO dto = articleService.likeArticle(u, article);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/unlike/article{id}")
    public ResponseEntity<ArticleResponseDTO> unlikeArticle(@PathVariable long id, HttpSession session, HttpServletRequest request) {

        Article article = articleService.getById(id);
        SessionValidator.validateSession(session, request);
        User u = articleService.getUserById(((long) session.getAttribute(SessionValidator.USER_ID)));
        ArticleResponseDTO dto = articleService.unlikeArticle(u, article);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/dislike/article{id}")
    public ResponseEntity<ArticleResponseDTO> dislikeArticle(@PathVariable long id, HttpSession session, HttpServletRequest request) {

        Article article = articleService.getById(id);
        SessionValidator.validateSession(session, request);
        User u = articleService.getUserById(((long) session.getAttribute(SessionValidator.USER_ID)));
        ArticleResponseDTO dto = articleService.dislikeArticle(u, article);

        return ResponseEntity.ok(dto);
    }


    @PostMapping("/uploadPicture/article{id}")
    public String uploadArticleImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request, @PathVariable long id) {
        return articleService.uploadArticlePicture(file, request, id);
    }

    @PostMapping("/uploadVideo/article{id}")
    public String uploadArticleVideo(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request, @PathVariable long id) {
        return articleService.uploadArticleVideo(file, request, id);
    }


}
