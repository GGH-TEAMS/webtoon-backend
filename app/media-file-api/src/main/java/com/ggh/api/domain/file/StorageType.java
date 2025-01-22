package com.ggh.api.domain.file;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StorageType {
    LOCAL("Local"),
    AWS("AWS S3"),
    ;

    private final String description;
}
