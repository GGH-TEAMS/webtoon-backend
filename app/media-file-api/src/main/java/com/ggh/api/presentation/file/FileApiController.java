package com.ggh.api.presentation.file;

import com.ggh.api.applicaiton.file.FileService;
import com.ggh.api.core.data.file.FileUtils;
import com.ggh.api.core.data.file.media.MediaMimeTypeUtils;
import com.ggh.api.presentation.file.model.request.DeleteMediaFileRequest;
import com.ggh.api.presentation.file.model.request.PersistentMediaFileRequest;
import com.ggh.api.presentation.file.model.response.UploadMediaFileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileApiController implements FileApi {

    private final FileService fileService;


    @Override
    public UploadMediaFileResponse uploadMediaFile(MultipartFile file) {

        var originalFilename = file.getOriginalFilename();
        var fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var existFileExtension = fileExtension != null;
        if (!existFileExtension) {
            fileExtension = MediaMimeTypeUtils.getExtensionFromMimeType(file.getContentType());
        }

        try (InputStream inputStream = file.getInputStream()) {

            var fileUrl = fileService.uploadTempFile(
                    FileUtils.addExtension(originalFilename, fileExtension),
                    fileExtension,
                    inputStream
            );

            return new UploadMediaFileResponse(fileUrl);
        } catch (IOException e) {
            throw new IllegalArgumentException("File InputStream Error [File " + file.getOriginalFilename() + " ] : ", e);
        }
    }

    @Override
    public void deleteMediaFile(DeleteMediaFileRequest request) {
        fileService.deleteFile(request.fileUrl());
    }

    @Override
    public void persistentMediaFile(PersistentMediaFileRequest request) {
        fileService.persistFile(request.fileUrl(), request.expirationDay(), request.serviceId(), request.useCase());
    }
}
