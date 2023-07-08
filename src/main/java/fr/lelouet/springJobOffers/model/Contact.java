package fr.lelouet.springJobOffers.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Contact extends CodedDatedEntity {

	public enum Title {
		Mr, Ms
	}

	@Size(min = 0, max = 255)
	private String lastName;

	@NotNull
	@Size(min = 0, max = 255)
	private String firstName;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Title title;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "contact_phone_number", joinColumns = @JoinColumn(name = "contact_id"))
	@OrderColumn
	private List<String> phoneNumbers;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "contact_mail_address", joinColumns = @JoinColumn(name = "contact_id"))
	@OrderColumn
	private List<@Email String> mailAddresses;

	public void update(Contact data) {
		if (data.firstName != null) {
			firstName = data.firstName;
		}
		if (data.lastName != null) {
			lastName = data.lastName;
		}
		if (data.mailAddresses != null) {
			mailAddresses = data.mailAddresses;
		}
		if (data.phoneNumbers != null) {
			phoneNumbers = data.phoneNumbers;
		}
		if (data.title != null) {
			title = data.title;
		}
	}

}
