package fr.lelouet.springJobOffers.model.messages;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class PhoneMessage extends Message {

	@NotNull
	private String number;

	@NotNull
	@Lob
	private String content;

}
