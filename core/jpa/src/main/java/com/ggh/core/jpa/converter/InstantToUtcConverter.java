package com.ggh.core.jpa.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;

@Converter
public class InstantToUtcConverter implements AttributeConverter<Instant, LocalDateTime> {

    @Override
    public LocalDateTime convertToDatabaseColumn(Instant instant) {

        return instant == null
                ? null
                : LocalDateTime.ofInstant(instant, Clock.systemUTC().getZone());
    }

    @Override
    public Instant convertToEntityAttribute(LocalDateTime localDateTime) {

        return localDateTime == null
                ? null
                : localDateTime.atZone(Clock.systemUTC().getZone()).toInstant();
    }
}
