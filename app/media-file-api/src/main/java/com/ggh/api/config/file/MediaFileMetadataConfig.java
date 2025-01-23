package com.ggh.api.config.file;

import com.ggh.api.core.data.file.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@RequiredArgsConstructor
@Configuration
public class MediaFileMetadataConfig {

    private final LocalFileProperties localFileProperties;

    @Bean
    public FileDirectoryPathExtender directoryPathExtender() {
        return new DateFileDirectoryPathExtender();
    }

    @Bean
    public FileNameGenerator fileNameGenerator() {
        return new UuidFileNameGenerator();
    }

    @Bean
    public FileDirectoryPathGenerator directoryPathGenerator(
            FileDirectoryPathExtender fileDirectoryPathExtender
    ) {
        return () -> {
            var base = Path.of(localFileProperties.getBaseDir());
            return fileDirectoryPathExtender.extendPath(base);
        };
    }
}
