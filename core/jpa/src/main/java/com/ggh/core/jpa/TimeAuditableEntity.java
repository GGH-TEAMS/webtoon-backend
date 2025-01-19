package com.ggh.core.jpa;

import com.ggh.core.jpa.converter.InstantToUtcConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@Getter(AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class TimeAuditableEntity {

	@CreatedDate
	@Column(name = "CT_UTC", updatable = false)
	@Convert(converter = InstantToUtcConverter.class)
	private Instant creationTime;

	@LastModifiedDate
	@Column(name = "UT_UTC")
	@Convert(converter = InstantToUtcConverter.class)
	private Instant updateTime;

}
