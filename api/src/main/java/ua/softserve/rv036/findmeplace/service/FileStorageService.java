package ua.softserve.rv036.findmeplace.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ua.softserve.rv036.findmeplace.exception.FileNotFoundException;
import ua.softserve.rv036.findmeplace.exception.FileStorageException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String location) {

        this.fileStorageLocation = Paths.get(location).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Can't create main directory for upload file", e);
        }
    }

    public String storeFile(MultipartFile file, String folder) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename())
                .replaceAll("\\s+", "-")
                .replaceAll("[^\\p{ASCII}]", "");
        fileName = UUID.randomUUID().toString() + fileName;

        try {
            Files.createDirectories(this.fileStorageLocation.resolve(folder));
        } catch (IOException e) {
            throw new FileStorageException("Can't create directory for upload file", e);
        }

        try {

            if (file.isEmpty()) {
                throw new FileStorageException("File is empty");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(
                        inputStream,
                        this.fileStorageLocation.resolve(folder).resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } catch (IOException e) {
            throw new FileStorageException("Can't store file: " + fileName + ". Try again", e);
        }

        return Paths.get(folder, fileName).normalize().toString();
    }

    public Resource downloadFile(String filePath) {

        try {
            Path file = fileStorageLocation.resolve(filePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Can't read file: " + filePath);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Can't read file: " + filePath, e);
        }
    }

}
