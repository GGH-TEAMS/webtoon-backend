package com.ggh.api.domain.file;

import java.util.Optional;

public interface FileMetadataRepository {
    Optional<FileMetadata> findByFileUrl(String fileUrl);
    FileMetadata save(FileMetadata fileMetadata);
    void delete(FileMetadata fileMetadata);
}
