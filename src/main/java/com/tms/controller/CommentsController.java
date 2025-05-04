package com.tms.controller;

import com.tms.model.dto.CommentResponseDto;
import com.tms.model.entity.Comment;
import com.tms.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
@Tag(name = "Comment Controller", description = "Comment Management")
public class CommentsController {
    private final CommentService commentService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create comment", description = "Adds a new comment to the system")
    @ApiResponse(responseCode = "201", description = "Comment successfully created")
    @ApiResponse(responseCode = "409", description = "Conflict: Comment not created")
    @PostMapping
    public ResponseEntity<HttpStatus> createNews(@RequestBody Comment comment) {
        logger.info("Received request to create comment: {}", comment);
        Optional<CommentResponseDto> commentResponseDto = commentService.createComment(comment);
        if (commentResponseDto.isEmpty()) {
            logger.error("Failed to create comment: {}", comment);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Comment created successfully: {}", commentResponseDto.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get comment by ID", description = "Returns a comment by their unique ID")
    @ApiResponse(responseCode = "200", description = "Comment found")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id") @Parameter(description = "Comment ID")  Long commentId) {
        logger.info("Received request to fetch comment with ID: {}", commentId);
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (comment.isEmpty()) {
            logger.warn("Comment with ID {} not found", commentId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Comment successfully fetched: {}", comment.get());
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @Operation(summary = "Update comment", description = "Updates comment information")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "409", description = "Conflict when updating comment")
    @PutMapping
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody Comment comment) {
        logger.info("Received request to update comment: {}", comment);
        Optional<CommentResponseDto> commentUpdated = commentService.updateComment(comment);
        if (commentUpdated.isEmpty()) {
            logger.error("Failed to update comment: {}", comment);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("Comment updated successfully: {}", commentUpdated.get());
        return new ResponseEntity<>(commentUpdated.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get all comments", description = "Return all comments")
    @ApiResponse(responseCode = "200", description = "Comment list successfully retrieved")
    @ApiResponse(responseCode = "204", description = "The comment list is empty")
    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        logger.info("Received request to fetch all comments");
        Optional<List<CommentResponseDto>> commentList = commentService.getAllComment();
        if (commentList.isEmpty()) {
            logger.warn("Comments not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Comments successfully fetched: Quantity {}", commentList.get().size());
        return new ResponseEntity<>(commentList.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete comment", description = "Deletes comment by ID")
    @ApiResponse(responseCode = "204", description = "Comment successfully deleted")
    @ApiResponse(responseCode = "409", description = "Error deleting comment")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") @Parameter(description = "Comment ID") Long commentId) {
        logger.info("Received request to delete comment with ID: {}", commentId);
        Boolean result = commentService.deleteComment(commentId);
        if (!result) {
            logger.info("Comment with ID {} deleted successfully", commentId);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.warn("Failed to delete comment with ID {}", commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
