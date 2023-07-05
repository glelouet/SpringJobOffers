package fr.lelouet.springJobOffers.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.lelouet.springJobOffers.model.Contact;
import fr.lelouet.springJobOffers.repositories.ContactRepository;
import jakarta.transaction.Transactional;

@Service
public class ContactService {

	@Autowired
	private ContactRepository repository;

	public List<Contact> getAll() {
		return repository.findAll();
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
				cleaned.add(elem.replaceAll("\\s+", ""));
			}
			cleaned.sort(Comparator.naturalOrder());
			data.setPhoneNumbers(cleaned);
		}
		return repository.save(data);
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

	public ResponseEntity<?> createOrUpdate(Contact data) {
		if (data.getId() != null) {
			Optional<Contact> optData = repository.findById(data.getId());
			if (optData.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			Contact existing = optData.get();
			existing.update(data);
			return ResponseEntity.ok(save(existing));
		}
		return ResponseEntity.ok(save(data));
	}

	@Transactional
	public void delete(Contact data) {
		repository.delete(data);
	}

	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
