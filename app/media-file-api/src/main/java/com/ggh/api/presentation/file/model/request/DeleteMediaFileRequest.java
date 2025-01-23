package com.ggh.api.presentation.file.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DeleteMediaFileRequest(

        @Schema(
                description = """
                        파일 URL
                        """,
                example = "http://localhost:8081/test.png"
        )
        @NotBlank
        String fileUrl
) {
}
