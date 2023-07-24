package fr.lelouet.springJobOffers.model.dto.messages;

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
public class PhoneMessageInDTO {

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	public Date date;
	public String number;
	public String content;
}
