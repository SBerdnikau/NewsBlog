package com.tms.service;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ImgService {
    private final Path ROOT_FILE_PATH = Paths.get("data/img");

    public Boolean uploadImage(MultipartFile file) {
        try {
            if (file.getOriginalFilename() == null) {
                return false;
            }
            Files.copy(file.getInputStream(), ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
        } catch (IOException exception) {
            return false;
        }
        return true;
    }

    public Optional<Resource> getImage(String fileName) {
        Resource resource = new PathResource(ROOT_FILE_PATH.resolve(fileName));
        if (resource.exists()) {
            return Optional.of(resource);
        }
        return Optional.empty();
    }

    public Boolean deleteImage(String fileName) {
        Path path = ROOT_FILE_PATH.resolve(fileName);
        File file = new File(path.toString());
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
