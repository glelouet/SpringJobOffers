package fr.lelouet.springJobOffers.controllers.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) Boolean desc) {
		var sort = Sort.by(sortBy);
		sort = desc == Boolean.TRUE ? sort.descending() : sort.ascending();
		if (pageSize != null || pageNo != null) {
			return service.all(PageRequest.of(pageNo == null ? 0 : pageNo, pageSize == null ? 50 : pageSize, sort));
		} else {
			return service.all(sort);
		}
	}

	@GetMapping("/byid/{id}")
	Optional<Contact> bydId(@PathVariable long id) {
		return service.getById(id);
	}

	@PostMapping("/byid/{id}/mail/{mail}")
	ResponseEntity<?> addMailForId(@PathVariable long id, @PathVariable String mail) {
		return addMail(service.getById(id), mail);
	}

	@PostMapping("/bycode/{code}/mail/{mail}")
	ResponseEntity<?> addMailForCode(@PathVariable String code, @PathVariable String mail) {
		return addMail(service.getByCode(code), mail);
	}

	ResponseEntity<?> addMail(Optional<Contact> opt, String mail) {
		if (opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		var data = opt.get();
		var mails = data.getMailAddresses();
		if (mails == null) {
			mails = new ArrayList<>();
			data.setMailAddresses(mails);
		}
		mails.add(mail);
		service.save(data);
		return ResponseEntity.ok(data.getMailAddresses());
	}

	@DeleteMapping("/byid/{id}/mail/{mail}")
	ResponseEntity<?> delMailForId(@PathVariable long id, @PathVariable String mail) {
		return delMail(service.getById(id), mail);
	}

	@DeleteMapping("/bycode/{code}/mail/{mail}")
	ResponseEntity<?> delMailForCode(@PathVariable String code, @PathVariable String mail) {
		return delMail(service.getByCode(code), mail);
	}

	ResponseEntity<?> delMail(Optional<Contact> opt, String mail) {
		if (opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		var data = opt.get();
		var mails = data.getMailAddresses();
		if (mails != null && !mails.isEmpty()) {
			mails.remove(mail);
			service.save(data);
		}
		return ResponseEntity.ok(data.getMailAddresses());
	}

	@PostMapping("/byid/{id}/phonenumber/{number}")
	ResponseEntity<?> addPhoneNumberForId(@PathVariable long id, @PathVariable String number) {
		return addPhoneNumber(service.getById(id), number);
	}

	@PostMapping("/bycode/{code}/phonenumber/{number}")
	ResponseEntity<?> addPhoneNumberForCode(@PathVariable String code, @PathVariable String number) {
		return addPhoneNumber(service.getByCode(code), number);
	}

	ResponseEntity<?> addPhoneNumber(Optional<Contact> opt, String number) {
		if (opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		var data = opt.get();
		var numbers = data.getPhoneNumbers();
		if (numbers == null) {
			numbers = new ArrayList<>();
			data.setPhoneNumbers(numbers);
		}
		numbers.add(number);
		service.save(data);
		return ResponseEntity.ok(data.getPhoneNumbers());
	}

	@DeleteMapping("/byid/{id}/phonenumber/{number}")
	ResponseEntity<?> delPhoneNumberForId(@PathVariable long id, @PathVariable String number) {
		return delPhoneNumber(service.getById(id), number);
	}

	@DeleteMapping("/bycode/{code}/phonenumber/{number}")
	ResponseEntity<?> delPhoneNumberForCode(@PathVariable String code, @PathVariable String number) {
		return delPhoneNumber(service.getByCode(code), number);
	}

	ResponseEntity<?> delPhoneNumber(Optional<Contact> opt, String number) {
		if (opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		var data = opt.get();
		var numbers = data.getPhoneNumbers();
		if (numbers != null && !numbers.isEmpty()) {
			numbers.remove(number);
			service.save(data);
		}
		return ResponseEntity.ok(data.getPhoneNumbers());
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
