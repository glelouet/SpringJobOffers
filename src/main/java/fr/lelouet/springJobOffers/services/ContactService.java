package fr.lelouet.springJobOffers.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.Contact;
import fr.lelouet.springJobOffers.repositories.ContactRepository;
import fr.lelouet.springJobOffers.services.generic.CodedDatedEService;

@Service
public class ContactService extends CodedDatedEService<Contact, ContactRepository> {

	public static String standardizePhoneNumber(String number) {
		if (number == null) {
			return null;
		}
		number = number.replaceAll("\\s+", "");
		switch (number.length()) {
		case 10:
			if (number.startsWith("0")) {
				number = "+33" + number.substring(1);
			}
			break;
		}
		return number;
	}

	@Override
	public Contact save(Contact data) {
		if (data.getMailAddresses() != null && !data.getMailAddresses().isEmpty()) {
			data.setMailAddresses(data.getMailAddresses().stream()
					.filter(e -> e != null)
					.sorted()
					.distinct()
					.collect(Collectors.toList()));
		}
		if (data.getPhoneNumbers() != null && !data.getPhoneNumbers().isEmpty()) {
			data.setPhoneNumbers(data.getPhoneNumbers().stream()
					.filter(e -> e != null)
					.map(ContactService::standardizePhoneNumber)
					.sorted()
					.distinct()
					.collect(Collectors.toList()));
		}
		return super.save(data);
	}

	public ResponseEntity<?> createOrUpdate(Contact data) {
		if (data.getId() != null) {
			Optional<Contact> optData = getRepository().findById(data.getId());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			Contact existing = optData.get();
			existing.update(data);
			return ResponseEntity.ok(save(existing));
		} else if (data.getCode() != null) {
			Optional<Contact> optData = getRepository().findByCode(data.getCode());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		}
		return ResponseEntity.ok(save(data));
	}

	public List<Contact> searchName(String str) {
		return getRepository().findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(str, str);
	}

	public List<Contact> searchPhoneNumber(String str) {
		return getRepository().findByPhoneNumbers(str);
	}

	public List<Contact> searchMail(String mail) {
		return getRepository().findByMailAddresses(mail);
	}

}
