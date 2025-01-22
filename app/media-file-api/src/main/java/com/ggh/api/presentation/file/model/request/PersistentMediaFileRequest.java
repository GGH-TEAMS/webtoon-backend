package com.ggh.api.presentation.file.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PersistentMediaFileRequest(

        @Schema(
                description = """
                        파일 URL
                        """,
                example = "http://localhost:8081/test.png"
        )
        @NotBlank
        String fileUrl,

        @Schema(
                description = """
                        Service Id
                        """,
                example = "webtoon"
        )
        @NotBlank
        String serviceId,

        @Schema(
                description = """
                        사용 케이스
                        """,
                example = "webtoon-content"
        )
        @NotBlank
        String usecase,

        @Schema(
                description = """
                        현재시점으로부터 파일 만료일 \n
                        -1 인 경우 무기한
                        """,
                example = "3"
        )
        @Min(0)
        Integer expirationDay
) {
}
