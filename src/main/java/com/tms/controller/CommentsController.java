package com.tms.controller;

import com.tms.model.dto.CommentResponseDto;
import com.tms.model.entity.Comment;
import com.tms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentsController {
    private final CommentService commentService;

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createNews(@RequestBody Comment comment) {
        Optional<CommentResponseDto> commentResponseDto = commentService.createComment(comment);
        if (commentResponseDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id")  Long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody Comment comment) {
        Optional<CommentResponseDto> commentUpdated = commentService.updateComment(comment);
        if (commentUpdated.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(commentUpdated.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        Optional<List<CommentResponseDto>> commentList = commentService.getAllComment();
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentList.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") Long commentId) {
        Boolean result = commentService.deleteComment(commentId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
