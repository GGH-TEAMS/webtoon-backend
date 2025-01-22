package com.ggh.api.domain.file;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FileMetadataTest {

    @Test
    void 임시_파일_메타데이터_생성_테스트() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When
        var temporaryFileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        // Then
        assertNotNull(temporaryFileMetadata);
        assertEquals(originalFilename, temporaryFileMetadata.getOriginalFilename());
        assertEquals(filename, temporaryFileMetadata.getFilename());
        assertEquals(directoryPath, temporaryFileMetadata.getDirectoryPath());
        assertEquals(fileUrl, temporaryFileMetadata.getFileUrl());
        assertTrue(temporaryFileMetadata.isTemporary());
        assertEquals(currentDate.plusDays(3), temporaryFileMetadata.getExpirationDate());
    }

    @Test
    void 유효하지_않은_파일_이름으로_생성_시_예외_발생() {
        // Given
        String originalFilename = "original.txt";
        String invalidFilename = "";
        String validDirectoryPath = "/valid";
        String validFileUrl = "http://example.com/valid.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When & Then
        assertThrows(IllegalArgumentException.class, () ->
                FileMetadata.createTemporary(
                        StorageType.LOCAL,
                        invalidFilename,
                        originalFilename,
                        validDirectoryPath,
                        validFileUrl,
                        currentDate
                )
        );
    }

    @Test
    void 임시_파일은_3일뒤_만료가_되어야한다() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When
        var temporaryFileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        // Then
        var expirationDate = currentDate.plusDays(4);
        assertTrue(temporaryFileMetadata.isExpired(expirationDate));
    }

    @Test
    void 임시파일_영속화_테스트() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When
        var fileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        var persistentMetadata = new PersistentMetadata("service123", "useCaseABC");
        assertTrue(fileMetadata.isTemporary());

        // When
        fileMetadata.persist(LocalDate.of(2025, 1, 25), 5, persistentMetadata);


        // Then
        assertNotNull(fileMetadata);
        assertEquals(originalFilename, fileMetadata.getOriginalFilename());
        assertEquals(filename, fileMetadata.getFilename());
        assertEquals(directoryPath, fileMetadata.getDirectoryPath());
        assertEquals(fileUrl, fileMetadata.getFileUrl());
        assertFalse(fileMetadata.isTemporary());
        assertEquals(LocalDate.of(2025, 1, 30), fileMetadata.getExpirationDate());
    }

    @Test
    void 이미_영속화가_진행된_파일을_영속화_시_예외발생() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When
        var fileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        var persistentMetadata = new PersistentMetadata("service123", "useCaseABC");
        fileMetadata.persist(LocalDate.of(2025, 1, 25), 5, persistentMetadata);
        assertFalse(fileMetadata.isTemporary());
        // When

        assertThrows(IllegalStateException.class, () ->
                fileMetadata.persist(LocalDate.of(2025, 1, 25), 5, persistentMetadata)
        );
    }

    @Test
    void 영속화를_할_때_만료일이_마이너스_1보다_작은경우_예외발생() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When & Then
        var fileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        var persistentMetadata = new PersistentMetadata("service123", "useCaseABC");
        assertThrows(IllegalArgumentException.class, () -> fileMetadata.persist(LocalDate.of(2025, 1, 25), -2, persistentMetadata));
    }

    @Test
    void 영속화를_할_때_만료일이_마이너스_1인경우_만료일이_무기한_테스트() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When & Then
        var fileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        var persistentMetadata = new PersistentMetadata("service123", "useCaseABC");
        fileMetadata.persist(LocalDate.of(2025, 1, 25), -1, persistentMetadata);

        assertNull(fileMetadata.getExpirationDate());
    }

    @Test
    void 만료일이_무기한일때_isExpired는_항상_false를_반환해야한다() {
        // Given
        String originalFilename = "original.txt";
        String filename = "temp.txt";
        String directoryPath = "/temp";
        String fileUrl = "http://example.com/temp.txt";
        LocalDate currentDate = LocalDate.of(2025, 1, 21);

        // When & Then
        var fileMetadata = FileMetadata.createTemporary(
                StorageType.LOCAL,
                originalFilename,
                filename,
                directoryPath,
                fileUrl,
                currentDate
        );

        var persistentMetadata = new PersistentMetadata("service123", "useCaseABC");
        fileMetadata.persist(LocalDate.of(2025, 1, 25), -1, persistentMetadata);

        assertFalse(fileMetadata.isExpired(LocalDate.of(2025, 1, 25).plusYears(100)));
    }
}
