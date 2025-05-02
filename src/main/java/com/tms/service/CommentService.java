package com.tms.service;

import com.tms.model.entity.Comment;
import com.tms.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Optional<Comment> updateComment(Comment comment) {
        return Optional.of(commentRepository.save(comment));
    }

    public Boolean deleteComment(Long id) {
        commentRepository.deleteById(id);
        return !commentRepository.existsById(id);
    }

    public Boolean createComment(Comment comment) {
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
