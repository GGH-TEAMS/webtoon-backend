package com.ggh.api.core.data.file.media;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MediaFileExtensionValidatorTest {

    private final MediaFileExtensionValidator validator = new MediaFileExtensionValidator();

    @ParameterizedTest
    @ValueSource(strings = {
            "image.jpg", "photo.jpeg", "picture.png", "graphic.gif",
            "design.bmp", "icon.svg", "thumbnail.webp"
    })
    void 이미지_파일_확장자가_유효하면_true를_반환한다(String fileName) {
        // Act & Assert
        assertTrue(validator.isValidFileExtension(fileName), fileName + "는 유효한 이미지 파일 확장자여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "song.mp3", "track.wav", "sound.ogg",
            "music.flac", "voice.aac", "melody.midi"
    })
    void 오디오_파일_확장자가_유효하면_true를_반환한다(String fileName) {
        // Act & Assert
        assertTrue(validator.isValidFileExtension(fileName), fileName + "는 유효한 오디오 파일 확장자여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "movie.mp4", "clip.avi", "video.mkv",
            "film.mov", "recording.wmv", "animation.webm",
            "short.3gp", "trailer.3g2"
    })
    void 비디오_파일_확장자가_유효하면_true를_반환한다(String fileName) {
        // Act & Assert
        assertTrue(validator.isValidFileExtension(fileName), fileName + "는 유효한 비디오 파일 확장자여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "document.pdf", "archive.zip",
            "script.js", "style.css",
            "spreadsheet.xlsx", "presentation.pptx"
    })
    void 잘못된_파일_확장자는_false를_반환한다(String fileName) {
        // Act & Assert
        assertFalse(validator.isValidFileExtension(fileName), fileName + "는 유효하지 않은 파일 확장자여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"file", "imagefile", "audiofile.", ".hiddenfile"})
    void 확장자가_없는_파일은_false를_반환한다(String fileName) {
        // Act & Assert
        assertFalse(validator.isValidFileExtension(fileName), fileName + "는 확장자가 없으므로 유효하지 않아야 합니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "IMAGE.JPG, true",
            "Photo.JPEG, true",
            "video.Mp4, true",
            "sound.WAV, true",
            "document.PDF, false",
            ".hiddenfile, false"
    })
    void 대소문자와_무관하게_확장자를_검증한다(String fileName, boolean expectedResult) {
        // Act
        boolean result = validator.isValidFileExtension(fileName);

        // Assert
        assertEquals(expectedResult, result, fileName + (expectedResult ? "는 유효해야 합니다." : "는 유효하지 않아야 합니다."));
    }
}
