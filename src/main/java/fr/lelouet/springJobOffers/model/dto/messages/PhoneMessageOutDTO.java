package fr.lelouet.springJobOffers.model.dto.messages;

import java.time.Instant;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PhoneMessageOutDTO {
	public Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date sentDate;
	public String number;
	public String content;

	public void setSentDate(Instant instant) {
		sentDate = Date.from(instant);
	}
}
