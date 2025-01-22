package com.ggh.api.config.file;

import com.ggh.api.core.data.file.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class MediaFileMetadataConfig {

    @Bean
    public DirectoryPathExtender directoryPathExtender() {
        return new DateDirectoryPathExtender();
    }

    @Bean
    public FileNameGenerator fileNameGenerator() {
        return new UuidFileNameGenerator();
    }

    @Bean
    public DirectoryPathGenerator directoryPathGenerator(
            DirectoryPathExtender directoryPathExtender
    ) {
        return () -> {
            var base = Path.of("");
            return directoryPathExtender.extendPath(base);
        };
    }
}
