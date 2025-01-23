package com.ggh.api.presentation.file;

import com.ggh.api.presentation.file.model.request.DeleteMediaFileRequest;
import com.ggh.api.presentation.file.model.request.PersistentMediaFileRequest;
import com.ggh.api.presentation.file.model.response.UploadMediaFileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "미디어 파일 API",
        description = "미디어 파일 관련 API 입니다."
)
@RequestMapping("/v1/media-file")
public interface FileApi {

    @Operation(
            summary = "미디어 파일 업로드 API",
            description = """
                    미디어 파일 업로드 API 이며 \n
                    업로드된 파일은 기본적으로 임시파일에 해당되며 해당 파일의 보존기간은 3일 입니다. \n
                    임시 파일에 대한 상태를 변경하기 위해서는 Persistent API을 통해 미디어 파일에 대한 구체적인 Metadata 설정이 필요합니다. 
                    """
    )
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    UploadMediaFileResponse uploadMediaFile(
            @Parameter(description = "미디어 파일")
            MultipartFile file);

    @Operation(
            summary = "미디어 파일 삭제 API",
            description = """
                    미디어 파일 삭제 API
                    """
    )
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteMediaFile(@Valid @RequestBody DeleteMediaFileRequest request);

    @Operation(
            summary = "미디어 파일 Persist API",
            description = """
                    미디어 파일에 대한 구체적인 Metadata 설정하는 API
                    """
    )
    @PutMapping(value = "/persist", produces = MediaType.APPLICATION_JSON_VALUE)
    void persistentMediaFile(@Valid @RequestBody PersistentMediaFileRequest request);
}
