package com.example.sportalproject.service;

import com.example.sportalproject.exceptions.BadRequestException;
import com.example.sportalproject.exceptions.NotFoundException;
import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.categoryDTOs.CommentEditDTO;
import com.example.sportalproject.model.DTO.commentDTOs.*;
import com.example.sportalproject.model.entity.Article;
import com.example.sportalproject.model.entity.Comment;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.model.repository.ArticleRepository;
import com.example.sportalproject.model.repository.CommentRepository;
import com.example.sportalproject.model.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public CommentWithoutArticleDTO createParentComment(CommentRequestDTO commentDto, Article article, User u) {

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setContent(commentDto.getName());
        comment.setAuthor(u);
        comment.setParentComment(comment);
        commentRepository.save(comment);
        CommentWithoutArticleDTO dto = modelMapper.map(comment, CommentWithoutArticleDTO.class);
        return dto;
    }

    public Comment getCommentById(Long parentComment_id) {
        return commentRepository.findById(parentComment_id).orElseThrow(() -> new NotFoundException("Comment not found"));
    }

    @Transactional
    public CommentWithoutArticleDTO createChildComment(SubCommentDTO subCommentDTO, User u) {
        Comment parentComment = getCommentById(subCommentDTO.getId());


        Comment comment = new Comment();
        comment.setParentComment(parentComment);
        comment.setArticle(parentComment.getArticle());
        comment.setAuthor(u);
        comment.setContent(subCommentDTO.getContent());
        commentRepository.save(comment);
        CommentWithoutArticleDTO dto = modelMapper.map(comment, CommentWithoutArticleDTO.class);
        return dto;
    }

    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
    }


    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public ResponseEntity<CommentEditDTO> editComment(CommentEditDTO comment, User u) {
        Comment com = commentRepository.getById(comment.getId());
        if (com.getAuthor().getId() != u.getId()) {
            throw new UnauthorisedException("You cannot edit this comment!");
        }
        com.setContent(comment.getContent());
        com.setAuthor(u);
        commentRepository.save(com);
        CommentEditDTO dto = modelMapper.map(com, CommentEditDTO.class);

        return ResponseEntity.ok(dto);
    }

    public void deleteComment(long id) {

        Comment comment = commentRepository.getById(id);
        if (comment == null) {
            throw new UnauthorisedException("Comment not found");
        }
        List<Comment> comments=commentRepository.findCommentByParentCommentId(id);
        commentRepository.deleteAll(comments);
        commentRepository.delete(comment);

    }

    @Transactional
    public LikeCommentDTO likeComment(User u, Comment comment) {
        if (comment.getCommentLikers().contains(u)) {
            throw new BadRequestException("You already liked the comment.");
        }
        comment.getCommentLikers().add(u);
        u.getLikedComments().add(comment);
        comment.getCommentDislikers().remove(u);
        u.getDislikedComments().remove(comment);
        userRepository.save(u);
        commentRepository.save(comment);
        LikeCommentDTO dto = modelMapper.map(comment, LikeCommentDTO.class);
        return dto;
    }

    @Transactional
    public DislikeCommentDTO unlikeComment(User u, Comment comment) {
        if (!comment.getCommentLikers().contains(u)) {
            throw new BadRequestException("You already unliked the comment.");
        }
        comment.getCommentLikers().remove(u);
        u.getLikedComments().remove(comment);
        userRepository.save(u);
        commentRepository.save(comment);
        DislikeCommentDTO dto = modelMapper.map(comment, DislikeCommentDTO.class);
        return dto;

    }

    @Transactional
    public LikeCommentDTO dislikeComment(User u, Comment comment) {
        if (comment.getCommentDislikers().contains(u)) {
            throw new BadRequestException("You already disliked the comment.");
        }

        comment.getCommentDislikers().add(u);
        u.getDislikedComments().add(comment);
        comment.getCommentLikers().remove(u);
        u.getLikedComments().remove(comment);
        userRepository.save(u);
        commentRepository.save(comment);
        LikeCommentDTO dto = modelMapper.map(comment, LikeCommentDTO.class);
        return dto;
    }
}

