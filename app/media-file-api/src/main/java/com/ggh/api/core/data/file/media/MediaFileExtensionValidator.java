package com.ggh.api.core.data.file.media;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MediaFileExtensionValidator {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("^.+\\.(jpg|jpeg|png|gif|bmp|svg|webp)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern AUDIO_PATTERN = Pattern.compile("^.+\\.(mp3|wav|ogg|flac|aac|midi)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VIDEO_PATTERN = Pattern.compile("^.+\\.(mp4|avi|mkv|mov|wmv|webm|3gp|3g2)$", Pattern.CASE_INSENSITIVE);

    public boolean isValidFileExtension(String fileName) {
        return IMAGE_PATTERN.matcher(fileName).matches() || AUDIO_PATTERN.matcher(fileName).matches() || VIDEO_PATTERN.matcher(fileName).matches();
    }
}