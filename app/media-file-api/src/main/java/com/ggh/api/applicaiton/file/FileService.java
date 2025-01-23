package com.ggh.api.applicaiton.file;

import com.ggh.api.core.data.file.FileDirectoryPathGenerator;
import com.ggh.api.core.data.file.FileNameGenerator;
import com.ggh.api.core.data.file.FileUtils;
import com.ggh.api.domain.file.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FileMetadataRepository fileMetadataRepository;
    private final FileRepositoryProperties fileRepositoryProperties;
    private final FileNameGenerator fileNameGenerator;
    private final FileDirectoryPathGenerator fileDirectoryPathGenerator;

    @Transactional
    public String uploadTempFile(String originalFilename, String fileExtension, InputStream inputStream) {

        var storageType = StorageType.LOCAL;
        var filename = FileUtils.addExtension(fileNameGenerator.generateFileName(fileExtension), fileExtension);
        var dirPath = fileDirectoryPathGenerator.generateDirectoryPath();
        var fileUrl = fileRepositoryProperties.baseUrl() + "/" + dirPath + "/" + filename;
        var filePath = dirPath.resolve(filename).normalize();
        var fileMetadata = FileMetadata.createTemporary(
                storageType,
                originalFilename,
                filename,
                dirPath.toString(),
                fileUrl,
                LocalDate.now()
        );

        fileRepository.save(fileRepositoryProperties, filePath, fileMetadata, inputStream);
        fileMetadataRepository.save(fileMetadata);

        return fileMetadata.getFileUrl();
    }
}
