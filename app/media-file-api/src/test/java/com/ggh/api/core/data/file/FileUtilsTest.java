package com.ggh.api.core.data.file;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FileUtilsTest {

    @Test
    void baseUrl과_fileUrl이_일치하면_상대_경로를_반환한다() {
        String baseUrl = "https://example.com/files";
        String fileUrl = "https://example.com/files/documents/report.pdf";
        Path expectedPath = Path.of("documents/report.pdf");

        Path result = FileUtils.convertFilePath(baseUrl, fileUrl);

        assertEquals(expectedPath, result, "fileUrl에서 baseUrl을 제거한 상대 경로가 반환되어야 합니다.");
    }

    @Test
    void fileUrl이_baseUrl로_시작하지_않으면_예외를_던진다() {
        String baseUrl = "https://example.com/files";
        String fileUrl = "https://otherdomain.com/files/documents/report.pdf";

        assertThrows(
                IllegalArgumentException.class,
                () -> FileUtils.convertFilePath(baseUrl, fileUrl),
                "fileUrl이 baseUrl로 시작하지 않으면 IllegalArgumentException이 발생해야 합니다."
        );
    }

    @Test
    void baseUrl과_fileUrl이_같을_때_빈_경로를_반환한다() {
        String baseUrl = "https://example.com/files";
        String fileUrl = "https://example.com/files";
        Path expectedPath = Path.of("");

        Path result = FileUtils.convertFilePath(baseUrl, fileUrl);

        assertEquals(expectedPath, result, "baseUrl과 fileUrl이 같으면 빈 경로가 반환되어야 합니다.");
    }

    @Test
    void fileUrl의_baseUrl_뒤에_슬래시가_있으면_정상적으로_처리한다() {
        String baseUrl = "https://example.com/files/";
        String fileUrl = "https://example.com/files/documents/report.pdf";
        Path expectedPath = Path.of("documents/report.pdf");

        Path result = FileUtils.convertFilePath(baseUrl, fileUrl);

        assertEquals(expectedPath, result, "baseUrl 뒤에 슬래시가 있어도 상대 경로가 올바르게 반환되어야 합니다.");
    }
}
