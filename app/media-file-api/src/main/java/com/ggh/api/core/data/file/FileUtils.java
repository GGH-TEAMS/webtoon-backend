package com.ggh.api.core.data.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    public static Path convertFilePath(
            String baseUrl,
            String fileUrl
    ) {
        if (fileUrl.startsWith(baseUrl)) {
            String relativePath = fileUrl.substring(baseUrl.length());

            if (relativePath.startsWith("/")) {
                relativePath = relativePath.substring(1);
            }

            return Paths.get(relativePath);
        } else {
            throw new IllegalArgumentException("Invalid host: " + fileUrl);
        }
    }


}
