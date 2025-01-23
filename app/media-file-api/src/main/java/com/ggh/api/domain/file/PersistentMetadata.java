package com.ggh.api.domain.file;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PersistentMetadata {

    private String serviceId;

    private String useCase;

    public PersistentMetadata(String serviceId, String useCase) {
        if (!StringUtils.hasText(serviceId)) {
            throw new IllegalArgumentException("service id must not be empty");
        }

        if (!StringUtils.hasText(useCase)) {
            throw new IllegalArgumentException("usecase must not be empty");
        }

        this.serviceId = serviceId;
        this.useCase = useCase;
    }
}
