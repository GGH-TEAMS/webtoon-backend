package com.ggh.api.core.data.file;

import java.util.UUID;

public class UuidFileNameGenerator implements FileNameGenerator {

    @Override
    public String generateFileName(String fileExtension) {
        return UUID.randomUUID() + "." + fileExtension;
    }
}
