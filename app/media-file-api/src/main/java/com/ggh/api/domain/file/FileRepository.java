package com.ggh.api.domain.file;

import java.io.InputStream;
import java.nio.file.Path;

public interface FileRepository {

    void save(FileRepositoryProperties properties, Path filePath, FileMetadata metadata, InputStream fileContent);

    void delete(FileRepositoryProperties properties, Path filePath);

    boolean support(FileRepositoryProperties properties, StorageType storageType);
}
