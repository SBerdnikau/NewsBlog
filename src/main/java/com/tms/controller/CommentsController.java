package com.tms.controller;

import com.tms.model.dto.CommentResponseDto;
import com.tms.model.entity.Comment;
import com.tms.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Autowired
    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Create comment", description = "Adds a new comment to the system")
    @ApiResponse(responseCode = "201", description = "Comment successfully created")
    @ApiResponse(responseCode = "409", description = "Conflict: Comment not created")
    @PostMapping
    public ResponseEntity<HttpStatus> createNews(@RequestBody Comment comment) {
        Optional<CommentResponseDto> commentResponseDto = commentService.createComment(comment);
        if (commentResponseDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get comment by ID", description = "Returns a comment by their unique ID")
    @ApiResponse(responseCode = "200", description = "Comment found")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("id")  Long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }

    @Operation(summary = "Update comment", description = "Updates comment information")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully")
    @ApiResponse(responseCode = "409", description = "Conflict when updating comment")
    @PutMapping
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody Comment comment) {
        Optional<CommentResponseDto> commentUpdated = commentService.updateComment(comment);
        if (commentUpdated.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(commentUpdated.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get all comments", description = "Return all comments")
    @ApiResponse(responseCode = "200", description = "Comment list successfully retrieved")
    @ApiResponse(responseCode = "204", description = "The comment list is empty")
    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        Optional<List<CommentResponseDto>> commentList = commentService.getAllComment();
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentList.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete comment", description = "Deletes comment by ID")
    @ApiResponse(responseCode = "204", description = "Comment successfully deleted")
    @ApiResponse(responseCode = "409", description = "Error deleting comment")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable("id") Long commentId) {
        Boolean result = commentService.deleteComment(commentId);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
