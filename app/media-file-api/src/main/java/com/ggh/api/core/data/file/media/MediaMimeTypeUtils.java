package com.ggh.api.core.data.file.media;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MediaMimeTypeUtils {
    private static final Pattern IMAGE_MIME_PATTERN = Pattern.compile("image/(jpeg|png|gif|svg\\+xml|bmp|tiff|x-icon|webp)");

    private static final Pattern AUDIO_MIME_PATTERN = Pattern.compile("audio/(mpeg|wav|ogg|midi|flac|aac|webm|3gpp|3gpp2)");

    private static final Pattern VIDEO_MIME_PATTERN = Pattern.compile("video/(mp4|mpeg|webm|ogg|x-msvideo|3gpp|3gpp2|quicktime|x-matroska)");

    public static void validateMimeType(String mimeType) {
        if (IMAGE_MIME_PATTERN.matcher(mimeType).matches() || AUDIO_MIME_PATTERN.matcher(mimeType).matches() || VIDEO_MIME_PATTERN.matcher(mimeType).matches())
            return;

        throw new IllegalArgumentException("Invalid mime type: " + mimeType);
    }

    private static final Map<String, String> mimeTypeToExtensionMap = new HashMap<>();

    static {
        mimeTypeToExtensionMap.put("image/jpeg", "jpg");
        mimeTypeToExtensionMap.put("image/png", "png");
        mimeTypeToExtensionMap.put("image/gif", "gif");
        mimeTypeToExtensionMap.put("image/svg+xml", "svg");
        mimeTypeToExtensionMap.put("image/bmp", "bmp");
        mimeTypeToExtensionMap.put("image/tiff", "tiff");
        mimeTypeToExtensionMap.put("image/x-icon", "ico");
        mimeTypeToExtensionMap.put("image/webp", "webp");

        mimeTypeToExtensionMap.put("audio/mpeg", "mp3");
        mimeTypeToExtensionMap.put("audio/wav", "wav");
        mimeTypeToExtensionMap.put("audio/ogg", "ogg");
        mimeTypeToExtensionMap.put("audio/midi", "midi");
        mimeTypeToExtensionMap.put("audio/flac", "flac");
        mimeTypeToExtensionMap.put("audio/aac", "aac");
        mimeTypeToExtensionMap.put("audio/webm", "webm");
        mimeTypeToExtensionMap.put("audio/3gpp", "3gp");
        mimeTypeToExtensionMap.put("audio/3gpp2", "3g2");

        mimeTypeToExtensionMap.put("video/mp4", "mp4");
        mimeTypeToExtensionMap.put("video/mpeg", "mpeg");
        mimeTypeToExtensionMap.put("video/webm", "webm");
        mimeTypeToExtensionMap.put("video/ogg", "ogv");
        mimeTypeToExtensionMap.put("video/x-msvideo", "avi");
        mimeTypeToExtensionMap.put("video/3gpp", "3gp");
        mimeTypeToExtensionMap.put("video/3gpp2", "3g2");
        mimeTypeToExtensionMap.put("video/quicktime", "mov");
        mimeTypeToExtensionMap.put("video/x-matroska", "mkv");
    }

    public static String getExtensionFromMimeType(String mimeType) {
        validateMimeType(mimeType);
        return mimeTypeToExtensionMap.get(mimeType);
    }

}
