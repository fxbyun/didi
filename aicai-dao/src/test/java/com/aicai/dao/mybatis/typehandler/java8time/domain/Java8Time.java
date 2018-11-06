package com.aicai.dao.mybatis.typehandler.java8time.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class Java8Time implements Serializable {

	private static final long serialVersionUID = -1333489730005167182L;

	private int id;

	private Instant instant;
	private LocalDateTime localDateTime;
	private LocalDate localDate;
	private LocalTime localTime;
	private OffsetDateTime offsetDateTime;
	private ZonedDateTime zonedDateTime;

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public LocalTime getLocalTime() {
		return localTime;
	}

	public OffsetDateTime getOffsetDateTime() {
		return offsetDateTime;
	}

	public ZonedDateTime getZonedDateTime() {
		return zonedDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
		this.offsetDateTime = offsetDateTime;
	}

	public void setZonedDateTime(ZonedDateTime zonedDateTime) {
		this.zonedDateTime = zonedDateTime;
	}
}
