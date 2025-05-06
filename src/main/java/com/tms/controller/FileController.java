package com.tms.controller;

import com.tms.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/file")
@Tag(name = "File Controller", description = "File Management")
public class FileController {
    private final FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Upload file by name", description = "Upload file by their name")
    @ApiResponse(responseCode = "200", description = "File uploaded")
    @ApiResponse(responseCode = "409", description = "File not uploaded")
    @PostMapping
    public ResponseEntity<HttpStatus> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Received request to upload file: {}", file);
        Boolean result = fileService.uploadFile(file);
        return new ResponseEntity<>(result ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Get file by name", description = "Returns a file by their name")
    @ApiResponse(responseCode = "200", description = "File found")
    @ApiResponse(responseCode = "404", description = "File not found")
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        logger.info("Received request to fetch file: {}", filename);
        Optional<Resource> resource = fileService.getFile(filename);
        if (resource.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.get().getFilename());
            logger.info("File successfully fetched: {}",filename);
            return new ResponseEntity<>(resource.get(), headers, HttpStatus.OK);
        }
        logger.warn("File with name  {} not found", filename);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all files", description = "Returns all files")
    @ApiResponse(responseCode = "200", description = "Files found")
    @ApiResponse(responseCode = "404", description = "Files not found")
    @ApiResponse(responseCode = "500", description = "Server error reading file")
    @GetMapping("/all")
    public ResponseEntity<ArrayList<String>> getListOfFiles() {
        logger.info("Received request to fetch all files");
        ArrayList<String> files;
        try {
            files = fileService.getListOfFiles();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            logger.error("Server error reading files, {}" ,e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (files.isEmpty()) {
            logger.warn("Files not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Files to fetch successfully: {}", files);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @Operation(summary = "Delete file by name", description = "Delete file by their name")
    @ApiResponse(responseCode = "204", description = "File deleted")
    @ApiResponse(responseCode = "409", description = "Files not deleted")
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("filename") String filename) {
        logger.info("Received request to delete file with name: {}", filename);
        Boolean result = fileService.deleteFile(filename);
        return new ResponseEntity<>(result ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
