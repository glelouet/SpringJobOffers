package fr.lelouet.springJobOffers.services;

import java.util.stream.Collectors;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.converter.EmailConverter;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.messages.MailMessage;
import fr.lelouet.springJobOffers.model.messages.MailMessage.MailMessageBuilder;
import fr.lelouet.springJobOffers.repositories.MailMessageRepository;
import fr.lelouet.springJobOffers.services.generic.DatedEService;
import jakarta.validation.constraints.NotNull;

@Service
public class MailMessageService extends DatedEService<MailMessage, MailMessageRepository> {

	public String addEML(@NotNull String eml) {
		Email converted = EmailConverter.emlToEmail(eml);
		MailMessageBuilder<?, ?> builder = MailMessage.builder();

		if (converted.getSubject() != null) {
			builder.subject(converted.getSubject());
		} else {
			return "missing subject";
		}

		if (converted.getFromRecipient() != null) {
			builder.sourceMail(converted.getFromRecipient().getAddress());
		} else {
			return "missing from";
		}

		if (converted.getToRecipients() != null) {
			builder.targetMails(converted.getToRecipients().stream().map(r -> r.getAddress()).collect(Collectors.toList()));
		} else {
			return "missing to";
		}

		if (converted.getPlainText() != null) {
			builder.content(converted.getPlainText());
		} else {
			return "missing content";
		}

		if (converted.getSentDate() != null) {
			builder.sentDate(converted.getSentDate().toInstant());
		}

		builder.eml(eml);

		save(builder.build());
		return null;
	}

}
