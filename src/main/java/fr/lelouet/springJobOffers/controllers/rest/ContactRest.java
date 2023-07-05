package fr.lelouet.springJobOffers.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.lelouet.springJobOffers.model.Contact;
import fr.lelouet.springJobOffers.services.ContactService;

@RestController
@RequestMapping("/api/contact")
public class ContactRest {

	@Autowired
	private ContactService service;

	@GetMapping("")
	List<Contact> all(
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "100") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "true") Boolean asc) {
		var sort = Sort.by(sortBy);
		Pageable paging = PageRequest.of(pageNo, pageSize, asc ? sort.ascending() : sort.descending());
		return service.all(paging);
	}

	@GetMapping("/byid/{id}")
	Optional<Contact> bydId(@PathVariable long id) {
		return service.getById(id);
	}

	@GetMapping("/search/name/{str}")
	List<Contact> search(@PathVariable String str) {
		return service.searchName(str);
	}

	@GetMapping("/search/phonenumber/{num}")
	List<Contact> byPhoneNumber(@PathVariable String num) {
		return service.searchPhoneNumber(num);
	}

	@GetMapping("/search/mail/{mail}")
	List<Contact> byMail(@PathVariable String mail) {
		return service.searchMail(mail);
	}

	@PostMapping("")
	ResponseEntity<?> createOrUpdate(@RequestBody Contact data) {
		return service.createOrUpdate(data);
	}

	@DeleteMapping("/byid/{id}")
	void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}

}
