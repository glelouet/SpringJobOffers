package fr.lelouet.springJobOffers.model.messages;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class MailMessage extends Message {

	@NotNull
	private String sourceMail;

	private List<String> targetMails;

	@NotNull
	private String subject;

	@NotNull
	private String content;

}
