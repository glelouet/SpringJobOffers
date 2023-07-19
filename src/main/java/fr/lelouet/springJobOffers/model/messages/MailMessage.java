package fr.lelouet.springJobOffers.model.messages;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

	@Builder.Default
	private List<String> targetMails = new ArrayList<>();

	@NotNull
	private String subject;

	@NotNull
	@Lob
	private String content;

	@Lob
	private String eml;

}
