package fr.lelouet.springJobOffers.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.Contact;
import fr.lelouet.springJobOffers.repositories.ContactRepository;
import jakarta.transaction.Transactional;

@Service
public class ContactService {

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

	@Autowired
	private ContactRepository repository;

	public List<Contact> all() {
		return repository.findAll();
	}

	public List<Contact> all(Pageable paging) {
		return paging == null ? all() : repository.findAll(paging).getContent();
	}

	public List<Contact> all(Sort sort) {
		return sort == null ? all() : repository.findAll(sort);
	}

	public Optional<Contact> getById(long id) {
		return repository.findById(id);
	}

	public Contact save(Contact data) {
		if (data.getMailAddresses() != null) {
			List<String> cleaned = new ArrayList<>();
			for (String elem : data.getMailAddresses()) {
				cleaned.add(elem);
			}
			cleaned.sort(Comparator.naturalOrder());
			data.setMailAddresses(cleaned);
		}
		if (data.getPhoneNumbers() != null) {
			List<String> cleaned = new ArrayList<>();
			for (String elem : data.getPhoneNumbers()) {
				cleaned.add(standardizePhoneNumber(elem));
			}
			cleaned.sort(Comparator.naturalOrder());
			data.setPhoneNumbers(cleaned);
		}
		if (data.getCode() == null) {
			data.setCode(data.getTitle()
					+ "_" + Optional.ofNullable(data.getFirstName()).orElse("").replaceAll("\\s+", "").toUpperCase()
					+ "_" + Optional.ofNullable(data.getLastName()).orElse("").replaceAll("\\s+", "").toUpperCase());
		}
		if (data.getId() == null) {
			data.setCreatedDate(Instant.now());
		}
		data.setUpdatedDate(Instant.now());
		return repository.save(data);
	}

	@Transactional
	public void delete(Contact data) {
		repository.delete(data);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public ResponseEntity<?> createOrUpdate(Contact data) {
		if (data.getId() != null) {
			Optional<Contact> optData = repository.findById(data.getId());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			Contact existing = optData.get();
			existing.update(data);
			return ResponseEntity.ok(save(existing));
		} else if (data.getCode() != null) {
			Optional<Contact> optData = repository.findByCode(data.getCode());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		}
		return ResponseEntity.ok(save(data));
	}

	public List<Contact> searchName(String str) {
		return repository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(str, str);
	}

	public List<Contact> searchPhoneNumber(String str) {
		return repository.findByPhoneNumbers(str);
	}

	public List<Contact> searchMail(String mail) {
		return repository.findByMailAddresses(mail);
	}

}
