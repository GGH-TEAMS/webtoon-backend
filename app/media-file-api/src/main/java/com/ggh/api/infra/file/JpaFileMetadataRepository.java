package com.ggh.api.infra.file;

import com.ggh.api.domain.file.FileMetadata;
import com.ggh.api.domain.file.FileMetadataRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFileMetadataRepository extends JpaRepository<FileMetadata, Long>, FileMetadataRepository {
}
