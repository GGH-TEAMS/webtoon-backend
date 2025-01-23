package com.ggh.api.core.data.file;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DateFileDirectoryPathExtenderTest {

    private final DateFileDirectoryPathExtender extender = new DateFileDirectoryPathExtender();

    @Test
    void 현재_날짜를_경로에_추가한다() {
        // Arrange
        Path basePath = Paths.get("/base/path");
        LocalDate currentDate = LocalDate.now(Clock.systemUTC());
        Path expectedPath = basePath.resolve(String.valueOf(currentDate.getYear()))
                                    .resolve(String.valueOf(currentDate.getMonthValue()))
                                    .resolve(String.valueOf(currentDate.getDayOfMonth()))
                                    .normalize();

        // Act
        Path result = extender.extendPath(basePath);

        // Assert
        assertEquals(expectedPath, result, "현재 날짜를 기준으로 경로가 올바르게 확장되어야 합니다.");
    }

    @Test
    void 특정_날짜를_경로에_추가한다() {
        // Arrange
        Path basePath = Paths.get("/base/path");
        LocalDate specificDate = LocalDate.of(2025, 1, 23); // 2025년 1월 23일
        Path expectedPath = basePath.resolve("2025")
                                    .resolve("1")
                                    .resolve("23")
                                    .normalize();

        // Act
        Path result = extender.extendPath(basePath, specificDate);

        // Assert
        assertEquals(expectedPath, result, "특정 날짜를 기준으로 경로가 올바르게 확장되어야 합니다.");
    }
}
