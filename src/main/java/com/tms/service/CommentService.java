package com.tms.service;

import com.tms.model.dto.CommentResponseDto;
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

    public Optional<List<CommentResponseDto>> getAllComment() {
        return Optional.of(commentRepository.findAll().stream()
                .map(commentRequestDto -> CommentResponseDto.builder()
                        .commentTopic(commentRequestDto.getCommentTopic())
                        .descriptionComments(commentRequestDto.getDescriptionComments())
                        .newsId(commentRequestDto.getNewsId())
                        .authorCommentId(commentRequestDto.getAuthorCommentId())
                        .build()
                ).toList());
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Optional<CommentResponseDto> updateComment(Comment comment) {
        return Optional.of(commentRepository.save(comment))
                .map(commentRequestDto -> CommentResponseDto.builder()
                        .commentTopic(commentRequestDto.getCommentTopic())
                        .descriptionComments(commentRequestDto.getDescriptionComments())
                        .newsId(commentRequestDto.getNewsId())
                        .authorCommentId(commentRequestDto.getAuthorCommentId())
                        .build()
                );
    }

    public Boolean deleteComment(Long id) {
        commentRepository.deleteById(id);
        return !commentRepository.existsById(id);
    }

    public Optional<CommentResponseDto> createComment(Comment comment) {
         return  Optional.of(commentRepository.save(comment))
                 .map(commentRequestDto -> CommentResponseDto.builder()
                         .commentTopic(commentRequestDto.getCommentTopic())
                         .descriptionComments(commentRequestDto.getDescriptionComments())
                         .newsId(commentRequestDto.getNewsId())
                         .authorCommentId(commentRequestDto.getAuthorCommentId())
                         .build()
                 );
    }
}
