package com.ggh.api.config.file;

import com.ggh.api.domain.file.FileRepositoryProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("app.file.local")
public class LocalFileProperties implements FileRepositoryProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    private String baseDir;

    @Override
    public String baseUrl() {
        return this.baseUrl;
    }
}
