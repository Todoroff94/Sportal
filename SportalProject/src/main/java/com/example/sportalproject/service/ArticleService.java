package com.example.sportalproject.service;

import com.example.sportalproject.exceptions.BadRequestException;
import com.example.sportalproject.exceptions.NotFoundException;
import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleAddDTO;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleResponseDTO;
import com.example.sportalproject.model.DTO.articleDTOs.ArticleWithoutOwnerDTO;
import com.example.sportalproject.model.entity.*;
import com.example.sportalproject.repository.ArticleRepository;
import com.example.sportalproject.repository.PictureRepository;
import com.example.sportalproject.repository.UserRepository;
import com.example.sportalproject.repository.VideoRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.sportalproject.controller.SessionValidator.validateSession;

@Service
public class ArticleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private CategoryService categoryService;


    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }


    private Article getArticleById(long id) {
        return articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article not found"));
    }

    @Transactional
    public ArticleWithoutOwnerDTO create(ArticleAddDTO article, User u) {

        String headline = article.getHeadline();
        if (articleRepository.findByHeadline(headline) != null) {

            throw new BadRequestException("Article already exists.");
        }
        if (!u.is_admin()) {
            throw new UnauthorisedException("You cannot create articles!");
        }
        Category category=categoryService.getById(article.getCategory().getId());
        Article art = modelMapper.map(article, Article.class);
        art.setAuthor(u);
        art.setCategory(category);
        articleRepository.save(art);
        ArticleWithoutOwnerDTO dto = modelMapper.map(art, ArticleWithoutOwnerDTO.class);
        return dto;
    }


    public Article getById(long id) {
        Optional<Article> art = articleRepository.findById(id);

        if (art.isPresent()) {
            Article article = articleRepository.getById(id);
            article.setArticle_views(article.getArticle_views() + 1);
            articleRepository.save(article);
            return article;

        } else
            throw new NotFoundException("Article does not exist.");
    }


    public List<ArticleResponseDTO> searchArticleByName(String title) {

        if (articleRepository.findArticleByHeadlineIsContaining(title) == null) {
            throw new NotFoundException("Article does not exist.");
        }
        List<Article> articles = articleRepository.findArticleByHeadlineIsContaining(title);

        List<ArticleResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < articles.size(); i++) {
            list.add(modelMapper.map(articles.get(i), ArticleResponseDTO.class));
        }

        return list;
    }

    public List<ArticleResponseDTO> getAllById() {
        List<Article> articles = articleRepository.findAll();

        List<ArticleResponseDTO> dto = new ArrayList<>();

        for (Article art : articles) {
            dto.add(modelMapper.map(art, ArticleResponseDTO.class));
        }

        return dto.stream().sorted((a1, a2) -> a2.getArticle_views() - a1.getArticle_views()).limit(5).collect(Collectors.toList());
    }


    @SneakyThrows
    public String uploadArticlePicture(MultipartFile file, HttpServletRequest request, long article_id) {
        validateSession(request.getSession(), request);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("D://sportalpics" + File.separator + name).toPath());
        Picture picture = new Picture();
        picture.setUrl(name);

        Optional<Article> art = articleRepository.findById(article_id);
        if (!art.isPresent()) {
            throw new NotFoundException("Article is not found.You cannot upload files.");
        }

        Article article = articleRepository.getById(article_id);
        picture.setArticle(article);
        picture.setUrl(name);
        article.getPictures().add(picture);
        pictureRepository.save(picture);
        articleRepository.save(article);

        return name;
    }

    @SneakyThrows
    public String uploadArticleVideo(MultipartFile file, HttpServletRequest request, long article_id) {
        validateSession(request.getSession(), request);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("D://sportalpics" + File.separator + name).toPath());
        Optional<Article> art = articleRepository.findById(article_id);
        Video video = new Video();
        video.setUrl(name);

        if (!art.isPresent()) {
            throw new NotFoundException("Article is not found.You cannot upload files.");
        }
        Article article = articleRepository.getById(article_id);
        article.getVideos().add(video);
        videoRepository.save(video);
        articleRepository.save(article);

        return name;
    }

    @Transactional
    public ArticleResponseDTO likeArticle(User u, Article article) {
        if (article.getArticleLikers().contains(u)) {
            throw new BadRequestException("You already liked the article.");
        }
        article.getArticleDislikers().remove(u);
        u.getDislikedArticles().remove(article);
        article.getArticleLikers().add(u);
        u.getLikedArticles().add(article);
        userRepository.save(u);
        articleRepository.save(article);
        ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
        return dto;

    }

    @Transactional
    public ArticleResponseDTO unlikeArticle(User u, Article article) {
        if (!article.getArticleLikers().contains(u)) {
            throw new BadRequestException("You already unliked the article.");
        }
        article.getArticleLikers().remove(u);
        u.getLikedArticles().remove(article);
        userRepository.save(u);
        articleRepository.save(article);
        ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
        return dto;

    }

    @Transactional
    public ArticleResponseDTO dislikeArticle(User u, Article article) {
        if (article.getArticleDislikers().contains(u)) {
            throw new BadRequestException("You already disliked the article!");
        }

        article.getArticleLikers().remove(u);
        u.getLikedArticles().remove(article);
        article.getArticleDislikers().add(u);
        u.getDislikedArticles().add(article);
        userRepository.save(u);
        articleRepository.save(article);
        ArticleResponseDTO dto = modelMapper.map(article, ArticleResponseDTO.class);
        return dto;


    }
}

