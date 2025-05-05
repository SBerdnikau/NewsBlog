package com.tms.controller;

import com.tms.service.FileService;
import com.tms.service.ImgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.Optional;

@RestController
@RequestMapping("/image")
@Tag(name = "Image Controller", description = "Image Management")
public class ImgController {

    private final ImgService imgService;
    private static final Logger logger = LoggerFactory.getLogger(ImgService.class);

    public ImgController(ImgService imgService) {
        this.imgService = imgService;
    }

    @Operation(summary = "Upload image by name", description = "Upload image by their name")
    @ApiResponse(responseCode = "200", description = "Image uploaded")
    @ApiResponse(responseCode = "409", description = "Image not uploaded")
    @PostMapping
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam("file") MultipartFile file) {
        logger.info("Received request to upload image: {}", file);
        Boolean result = imgService.uploadImage(file);
        return new ResponseEntity<>(result ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @Operation(summary = "Get image by name", description = "Returns a image by their name")
    @ApiResponse(responseCode = "200", description = "Image found")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        logger.info("Received request to fetch image: {}", filename);
        Optional<Resource> resource = imgService.getImage(filename);
        if (resource.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.get().getFilename());
            logger.info("Image successfully fetched: {}",filename);
            return new ResponseEntity<>(resource.get(), headers, HttpStatus.OK);
        }
        logger.warn("Image with name  {} not found", filename);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete image by name", description = "Delete image by their name")
    @ApiResponse(responseCode = "204", description = "File deleted")
    @ApiResponse(responseCode = "409", description = "Files not deleted")
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("filename") String filename) {
        logger.info("Received request to delete image with name: {}", filename);
        Boolean result = imgService.deleteImage(filename);
        return new ResponseEntity<>(result ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}
