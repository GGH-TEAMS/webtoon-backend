package com.ggh.api.infra.file;

import com.ggh.api.domain.file.FileMetadata;
import com.ggh.api.domain.file.FileRepository;
import com.ggh.api.domain.file.FileRepositoryProperties;
import com.ggh.api.domain.file.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Repository
public class LocalFileRepository implements FileRepository {

    @Override
    public void save(FileRepositoryProperties properties, Path filePath, FileMetadata metadata, InputStream fileContent) {
        try (InputStream content = fileContent) {
            Files.createDirectories(filePath.getParent());
            Files.copy(content, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            delete(properties, filePath);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(FileRepositoryProperties properties, Path filePath) {
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException deleteException) {
            throw new RuntimeException("Failed to delete partially written file: " + filePath, deleteException);
        }
    }

    @Override
    public boolean support(FileRepositoryProperties properties, StorageType storageType) {
        return StorageType.LOCAL.equals(storageType);
    }
}
