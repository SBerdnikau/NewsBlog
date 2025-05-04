package com.tms.controller;

import com.tms.model.dto.UserResponseDto;
import com.tms.model.entity.User;
import com.tms.service.UserService;
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
@RequestMapping("/user")
@Tag(name = "User Controller", description = "User Management")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by ID", description = "Returns a user by their unique ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") @Parameter(description = "User ID")  Long id) {
        logger.info("Received request to fetch user with ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            logger.warn("User with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("User successfully fetched: {}", user);
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @Operation(summary = "Update user", description = "Updates user information")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "409", description = "Conflict when updating user")
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody User user) {
        logger.info("Received request to update user: {}", user);
        Optional<UserResponseDto> userUpdated = userService.updateUser(user);
        if (userUpdated.isEmpty()) {
            logger.error("Failed to update user: {}", user);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("User updated successfully: {}", userUpdated.get());
        return new ResponseEntity<>(userUpdated.get(), HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Return all users")
    @ApiResponse(responseCode = "200", description = "User list successfully retrieved")
    @ApiResponse(responseCode = "204", description = "The user list is empty")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        logger.info("Received request to fetch all users");
        Optional<List<UserResponseDto>> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("Users not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Users successfully fetched: Quantity {}", users.get().size());
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

    @Operation(summary = "Delete user", description = "Deletes user by ID")
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @ApiResponse(responseCode = "409", description = "Error deleting user")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") @Parameter(description = "User ID") Long userId) {
        logger.info("Received request to delete user with ID: {}", userId);
        Boolean result = userService.deleteUser(userId);
        if (!result) {
            logger.warn("Failed to delete user with ID {}", userId);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        logger.info("User with ID {} deleted successfully", userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
