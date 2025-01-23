package com.ggh.api.core.data.file.media;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MediaMimeTypeUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "image/jpeg", "image/png", "image/gif", "image/svg+xml",
            "image/bmp", "image/tiff", "image/x-icon", "image/webp",
            "audio/mpeg", "audio/wav", "audio/ogg", "audio/midi",
            "audio/flac", "audio/aac", "audio/webm", "audio/3gpp",
            "audio/3gpp2", "video/mp4", "video/mpeg", "video/webm",
            "video/ogg", "video/x-msvideo", "video/3gpp", "video/3gpp2",
            "video/quicktime", "video/x-matroska"
    })
    void 유효한_MIME_타입은_예외를_던지지_않는다(String mimeType) {
        assertDoesNotThrow(() -> MediaMimeTypeUtils.validateMimeType(mimeType), mimeType + "는 유효한 MIME 타입이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "application/pdf", "text/plain", "application/json",
            "", "invalid/mime-type"
    })
    void 잘못된_MIME_타입은_IllegalArgumentException을_던진다(String mimeType) {
        assertThrows(
                IllegalArgumentException.class,
                () -> MediaMimeTypeUtils.validateMimeType(mimeType),
                mimeType + "는 유효하지 않은 MIME 타입이어야 합니다."
        );
    }

    @ParameterizedTest
    @CsvSource({
            "image/jpeg, jpg",
            "image/png, png",
            "image/gif, gif",
            "image/svg+xml, svg",
            "image/bmp, bmp",
            "image/tiff, tiff",
            "image/x-icon, ico",
            "image/webp, webp",
            "audio/mpeg, mp3",
            "audio/wav, wav",
            "audio/ogg, ogg",
            "audio/midi, midi",
            "audio/flac, flac",
            "audio/aac, aac",
            "audio/webm, webm",
            "audio/3gpp, 3gp",
            "audio/3gpp2, 3g2",
            "video/mp4, mp4",
            "video/mpeg, mpeg",
            "video/webm, webm",
            "video/ogg, ogv",
            "video/x-msvideo, avi",
            "video/3gpp, 3gp",
            "video/3gpp2, 3g2",
            "video/quicktime, mov",
            "video/x-matroska, mkv"
    })
    void 유효한_MIME_타입에서_확장자를_정상적으로_반환한다(String mimeType, String expectedExtension) {
        String extension = MediaMimeTypeUtils.getExtensionFromMimeType(mimeType);
        assertEquals(expectedExtension, extension, mimeType + "에 대한 확장자는 '" + expectedExtension + "'이어야 합니다.");
    }

    @Test
    void 잘못된_MIME_타입에서_getExtensionFromMimeType은_IllegalArgumentException을_던진다() {
        String invalidMimeType = "application/pdf";
        assertThrows(
                IllegalArgumentException.class,
                () -> MediaMimeTypeUtils.getExtensionFromMimeType(invalidMimeType),
                invalidMimeType + "는 유효하지 않은 MIME 타입이어야 합니다."
        );
    }
}
