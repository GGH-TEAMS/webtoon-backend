package com.ggh.api.core.data.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    private static final Pattern EXTENSION_PATTERN = Pattern.compile("^[a-z0-9]+$");

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

    public static String addExtension(String fileName, String extension) {

        if (!StringUtils.hasText(fileName)) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (!StringUtils.hasText(extension)) {
            throw new IllegalArgumentException("Extension cannot be null or empty");
        }

        boolean alreadyExistExtension = fileName.endsWith("." + extension);
        if (alreadyExistExtension) {
            return fileName;
        }

        boolean validExtension = EXTENSION_PATTERN.matcher(extension).matches();
        if (!validExtension) {
            throw new IllegalArgumentException("Extension must only contain lowercase letters and digits");
        }

        return fileName + "." + extension;
    }


}
