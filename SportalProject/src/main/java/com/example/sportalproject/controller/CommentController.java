package com.example.sportalproject.controller;

import com.example.sportalproject.exceptions.UnauthorisedException;
import com.example.sportalproject.model.DTO.categoryDTOs.CommentEditDTO;
import com.example.sportalproject.model.DTO.commentDTOs.*;
import com.example.sportalproject.model.entity.Article;
import com.example.sportalproject.model.entity.Comment;
import com.example.sportalproject.model.entity.User;
import com.example.sportalproject.repository.UserRepository;
import com.example.sportalproject.service.ArticleService;
import com.example.sportalproject.service.CommentService;
import com.example.sportalproject.service.UserService;
import com.mysql.cj.Session;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class CommentController  {


    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @PostMapping("/article/createComment")
    public ResponseEntity<CommentWithoutArticleDTO> createParentComment(@RequestBody CommentRequestDTO commentDTO, HttpSession session, HttpServletRequest request) {
        User u = userService.getById((long) session.getAttribute(SessionValidator.USER_ID));
        Article article = articleService.getById(commentDTO.getId());
        CommentWithoutArticleDTO comment = commentService.createParentComment(commentDTO, article, u);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/article/comment/createSubComment")
    public ResponseEntity<CommentWithoutArticleDTO> createChildComment(@RequestBody SubCommentDTO subComment, HttpSession session, HttpServletRequest request) {
        User u = userService.getById((long) session.getAttribute(SessionValidator.USER_ID));
        CommentWithoutArticleDTO dto = commentService.createChildComment(subComment, u);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/edit/comment")
    public ResponseEntity<CommentEditDTO> editComment(@RequestBody CommentEditDTO comment, HttpServletRequest request, HttpSession session) {
        SessionValidator.validateSession(session, request);
        long sessionId = (long) session.getAttribute(SessionValidator.USER_ID);
        User u = userRepository.getById(comment.getAuthor().getId());
        if (sessionId != comment.getAuthor().getId()) {
            throw new UnauthorisedException("You have to login!");
        }
        return commentService.editComment(comment, u);
    }

    @DeleteMapping("/delete/comment{id}")
    public void deleteComment(@PathVariable long id, HttpServletRequest request, HttpSession session) {
        SessionValidator.validateSession(session, request);
        commentService.deleteComment(id);
    }

    @PutMapping("/article/like/comment{id}")
    public ResponseEntity<LikeCommentDTO> likeComment(@PathVariable long id, HttpServletRequest request, HttpSession session) {

        Comment comment = commentService.getCommentById(id);
        SessionValidator.validateSession(session, request);
        User u = commentService.getUserById(((long) session.getAttribute(SessionValidator.USER_ID)));
        LikeCommentDTO dto = commentService.likeComment(u, comment);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/article/unlike/comment{id}")
    public ResponseEntity<DislikeCommentDTO> unlikeComment(@PathVariable long id, HttpServletRequest request, HttpSession session) {

        Comment comment = commentService.getCommentById(id);
        SessionValidator.validateSession(session, request);
        User u = commentService.getUserById(((long) session.getAttribute(SessionValidator.USER_ID)));
        DislikeCommentDTO dto = commentService.unlikeComment(u, comment);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/article/dislike/comment{id}")
    public ResponseEntity<LikeCommentDTO> dislikeComment(@PathVariable long id, HttpServletRequest request, HttpSession session) {

        Comment comment = commentService.getCommentById(id);
        SessionValidator.validateSession(session, request);
        User u = commentService.getUserById(((long) session.getAttribute(SessionValidator.USER_ID)));
        LikeCommentDTO dto = commentService.dislikeComment(u, comment);
        return ResponseEntity.ok(dto);
    }
}
