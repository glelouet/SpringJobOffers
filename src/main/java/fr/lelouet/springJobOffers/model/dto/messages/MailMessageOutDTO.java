package fr.lelouet.springJobOffers.model.dto.messages;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MailMessageOutDTO {

	private String sourceMail;

	private List<String> targetMails;

	private String subject;

	private String content;

	private Instant sentDate;

}
