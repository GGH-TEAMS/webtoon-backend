package com.ggh.api.presentation.file.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UploadMediaFileResponse(

        @Schema(
                description = """
                        파일 URL
                        """,
                example = "https://media-file-api-develop.aiforpet.com/media-file/*"
        )
        String fileUrl
) {
}
