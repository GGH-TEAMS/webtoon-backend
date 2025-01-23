package com.ggh.api.presentation.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(
        name = "Test API",
        description = """
                테스트 API 입니다."""
)
public interface TestApi {

    @Operation(
            summary = "테스트",
            description = """
                    테스트 입니다."""
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "정상"),
            @ApiResponse(responseCode = "400",
                    description = """
                            * 에러코드
                            """,
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/test")
    String test();

}
