package com.tms.service;

import com.tms.model.dto.CommentResponseDto;
import com.tms.model.entity.Comment;
import com.tms.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final SecurityService securityService;

    @Autowired
    public CommentService(CommentRepository commentRepository, SecurityService securityService) {
        this.commentRepository = commentRepository;
        this.securityService = securityService;
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
        if (securityService.canAccessComment(id)) {
            return commentRepository.findById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

    public Optional<CommentResponseDto> updateComment(Comment comment) {
        if (securityService.canAccessComment(comment.getId())) {
            return Optional.of(commentRepository.save(comment))
                    .map(commentRequestDto -> CommentResponseDto.builder()
                            .commentTopic(commentRequestDto.getCommentTopic())
                            .descriptionComments(commentRequestDto.getDescriptionComments())
                            .newsId(commentRequestDto.getNewsId())
                            .authorCommentId(commentRequestDto.getAuthorCommentId())
                            .build()
                    );
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + comment.getId());
    }

    public Boolean deleteComment(Long id) {
        if (securityService.canAccessComment(id)) {
            commentRepository.deleteById(id);
            return !commentRepository.existsById(id);
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + id);
    }

    public Optional<CommentResponseDto> createComment(Comment comment) {
        if (securityService.canAccessComment(comment.getId())) {
            return Optional.of(commentRepository.save(comment))
                    .map(commentRequestDto -> CommentResponseDto.builder()
                            .commentTopic(commentRequestDto.getCommentTopic())
                            .descriptionComments(commentRequestDto.getDescriptionComments())
                            .newsId(commentRequestDto.getNewsId())
                            .authorCommentId(commentRequestDto.getAuthorCommentId())
                            .build()
                    );
        }
        throw new AccessDeniedException("Access denied login:" + SecurityContextHolder.getContext().getAuthentication().getName() + " by id " + comment.getId());
    }
}
