package com.ggh.api.domain.file;

import com.ggh.core.jpa.TimeAuditableEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FILE_METADATA")
public class FileMetadata extends TimeAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "ORIGINAL_FILENAME")
    private String originalFilename;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "DIRECTORY_PATH")
    private String directoryPath;

    @Column(name = "FILE_URL")
    private String fileUrl;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name = "STORAGE_TYPE")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "serviceId", column = @Column(name = "SERVICE_ID")),
            @AttributeOverride(name = "useCase", column = @Column(name = "USE_CASE"))
    })
    private PersistentMetadata persistentMetadata;

    @Column(name = "IS_TEMPORARY")
    private boolean isTemporary;

    @Builder(access = AccessLevel.PRIVATE)
    private FileMetadata(
            String originalFilename,
            String filename,
            String directoryPath,
            String fileUrl,
            StorageType storageType,
            LocalDate currentDate,
            PersistentMetadata persistentMetadata,
            boolean isTemporary
    ) {
        if (!StringUtils.hasText(originalFilename)) {
            throw new IllegalArgumentException("originalFilename must not be empty");
        }

        if (!StringUtils.hasText(filename)) {
            throw new IllegalArgumentException("filename must not be empty");
        }

        if (!StringUtils.hasText(directoryPath)) {
            throw new IllegalArgumentException("directoryPath must not be empty");
        }

        if (!StringUtils.hasText(fileUrl)) {
            throw new IllegalArgumentException("fileUrl must not be empty");
        }

        this.storageType = Objects.requireNonNull(storageType);
        this.originalFilename = originalFilename;
        this.filename = filename;
        this.directoryPath = directoryPath;
        this.fileUrl = fileUrl;
        this.expirationDate = currentDate.plusDays(3);
        this.persistentMetadata = persistentMetadata;
        this.isTemporary = isTemporary;
    }

    public void persist(LocalDate currentDate, int expirationDay, PersistentMetadata persistentMetadata) {
        Objects.requireNonNull(persistentMetadata, "persistentMetadata must not be null");

        boolean isPersisted = !isTemporary;
        if(isPersisted) {
            throw new IllegalStateException("File metadata already persisted");
        }

        if (expirationDay < -1) {
            throw new IllegalArgumentException("expirationDay must not be negative");
        }

        var noExpirationFile = expirationDay == -1;
        if (!noExpirationFile) {
            this.expirationDate = currentDate.plusDays(expirationDay);
        } else {
            this.expirationDate = null;
        }

        this.isTemporary = false;
    }

    public boolean isTemporary() {
        return isTemporary;
    }

    public boolean isExpired(LocalDate currentDate) {
        if(expirationDate == null) {
            return false;
        }

        return expirationDate.isBefore(currentDate);
    }

    public static FileMetadata createTemporary(
            StorageType storageType,
            String originalFilename,
            String filename,
            String directoryPath,
            String fileUrl,
            LocalDate currentDate
    ) {
        return FileMetadata.builder()
                .storageType(storageType)
                .originalFilename(originalFilename)
                .filename(filename)
                .directoryPath(directoryPath)
                .fileUrl(fileUrl)
                .currentDate(currentDate)
                .isTemporary(true)
                .build();
    }

}

